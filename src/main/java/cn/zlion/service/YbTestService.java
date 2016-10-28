package cn.zlion.service;

import cn.zlion.ResultHttpSetting;
import cn.zlion.SchemaSetting;
import cn.zlion.controller.HttpRequestUtil.Param;
import cn.zlion.controller.HttpRequestUtil.RequestParamUtil;
import cn.zlion.dao.DataResultDao;
import cn.zlion.domain.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uestc.ercl.znsh.common.constant.DataType;

import javax.sql.DataSource;
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

    @Autowired
    @Qualifier("oracle")
    private DataSource dataSource;



    public void saveClusterDataToYbTest(String appId) throws IOException, URISyntaxException, TableNameException {

        int presentPage = 1;
        List<String> tables = resultHttpSetting.getTables();

        Param pageParam = new Param("page", presentPage);

        RequestParamUtil requestParamUtil = new RequestParamUtil(resultHttpSetting.getUrl());
        requestParamUtil.addParam(new Param("app_id", appId));
        requestParamUtil.addParam(new Param("rows", resultHttpSetting.getRowsForPerPage()));
        requestParamUtil.addParam(pageParam);
        requestParamUtil.addParam("table", null);

        if (!dataResultDao.checkUserExist(appId)){
            dataResultDao.createAppSchema(appId, schemaSetting.getNewSchemaPass());
            dataResultDao.createResultTableOnSchema(appId);

            for (String tableName: tables){

                presentPage = 1;
                Param tableParam = new Param("table", tableName);
                pageParam = new Param("page", presentPage);

                requestParamUtil.setParam(tableParam);
                requestParamUtil.setParam(pageParam);

                URI uri = new URI(requestParamUtil.getURI());

                JSONObject receiveJsonResult = this.clientRequest(uri);

                if(receiveJsonResult.getInteger("Code") == 200){

                    JSONObject pageData = receiveJsonResult.getJSONObject("Data");
                    this.saveStaticData(pageData.getJSONArray("data"), appId, tableName);
                    int pageSize = pageData.getInteger("totalPages");
                    for (presentPage = 2; presentPage <= pageSize; ++presentPage){

                        System.out.println(presentPage);

                        pageParam.setValue(presentPage);
                        requestParamUtil.setParam(pageParam);
                        uri = new URI(requestParamUtil.getURI());

                        receiveJsonResult = this.clientRequest(uri);
                        if (receiveJsonResult.getInteger("Code") == 200){
                            pageData = receiveJsonResult.getJSONObject("Data");
                            this.saveStaticData(pageData.getJSONArray("data"), appId, tableName);
                        }
                        else if (receiveJsonResult.getInteger("Code") == 103){
                            throw new TableNameException(receiveJsonResult.getString("Msg"));
                        }
                    }
                }
            }
        }
        else{
            //审核结果表已存在，此时只需要更新表中数据即可


        }



    }


    public JSONObject clientRequest(URI uri) throws IOException, URISyntaxException{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        ClientHttpRequest chr = schr.createRequest(uri, resultHttpSetting.getHttpMethod());
        //?page=1&rows=100&app_id=TestApp&table=T_RESULT_JOB
        //chr.getBody().write(param.getBytes());//body中设置请求参数
        ClientHttpResponse res = chr.execute();
        InputStream is = res.getBody(); //获得返回数据(流)
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String jsonString = br.readLine();
        JSONObject receiveJsonResult = JSON.parseObject(jsonString);

        return receiveJsonResult;
    }


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



    public Map<String, Object> getMapInterface(){
        Map<String, Object> result = new HashMap<String, Object>();

        List<Field> fields = new ArrayList<Field>();
        Field field = new Field();
        field.setPk(1231231);
        field.setId("jd124141asdf");
        field.setDataType(DataType.TEXT);
        field.setNullable(true);
        field.setName("name");
        field.setSheetPk(123123141);
        fields.add(field);


        List<Sheet> sheets = new ArrayList<Sheet>();
        Sheet sheet = new Sheet();

        sheet.setAppId("TestApp");
        sheet.setDesc("2333");
        sheet.setName("aaaa");
        sheet.setId("sdfa23123131");
        sheet.setPk(123123141);
        sheet.setFields(fields);
        sheets.add(sheet);

        result.put("TestApp", sheets);
        return result;
    }


    public void saveDynamicData(List<Sheet> sheets, String appId){

        sheets.forEach(sheet -> {



        });

    }

}
