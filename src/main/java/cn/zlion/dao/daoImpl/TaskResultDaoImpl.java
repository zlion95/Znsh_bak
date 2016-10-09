package cn.zlion.dao.daoImpl;

import cn.zlion.dao.TaskResultDao;
import cn.zlion.dao.TaskResultRowMapper;
import cn.zlion.domain.TaskResult;
import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.pagenationUtil.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zzs on 10/9/16.
 */
@Repository
public class TaskResultDaoImpl implements TaskResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public PageResult findByPage(String app_id, int curPage, int pageRows) {
        String sql = "SELECT * FROM \"" + app_id + "\".\"" + TaskResult.TABLE_NAME + "\"";
        Pagination pagination = new Pagination(sql, jdbcTemplate, pageRows, curPage, new TaskResultRowMapper());
        return new PageResult(pagination.getTotalRows(), pagination.getResultList());
    }
}
