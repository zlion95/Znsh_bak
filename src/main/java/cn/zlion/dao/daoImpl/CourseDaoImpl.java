package cn.zlion.dao.daoImpl;

import cn.zlion.dao.CourseDao;
import cn.zlion.dao.CourseRowMapper;
import cn.zlion.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zzs on 10/8/16.
 */
@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll(String app_id) {
        return jdbcTemplate.query("Select * from \""+app_id+"\".\""+Course.TABLE_NAME+"\"", new CourseRowMapper());
    }

    @Override
    public int findAmount(String app_id) {
        return jdbcTemplate.queryForObject("select count(*) from \""+app_id+"\".\""+Course.TABLE_NAME+"\"", Integer.class);
    }
}
