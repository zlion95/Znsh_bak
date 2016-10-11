package cn.zlion.dao.daoImpl;

import cn.zlion.dao.RuleResultDao;
import cn.zlion.dao.domainMapper.JobResultRowMapper;
import cn.zlion.dao.domainMapper.RuleResultRowMapper;
import cn.zlion.domain.RuleResult;
import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.pagenationUtil.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by zzs on 10/11/16.
 */
@Repository
public class RuleResultDaoImpl implements RuleResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PageResult findByPage(String app_id, int curPage, int pageRows) {
        String sql = "SELECT * FROM \"" + app_id + "\".\"" + RuleResult.TABLE_NAME + "\"";
        Pagination pagination = new Pagination(sql, jdbcTemplate, pageRows, curPage, new RuleResultRowMapper());
        return new PageResult(pagination.getTotalRows(), pagination.getTotalPages(),pagination.getResultList());
    }
}
