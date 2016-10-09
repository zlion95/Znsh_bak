package cn.zlion.controller;

import cn.zlion.dao.CourseDao;
import cn.zlion.dao.TaskResultDao;
import cn.zlion.domain.Course;
import cn.zlion.pagenationUtil.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by zzs on 10/8/16.
 */
@RestController
@EnableAutoConfiguration
public class ClusterController {

    private CourseDao courseDao;
    private TaskResultDao taskResultDao;

    public ClusterController(CourseDao courseDao, TaskResultDao taskResultDao) {
        this.courseDao = courseDao;
        this.taskResultDao = taskResultDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> home(){

//        return courseDao.getByPk("e1a46ceaf6e144b38eee4ae9049a1de8").toString();
//        List<Course> courses = courseDao.findAll("TestApp");
//        return courses.toString();
        PageResult pageResult = taskResultDao.findByPage("TestApp", 1, 100);
        return pageResult;
    }

}
