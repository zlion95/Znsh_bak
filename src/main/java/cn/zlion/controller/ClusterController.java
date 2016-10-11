package cn.zlion.controller;

import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.service.ClusterService;
import cn.zlion.service.TableNameException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zzs on 10/8/16.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/cluster")
public class ClusterController {

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

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public Result getResult(HttpServletRequest request){

        Result jsonRender = new Result();
        String appId = request.getParameter("app_id");
        String resultTableName = request.getParameter("table");
        int page = 1, rows = 100;
        //分页的基本参数，根据需要自己设置需要的参数
        if (!(request.getParameter("page").equals("")||request.getParameter("page")==null)
                && !(request.getParameter("rows")==null||request.getParameter("rows").equals(""))){
            page = Integer.parseInt(request.getParameter("page"));
            rows = Integer.parseInt(request.getParameter("rows"));
        }
        try{
            PageResult pageResult = clusterService.getPageResultByTableName(appId, resultTableName, page, rows);
            jsonRender.put("Data", pageResult);
        }catch (TableNameException e){
            e.printStackTrace();
            jsonRender.put("Code", 103);
            jsonRender.put("Msg", e.getMessage());
        }
        return jsonRender;
    }

    @RequestMapping(value = "/result/recover", method = RequestMethod.POST)
    public Result recoverResult(HttpServletRequest request){
        Result jsonRender = new Result();



        return jsonRender;
    }

}
