package cn.zlion.dao.daoImpl;

import cn.zlion.ResultHttpSetting;
import cn.zlion.dao.DataResultDao;
import cn.zlion.dao.domainMapper.JobResultRowMapper;
import cn.zlion.dao.domainMapper.RuleResultRowMapper;
import cn.zlion.dao.domainMapper.TaskResultRowMapper;
import cn.zlion.domain.JobResult;
import cn.zlion.domain.RuleResult;
import cn.zlion.domain.TaskResult;
import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.pagenationUtil.Pagination;
import cn.zlion.service.TableNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zzs on 10/13/16.
 */
@Repository
public class DataResultDaoImpl implements DataResultDao {

    @Autowired
    @Qualifier("postgresqlJdbcTemplate")
    private JdbcTemplate postgresqlJdbcTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleJdbcTemplate;


    /**
     * 分页查询应用对应的表数据, 静态审核结果表和动态用户表
     * @param app_id
     * @param curPage
     * @param pageRows
     * @param tableName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageResult findByPage(String app_id, int curPage, int pageRows, String tableName) {

        Pagination pagination = null;
        if (tableName.equals(JobResult.TABLE_NAME)){
            String sql = "SELECT * FROM \"" + app_id + "\".\"" + JobResult.TABLE_NAME + "\"";
            pagination = new Pagination(sql, postgresqlJdbcTemplate, pageRows, curPage, new JobResultRowMapper());
        }
        else if (tableName.equals(TaskResult.TABLE_NAME)){
            String sql = "SELECT * FROM \"" + app_id + "\".\"" + TaskResult.TABLE_NAME + "\"";
            pagination = new Pagination(sql, postgresqlJdbcTemplate, pageRows, curPage, new TaskResultRowMapper());
        }
        else if (tableName.equals(RuleResult.TABLE_NAME)){
            String sql = "SELECT * FROM \"" + app_id + "\".\"" + RuleResult.TABLE_NAME + "\"";
            pagination = new Pagination(sql, postgresqlJdbcTemplate, pageRows, curPage, new RuleResultRowMapper());
        }
        else{
            //动态表查询
        }

        return new PageResult(pagination.getTotalRows(), pagination.getTotalPages(),pagination.getResultList());
    }


    /**
     * 创建对应数据表的schema
     * @param appId
     * @param newSchemaPass
     * @throws TableNameException
     */
    @Override
    @Transactional
    public void createAppSchema(String appId, String newSchemaPass) throws TableNameException {
//        StringBuilder stringBuilder = new StringBuilder("CREATE USER \"" + appId + "\" IDENTIFIED BY " + newSchemaPass);
//        oracleJdbcTemplate.execute(stringBuilder.toString());
//
//        stringBuilder = new StringBuilder("GRANT CONNECT, RESOURCE TO \"" + appId + "\"");
//        oracleJdbcTemplate.execute(stringBuilder.toString());

        //调用存储过程创建数据表并赋予权限
        oracleJdbcTemplate.execute("CALL P_CREATE_SCHEMA('\"" + appId + "\"', '" + newSchemaPass +"')");
    }

    /**
     * 在对应schema中创建数据表
     * @param appId
     * @param tableName
     * @param isDynamic
     * @throws TableNameException
     */
    @Override
    @Transactional
    public void createTableOnAppSchema(String appId, String tableName, boolean isDynamic) throws TableNameException {

        StringBuilder stringBuilder = null;
        if (isDynamic){
            /**
             * 留给后面动态表的备份使用
             */
            return;
        }
        else{
            if (tableName.equals(TaskResult.TABLE_NAME)){
                stringBuilder = new StringBuilder("CREATE TABLE \"" + appId + "\".\"" + TaskResult.TABLE_NAME +"\" ");
                stringBuilder.append(TaskResult.TABLE_CREATE_SQL);
            }
            else if (tableName.equals(JobResult.TABLE_NAME)){
                stringBuilder = new StringBuilder("CREATE TABLE \"" + appId + "\".\"" + JobResult.TABLE_NAME +"\" ");
                stringBuilder.append(JobResult.TABLE_CREATE_SQL);
            }
            else if (tableName.equals(RuleResult.TABLE_NAME)){
                stringBuilder = new StringBuilder("CREATE TABLE \"" + appId + "\".\"" + RuleResult.TABLE_NAME +"\" ");
                stringBuilder.append(RuleResult.TABLE_CREATE_SQL);
            }
            else{
                throw new TableNameException("Table name aren't supports.");
            }
        }

        oracleJdbcTemplate.execute(stringBuilder.toString());
    }

    /**
     * 调用存储过程创建结果数据表
     * @param appId
     */
    @Override
    @Transactional
    public void createResultTableOnSchema(String appId){
        oracleJdbcTemplate.execute("CALL P_CREATE_RESULT_TABLE('\"" + appId + "\"')");
    }

    /**
     * 检查表在对应schema中是否存在数据表
     * @param appId
     * @param tableName
     * @return
     */
    @Override
    public boolean checkTableExist(String appId, String tableName){
        String sql = "select count(*) from dba_tables where owner=\'" +
                appId + "\' and table_name=\'" + tableName + "\'";
        int amount = oracleJdbcTemplate.queryForObject(sql, Integer.class);
        if (amount > 0)
            return true;
        else
            return false;
    }

    /**
     * 检查用户schema是否存在
     * @param appId
     * @return
     */
    @Override
    public boolean checkUserExist(String appId){
        String sql = "SELECT count(*) from DBA_USERS WHERE username=\'" + appId + "\'";
        int amount = oracleJdbcTemplate.queryForObject(sql, Integer.class);
        if (amount > 0)
            return true;
        else
            return false;
    }

    @Override
    public int[] updateData(List datas, String appId, String tableName) {

        int[] updateCounts = oracleJdbcTemplate.batchUpdate(
                "UPDATE ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, appId);
                        preparedStatement.setString(2, tableName);
                    }

                    @Override
                    public int getBatchSize() {
                        return datas.size();
                    }
                }
        );
        return updateCounts;
    }

    @Override
    @Transactional
    public int[] saveDate(final List datas, String appId, String tableName) {

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO \"" + appId + "\".\"" + tableName + "\" ");

        int[] insertCounts = null;
        if (tableName.equals(TaskResult.TABLE_NAME)){
            sqlBuilder.append(TaskResult.TABLE_DATA_INSERT_SQL);

            insertCounts = oracleJdbcTemplate.batchUpdate(
                    sqlBuilder.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            TaskResult taskResult = (TaskResult) datas.get(i);
                            preparedStatement.setString(1, taskResult.getPk());
                            preparedStatement.setString(2, taskResult.getApp_id());
                            preparedStatement.setShort(3, taskResult.getStatus());
                            preparedStatement.setString(4, taskResult.getMsg());
                            preparedStatement.setString(5, taskResult.getSummary());
                            preparedStatement.setTimestamp(6, taskResult.getTime_arrival());
                            preparedStatement.setTimestamp(7, taskResult.getTime_start());
                            preparedStatement.setTimestamp(8, taskResult.getTime_finish());
                        }

                        @Override
                        public int getBatchSize() {
                            return datas.size();
                        }
                    }
            );
        }
        else if (tableName.equals(JobResult.TABLE_NAME)){
            sqlBuilder.append(JobResult.TABLE_DATA_INSERT_SQL);

            insertCounts = oracleJdbcTemplate.batchUpdate(
                    sqlBuilder.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            JobResult jobResult = (JobResult) datas.get(i);
                            preparedStatement.setString(1, jobResult.getPk());
                            preparedStatement.setString(2, jobResult.getPk_t());
                            preparedStatement.setString(3, jobResult.getJob_id());
                            preparedStatement.setString(4, jobResult.getMsg());
                            preparedStatement.setString(5, jobResult.getSummary());
                            preparedStatement.setTimestamp(6, jobResult.getTime_start());
                            preparedStatement.setTimestamp(7, jobResult.getTime_finish());
                        }

                        @Override
                        public int getBatchSize() {
                            return datas.size();
                        }
                    }
            );
        }
        else if (tableName.equals(RuleResult.TABLE_NAME)){
            sqlBuilder.append(RuleResult.TABLE_DATA_INSERT_SQL);

            insertCounts = oracleJdbcTemplate.batchUpdate(
                    sqlBuilder.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            RuleResult ruleResult = (RuleResult) datas.get(i);
                            preparedStatement.setString(1, ruleResult.getPk());
                            preparedStatement.setString(2, ruleResult.getPk_j());
                            preparedStatement.setString(3, ruleResult.getRule_id());
                            preparedStatement.setString(4, ruleResult.getRule_info());
                            preparedStatement.setShort(5, ruleResult.getWarn_level());
                            preparedStatement.setString(6, ruleResult.getAdvice());
                            preparedStatement.setString(7, ruleResult.getMsg());
                            preparedStatement.setString(8, ruleResult.getReturns());
                        }

                        @Override
                        public int getBatchSize() {
                            return datas.size();
                        }
                    }
            );
        }
        else{
            /**
             * 动态表使用
             */
        }

        return insertCounts;
    }


    @Override
    public int totalInTable(String appId, String table) {
        String sql = "Select count(*) from \"" + appId + "\".\"" + table + "\"";
        return oracleJdbcTemplate.queryForObject(sql, Integer.class);
    }
}
