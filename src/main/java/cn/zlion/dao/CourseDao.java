package cn.zlion.dao;

import cn.zlion.domain.Course;

import java.util.List;

/**
 * Created by zzs on 10/8/16.
 */
public interface CourseDao {

    public List<Course> findAll(String app_id);
    public int findAmount(String app_id);
//    public JdbcTemplate getJt();

}
