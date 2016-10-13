package cn.zlion.service;

import cn.zlion.ResultHttpSetting;
import cn.zlion.controller.HttpRequestUtil.Param;
import cn.zlion.controller.HttpRequestUtil.RequestParamUtil;
import cn.zlion.domain.JobResult;
import cn.zlion.pagenationUtil.PageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by zzs on 10/12/16.
 */
@Service
public class YbTestService {

    @Autowired
    private ResultHttpSetting resultHttpSetting;

    public void saveClusterDataToYbTest(String appId) throws IOException, URISyntaxException, TableNameException {

        int presentPage = 1;
        List<String> tables = resultHttpSetting.getTables();

        Param app_idParam = new Param("app_id", appId);
        Param pageParam = new Param("page", presentPage);
        Param rowsParam = new Param("rows", resultHttpSetting.getRowsForPerPage());

        RequestParamUtil requestParamUtil = new RequestParamUtil(resultHttpSetting.getUrl());
        requestParamUtil.addParam(app_idParam);
        requestParamUtil.addParam(pageParam);
        requestParamUtil.addParam(rowsParam);

        for (String tableName: tables){
            Param tableParam = new Param("table", tableName);
            requestParamUtil.addParam(tableParam);
            URI uri = new URI(requestParamUtil.getURI());

            JSONObject receiveJsonResult = this.clientRequest(uri);

            if(receiveJsonResult.getInteger("Code") == 200){

                JSONObject pageData = receiveJsonResult.getJSONObject("Data");
                this.saveData(pageData.getJSONArray("data"), tableName);

                int pageSize = pageData.getInteger("totalPages");
                for (presentPage = 2; presentPage <= pageSize; ++presentPage){

                    pageParam.setValue(presentPage);
                    requestParamUtil.setParam(pageParam);
                    uri = new URI(requestParamUtil.getURI());

                    receiveJsonResult = this.clientRequest(uri);
                    if (receiveJsonResult.getInteger("Code") == 200){
                        pageData = receiveJsonResult.getJSONObject("Data");
                        this.saveData(pageData.getJSONArray("data"), tableName);
                    }
                    else if (receiveJsonResult.getInteger("Code") == 103){
                        throw new TableNameException(receiveJsonResult.getString("Msg"));
                    }
                }
            }

        }

    }


    public JSONObject clientRequest(URI uri) throws IOException, URISyntaxException{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        ClientHttpRequest chr = schr.createRequest(uri, resultHttpSetting.getHttpMethod());
        //?page=1&rows=100&app_id=TestApp&table=T_RESULT_JOB
        //chr.getBody().write(param.getBytes());//body中设置请求参数
        ClientHttpResponse res = chr.execute();
        InputStream is = res.getBody(); //获得返回数据,注意这里是个流
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String jsonString = br.readLine();
        JSONObject receiveJsonResult = JSON.parseObject(jsonString);

        return receiveJsonResult;
    }


    @Transactional
    private void saveData(JSONArray data, String tableName){

        System.out.println(data);

    }


}
