package cn.zlion.service;

import ch.qos.logback.classic.db.names.TableName;
import cn.zlion.ResultHttpSetting;
import cn.zlion.SchemaSetting;
import cn.zlion.controller.HttpRequestUtil.HttpRequestClientUtil;
import cn.zlion.controller.HttpRequestUtil.Param;
import cn.zlion.controller.HttpRequestUtil.RequestParamUtil;
import cn.zlion.dao.DataResultDao;
import cn.zlion.domain.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uestc.ercl.znsh.common.constant.DataType;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Created by zzs on 10/12/16.
 */
@Service
public class YbTestService {

    @Autowired
    private ResultHttpSetting resultHttpSetting;

    @Autowired
    private SchemaSetting schemaSetting;

    @Autowired
    private DataResultDao dataResultDao;


    /**
     * 备份用户的在appId表空间上的数据，当用户的app未创建的时候，
     * 则通过直接全部存入来实现对数据表的备份，当数据表已经存在的时候则通过上次的更新时间来进行部分数据备份。
     * @param appId
     * @throws IOException
     * @throws URISyntaxException
     * @throws TableNameException
     */
    public void saveClusterDataToYbTest(String appId) throws IOException, URISyntaxException, TableNameException {

        List<String> tables = resultHttpSetting.getTables();
        Param pageParam = new Param("page", 1);

        RequestParamUtil requestParamUtil = new RequestParamUtil(resultHttpSetting.getUrl());
        requestParamUtil.addParam(new Param("app_id", appId));
        requestParamUtil.addParam(new Param("rows", resultHttpSetting.getRowsForPerPage()));
        requestParamUtil.addParam(pageParam);
        requestParamUtil.addParam("table", null);

        if (!dataResultDao.checkUserExist(appId)){
            //初次备份创建表并全部移入，后面需要改掉
            dataResultDao.createAppSchema(appId, schemaSetting.getNewSchemaPass());
            dataResultDao.createResultTableOnSchema(appId);

            for (String tableName: tables){
                try{
                    listRequest(requestParamUtil, appId, tableName);
                }catch (ClusterRequestException e){
                    e.printStackTrace();
                }
            }
        }
        else{
            //审核结果表已存在，此时只需要更新表中数据即可
            //Task单独处理
            String taskTable = "T_RESULT_TASK";
            Date lastUpdateTime = dataResultDao.getTaskLastUpdateTime(appId);
            requestParamUtil.addParam(new Param("update-time", Long.toString(lastUpdateTime.getTime())));
            requestParamUtil.addParam(new Param("pk", ""));


            try{
                //获取需要更新的TaskResult的主键集合
                List<String> taskPks = listRequestUpdate(requestParamUtil, appId, taskTable);
                //Job和Rule的处理
                tables.remove(taskTable);
                for (String tableName : tables){
                    for (String pk : taskPks){
                        requestParamUtil.setParam("pk", pk);
                        listRequestUpdate(requestParamUtil, appId, tableName);
                    }
                }



            }catch (ClusterRequestException e){
                //考虑一下错误处理
                e.printStackTrace();
                return;
            }



        }

    }

    /**
     * 请求解析并存储获取到的数据
     * @param requestParamUtil
     * @param appId
     * @param tableName
     * @throws URISyntaxException
     * @throws IOException
     * @throws TableNameException
     * @throws ClusterRequestException
     */
    public void listRequest(RequestParamUtil requestParamUtil, String appId, String tableName)
            throws URISyntaxException, IOException, TableNameException, ClusterRequestException{

        int presentPage = 1;
        Param tableParam = new Param("table", tableName);
        Param pageParam = new Param("page", presentPage);
        requestParamUtil.setParam(tableParam);
        requestParamUtil.setParam(pageParam);

        URI uri = new URI(requestParamUtil.getURI());
//            JSONObject receiveJsonResult = this.clientRequest(uri);
        JSONObject receiveJsonResult = HttpRequestClientUtil.httpRequest(requestParamUtil);
        if(receiveJsonResult.getInteger("Code") == 200){

            JSONObject pageData = receiveJsonResult.getJSONObject("Data");
            this.saveStaticData(pageData.getJSONArray("data"), appId, tableName);
            int pageSize = pageData.getInteger("totalPages");
            for (presentPage = 2; presentPage <= pageSize; ++presentPage){

                System.out.println(presentPage);

                getRequestJsonResult(requestParamUtil, presentPage, appId, tableName, HttpMethod.GET);
            }
        }
        else{
            //请求数据错误异常
            throw new ClusterRequestException(receiveJsonResult.getString("Msg"));
        }
    }

    public List<String> listRequestUpdate(RequestParamUtil requestParamUtil, String appId, String tableName)
            throws URISyntaxException, IOException, TableNameException, ClusterRequestException{

        int presentPage = 1;
        Param tableParam = new Param("table", tableName);
        Param pageParam = new Param("page", presentPage);
        requestParamUtil.setParam(tableParam);
        requestParamUtil.setParam(pageParam);

        URI uri = new URI(requestParamUtil.getURI());
        JSONObject receiveJsonResult = HttpRequestClientUtil.httpRequest(requestParamUtil);
        HashSet<String> pkHashSet = new HashSet<String>();

        if(receiveJsonResult.getInteger("Code") == 200){

            JSONObject pageData = receiveJsonResult.getJSONObject("Data");
            this.saveStaticData(pageData.getJSONArray("data"), appId, tableName);
            int pageSize = pageData.getInteger("totalPages");
            for (presentPage = 2; presentPage <= pageSize; ++presentPage){

                System.out.println(presentPage);

                JSONArray datas = getRequestJsonResult(requestParamUtil, presentPage, appId, tableName, HttpMethod.GET);
                HashSet<String> pks = extractPkList(datas);
                pkHashSet.addAll(pks);
            }
        }
        else{
            //请求数据错误异常
            throw new ClusterRequestException(receiveJsonResult.getString("Msg"));
        }
        return new ArrayList<String>(pkHashSet);
    }



    private JSONArray getRequestJsonResult(RequestParamUtil requestParamUtil, int presentPage,
                                           String appId, String tableName, HttpMethod method)
            throws URISyntaxException, IOException, TableNameException{
        requestParamUtil.setParam(new Param("page", presentPage));
        URI uri = new URI(requestParamUtil.getURI());
        JSONArray datas = null;

        JSONObject receiveJsonResult = null;
        if (method == HttpMethod.POST){
            NameValuePair[] pairs = getPairs(requestParamUtil);
            receiveJsonResult = HttpRequestClientUtil.httpRequest(requestParamUtil.getURL(), pairs);
        }
        else{
            receiveJsonResult = HttpRequestClientUtil.httpRequest(requestParamUtil);
        }
        if (receiveJsonResult.getInteger("Code") == 200){
            JSONObject pageData = receiveJsonResult.getJSONObject("Data");
            datas = pageData.getJSONArray("data");
            this.saveStaticData(datas, appId, tableName);
        }
        else if (receiveJsonResult.getInteger("Code") == 103){
            throw new TableNameException(receiveJsonResult.getString("Msg"));
        }
        return datas;
    }


    private NameValuePair[] getPairs(RequestParamUtil requestParamUtil){
        Map<String, Object> params = requestParamUtil.getParaments();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : params.entrySet()){
            NameValuePair pair = new NameValuePair(entry.getKey(), (String) entry.getValue());
            pairs.add(pair);
        }

        return (NameValuePair[]) pairs.toArray();
    }



//    /**
//     * 请求并返回已解析的JSONObject
//     * @param uri
//     * @return receiveJsonResult
//     * @throws IOException
//     * @throws URISyntaxException
//     */
//    public JSONObject clientRequest(URI uri) throws IOException, URISyntaxException{
//
//        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
//        ClientHttpRequest chr = schr.createRequest(uri, resultHttpSetting.getHttpMethod());
//        //?page=1&rows=100&app_id=TestApp&table=T_RESULT_JOB
//        //chr.getBody().write(param.getBytes());//body中设置请求参数
//        ClientHttpResponse res = chr.execute();
//        InputStream is = res.getBody(); //获得返回数据(流)
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//
//        String jsonString = br.readLine();
//        JSONObject receiveJsonResult = JSON.parseObject(jsonString);
//
//        return receiveJsonResult;
//    }



    @Transactional
    private void saveStaticData(JSONArray data, String appId, String tableName) throws TableNameException{

        List list = null;
        if (tableName.equals(TaskResult.TABLE_NAME)) {
            list = new ArrayList<TaskResult>();
            for (Iterator jsonObjectIterator = data.subList(0, data.size()).iterator(); jsonObjectIterator.hasNext(); ) {
                TaskResult taskResult = ((JSONObject) jsonObjectIterator.next()).toJavaObject(TaskResult.class);
                list.add(taskResult);
            }
        }
        else if (tableName.equals(JobResult.TABLE_NAME)){
            list = new ArrayList<JobResult>();
            for (Iterator jsonObjectIterator = data.iterator(); jsonObjectIterator.hasNext(); ) {
                JobResult jobResult = ((JSONObject)jsonObjectIterator.next()).toJavaObject(JobResult.class);
                list.add(jobResult);
            }
        }
        else if (tableName.equals(RuleResult.TABLE_NAME)){
            list = new ArrayList<RuleResult>();
            for (Iterator jsonObjectIterator = data.iterator(); jsonObjectIterator.hasNext(); ) {
                RuleResult ruleResult = ((JSONObject)jsonObjectIterator.next()).toJavaObject(RuleResult.class);
                list.add(ruleResult);
            }
        }

        int[] result = dataResultDao.saveDate(list, "TestApp", tableName);
//        System.out.println(dataResultDao.totalInTable(appId, tableName));

    }

    //获取新添加的数据
    public HashSet<String> extractPkList(JSONArray taskResults){

        HashSet<String> hs = new HashSet<String>();
        for (Iterator jsonObjectIterator = taskResults.subList(0, taskResults.size()).iterator(); jsonObjectIterator.hasNext(); ) {
            TaskResult taskResult = ((JSONObject) jsonObjectIterator.next()).toJavaObject(TaskResult.class);
            hs.add(taskResult.getPk());
        }

        return hs;
    }



    // 测试数据
    public static List<Sheet> sheets()
    {
        //-----------------------------------------------------
        Field f_id = new Field();
        f_id.setPk(3645);
        f_id.setId("id");
        f_id.setName("编号");
        f_id.setDesc("课程编号");
        f_id.setDataType(DataType.NUMBER);
        f_id.setNullable(false);
        //
        Field f_name = new Field();
        f_name.setPk(5441);
        f_name.setId("name");
        f_name.setName("名称");
        f_name.setDesc("课程名称");
        f_name.setDataType(DataType.TEXT);
        f_name.setNullable(false);
        //
        Field f_max = new Field();
        f_max.setPk(6546);
        f_max.setId("maxLearners");
        f_max.setName("最大人数");
        f_max.setDesc("该课程最多选课人数");
        f_max.setDataType(DataType.NUMBER);
        f_max.setNullable(false);
        //
        Field f_cur = new Field();
        f_cur.setPk(365653);
        f_cur.setId("curLearners");
        f_cur.setName("当前人数");
        f_cur.setDesc("该课程当前选课人数");
        f_cur.setDataType(DataType.NUMBER);
        f_cur.setNullable(false);
        //
        Sheet t_cource = new Sheet();
        t_cource.setPk(321505);
        t_cource.setId("Course");
        t_cource.setName("课程信息表");
        t_cource.setDesc("可选课程的信息");
        t_cource.addField(f_id);
        t_cource.addField(f_name);
        t_cource.addField(f_max);
        t_cource.addField(f_cur);
        //-----------------------------------------------------
        Field f_cid = new Field();
        f_cid.setPk(352656);
        f_cid.setId("courseId");
        f_cid.setName("课程编号");
        f_cid.setDesc("选课申请中的课程编号");
        f_cid.setDataType(DataType.NUMBER);
        f_cid.setNullable(false);
        //
        Field f_sname = new Field();
        f_sname.setPk(98765);
        f_sname.setId("stuName");
        f_sname.setName("学生姓名");
        f_sname.setDesc("选课申请中的学生姓名");
        f_sname.setDataType(DataType.TEXT);
        f_sname.setNullable(false);
        //
        Field f_grade = new Field();
        f_grade.setPk(857498);
        f_grade.setId("stuGrade");
        f_grade.setName("学生年级");
        f_grade.setDesc("选课申请中的学生所在的年级");
        f_grade.setDataType(DataType.NUMBER);
        f_grade.setNullable(false);
        //
        Field f_class = new Field();
        f_class.setPk(6123);
        f_class.setId("stuClass");
        f_class.setName("学生班号");
        f_class.setDesc("选课申请中的学生所在的班");
        f_class.setDataType(DataType.NUMBER);
        f_class.setNullable(false);
        //
        Sheet t_registry = new Sheet();
        t_registry.setPk(52456);
        t_registry.setId("CourseRegistry");
        t_registry.setName("选课申请表");
        t_registry.setDesc("学生提交的选课信息");
        t_registry.addField(f_cid);
        t_registry.addField(f_sname);
        t_registry.addField(f_grade);
        t_registry.addField(f_class);
        //
        List<Sheet> sheets = new ArrayList<>();
        sheets.add(t_cource);
        sheets.add(t_registry);
        return sheets;
    }


    public void saveDynamicData(List<Sheet> sheets, String appId){

        sheets.forEach(sheet -> {



        });

    }

}
