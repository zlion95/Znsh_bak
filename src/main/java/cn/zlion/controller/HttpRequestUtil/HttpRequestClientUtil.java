package cn.zlion.controller.HttpRequestUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A client interface request util.
 * Created by zzs on 10/28/16.
 * TODO: 完成其他通用的接口请求并，修改能返回多种结果格式的接口
 */
public class HttpRequestClientUtil {

    /**
     * Post Request method.
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static JSONObject httpRequest(String url, NameValuePair[] params) throws IOException{

        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(url);
        HttpMethod method = postMethod(url, params);
        return excuteRequest(httpClient, method);

    }

    /**
     * Get Request method.
     * @param requestParamUtil
     * @return
     * @throws IOException
     */
    public static JSONObject httpRequest(RequestParamUtil requestParamUtil) throws IOException{

        HttpClient httpClient = new HttpClient();
//        httpClient.getHostConfiguration().setHost(url);
        HttpMethod method = getMethod(requestParamUtil);
        return  excuteRequest(httpClient, method);

    }


    private static HttpMethod getMethod(RequestParamUtil requestParamUtil) throws IOException{
        GetMethod get = new GetMethod(requestParamUtil.getURI());
        get.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        get.releaseConnection();

        return get;
    }


    private static HttpMethod postMethod(String url, NameValuePair[] params) throws IOException{
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        post.setRequestBody(params);
        post.releaseConnection();
        return post;
    }

    private static JSONObject excuteRequest(HttpClient httpClient, HttpMethod method) throws IOException{
        httpClient.executeMethod(method);

        InputStream is = method.getResponseBodyAsStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String jsonString = br.readLine();
        return JSON.parseObject(jsonString);
    }
}
