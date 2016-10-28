package cn.zlion.controller;

import cn.zlion.domain.Field;
import cn.zlion.domain.Sheet;
import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.service.ClusterService;
import cn.zlion.service.TableNameException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uestc.ercl.znsh.common.constant.DataType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by zzs on 10/8/16.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/cluster")
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ClusterController {

    private ClusterService clusterService;

    @Autowired
    public ClusterController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result home(){
        Result jsonRender = new Result();
        return jsonRender;
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public Result getResult(HttpServletRequest request){

        Result jsonRender = new Result();
        String appId = request.getParameter("app_id");
        String resultTableName = request.getParameter("table");
        String bakTimeString = null;
        Date baktime = new Date();
        try{
            appId = URLDecoder.decode(appId, "UTF-8");
            resultTableName = URLDecoder.decode(resultTableName, "UTF-8");
            if (request.getParameter("update-time") != null && !request.getParameter("update-time").equals("")){
                bakTimeString = URLDecoder.decode(request.getParameter("update-time"), "UTF-8");
                baktime.setTime(Long.parseLong(bakTimeString));
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            jsonRender.put("Code", 102);
            jsonRender.put("Msg", e.getMessage());
            return jsonRender;
        }
        int page = 1, rows = 100;
        //分页的基本参数，根据需要自己设置需要的参数
        if (!(request.getParameter("page")==null||request.getParameter("page").equals(""))
                && !(request.getParameter("rows")==null||request.getParameter("rows").equals(""))){
            page = Integer.parseInt(request.getParameter("page"));
            rows = Integer.parseInt(request.getParameter("rows"));
        }
        try{
            PageResult pageResult = null;
            if (bakTimeString == null){
                pageResult = clusterService.getPageResultByTableName(appId, resultTableName, page, rows);
            }
            else{
                pageResult = clusterService.getPageResultByTableNameAndTime(appId, resultTableName, page, rows, baktime);
            }
            jsonRender.put("Data", pageResult);
            jsonRender.put("Code", 200);
        }catch (TableNameException e){
            e.printStackTrace();
            jsonRender.put("Code", 103);
            jsonRender.put("Msg", e.getMessage());
        }
        return jsonRender;
    }


//    @RequestMapping(value = "/newData", method = RequestMethod.GET)
//    public Result getResultByUpdateTime(HttpServletRequest request){
//
//        Result jsonRender = new Result();
//        String appId = request.getParameter("app_id");
//        String resultTableName = request.getParameter("table");
//        String lastBackupTimeString = request.getParameter("update-time");
//        Date baktime = new Date();
//        try{
//            appId = URLDecoder.decode(appId, "UTF-8");
//            resultTableName = URLDecoder.decode(resultTableName, "UTF-8");
//            lastBackupTimeString = URLDecoder.decode(lastBackupTimeString, "UTF-8");
//            baktime.setTime(Long.parseLong(lastBackupTimeString));
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//            jsonRender.put("Code", 102);
//            jsonRender.put("Msg", e.getMessage());
//            return jsonRender;
//        }
//        int page = 1, rows = 100;
//        //分页的基本参数，根据需要自己设置需要的参数
//        if (!(request.getParameter("page")==null||request.getParameter("page").equals(""))
//                && !(request.getParameter("rows")==null||request.getParameter("rows").equals(""))){
//            page = Integer.parseInt(request.getParameter("page"));
//            rows = Integer.parseInt(request.getParameter("rows"));
//        }
//        try{
//            PageResult pageResult = clusterService.getPageResultByTableNameAndTime(appId, resultTableName, page, rows, baktime);
//            jsonRender.put("Data", pageResult);
//            jsonRender.put("Code", 200);
//        }catch (TableNameException e){
//            e.printStackTrace();
//            jsonRender.put("Code", 103);
//            jsonRender.put("Msg", e.getMessage());
//        }
//
//        return jsonRender;
//    }





}
