package cn.zlion.controller;

import cn.zlion.service.TableNameException;
import cn.zlion.service.YbTestService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by zzs on 10/9/16.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/ybtest")
public class YbTestController {

    private YbTestService ybTestService;

    @Autowired
    public YbTestController(YbTestService ybTestService) {
        this.ybTestService = ybTestService;
    }

    @RequestMapping(value = "/cluster/data/save", method = RequestMethod.PUT)
    public Result bakClusterDate(HttpServletRequest request){
        Result jsonRender = new Result();

        String appId = "";
        try{
            appId = request.getParameter("app_id");
        }catch (NullPointerException e){
            e.printStackTrace();
            jsonRender.put("Code", 104);
            jsonRender.put("Msg", "app_id can't be empty!");
        }

        try {
            ybTestService.saveClusterDataToYbTest(appId);
        } catch (URISyntaxException|IOException|TableNameException e) {
            e.printStackTrace();
            jsonRender.put("Code", 103);
            jsonRender.put("Msg", e.getMessage());
        }

        return jsonRender;
    }



}
