package cn.zlion.dao;

import cn.zlion.domain.JobResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zzs on 10/9/16.
 */
public class JobResultRowMapper implements RowMapper<JobResult> {

    @Override
    public JobResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        JobResult jobResult = new JobResult();

        jobResult.setPk(rs.getString("pk"));
        jobResult.setPk_t(rs.getString("pk_t"));
        jobResult.setTime_start(rs.getTimestamp("time_start"));
        jobResult.setTime_finish(rs.getTimestamp("time_finish"));
        jobResult.setMsg(rs.getString("msg"));
        jobResult.setSummary(rs.getString("summary"));
        jobResult.setJob_id(rs.getString("job_id"));

        return jobResult;
    }
}
