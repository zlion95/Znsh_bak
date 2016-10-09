package cn.zlion.dao.domainMapper;

import cn.zlion.domain.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zzs on 10/8/16.
 */

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getBigDecimal("id"));
        course.setPk(rs.getString("pk"));
        course.setPk_j(rs.getString("pk_j"));
        course.setName(rs.getString("name"));
        course.setCurLearners(rs.getBigDecimal("curLearners"));
        course.setMaxLearners(rs.getBigDecimal("maxLearners"));

        return course;
    }

}