package cn.zlion.controller;

import cn.zlion.pagenationUtil.PageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URI;

/**
 * Created by zzs on 10/9/16.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/ybtest")
public class YbTestController {


    @ResponseBody
    @RequestMapping(value = "/cluster/data/save", method = RequestMethod.GET)
    public Result bakClusterDate(HttpServletRequest request){
        Result jsonRender = new Result();

        try {
            URI uri = new URI("http://localhost:8080/cluster/result/get");
            SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
            ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.GET);
            //chr.getBody().write(param.getBytes());//body中设置请求参数
            ClientHttpResponse res = chr.execute();
            InputStream is = res.getBody(); //获得返回数据,注意这里是个流
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String jsonString = br.readLine();
            JSONObject receiveJsonResult = JSON.parseObject(jsonString);

        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonRender;
    }

}
