package cn.zlion.dao.domainMapper;

import cn.zlion.domain.TaskResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zzs on 10/9/16.
 */
public class TaskResultRowMapper implements RowMapper<TaskResult> {

    @Override
    public TaskResult mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaskResult taskResult = new TaskResult();

        taskResult.setPk(rs.getString("pk"));
        taskResult.setApp_id(rs.getString("app_id"));
        taskResult.setMsg(rs.getString("msg"));
        taskResult.setStatus(rs.getShort("status"));
        taskResult.setSummary(rs.getString("summary"));
        taskResult.setTime_arrival(rs.getTimestamp("time_arrival"));
        taskResult.setTime_finish(rs.getTimestamp("time_finish"));
        taskResult.setTime_start(rs.getTimestamp("time_start"));

        return taskResult;
    }
}
