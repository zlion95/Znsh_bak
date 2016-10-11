package cn.zlion.dao.domainMapper;

import cn.zlion.domain.RuleResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zzs on 10/11/16.
 */
public class RuleResultRowMapper implements RowMapper<RuleResult> {

    @Override
    public RuleResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        RuleResult ruleResult = new RuleResult();

        ruleResult.setPk(rs.getString("pk"));
        ruleResult.setPk_j(rs.getString("pk_j"));
        ruleResult.setRule_id(rs.getString("rule_id"));
        ruleResult.setRule_info(rs.getString("rule_info"));
        ruleResult.setWarn_level(rs.getShort("warn_level"));
        ruleResult.setAdvice(rs.getString("advice"));
        ruleResult.setMsg(rs.getString("msg"));
        ruleResult.setReturns(rs.getString("returns"));

        return ruleResult;
    }
}