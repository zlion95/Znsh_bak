package cn.zlion.dao.daoImpl;

import cn.zlion.dao.JobResultDao;
import cn.zlion.dao.domainMapper.JobResultRowMapper;
import cn.zlion.domain.JobResult;
import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.pagenationUtil.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by zzs on 10/9/16.
 */
@Repository
public class JobResultDaoImpl implements JobResultDao{

    @Autowired
    @Qualifier("postgresqlJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    @Qualifier("oracleJdbcTemplate")
//    private JdbcTemplate oracleJdbcTemplate;

    @Override
    public PageResult findByPage(String app_id, int curPage, int pageRows) {
        String sql = "SELECT * FROM \"" + app_id + "\".\"" + JobResult.TABLE_NAME + "\"";
        Pagination pagination = new Pagination(sql, jdbcTemplate, pageRows, curPage, new JobResultRowMapper());
        return new PageResult(pagination.getTotalRows(), pagination.getTotalPages(),pagination.getResultList());
    }

}
