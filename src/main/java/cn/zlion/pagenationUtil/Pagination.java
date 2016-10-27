package cn.zlion.pagenationUtil;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zzs on 10/8/16.
 */
public class Pagination {

    private int numPerPage;

    private int totalRows;

    private int totalPages;

    private int currentPage;

    private int beginLine;

    private int endLine;

    private List resultList;

    private Map<String, Object> resultMap;

    public Pagination(String sql, JdbcTemplate jdbcTemplate, int numPerPage, int currentPage, RowMapper rowMapper) {

        if (jdbcTemplate == null){
            throw new IllegalArgumentException("JdbcTemplate can't be null");
        }
        else if (sql==null || "".equals(sql)){
            throw new IllegalArgumentException("SQL statement can't be empty");
        }

        this.numPerPage = numPerPage;
        this.currentPage = currentPage;

        //获取查询数据的总行数
        String totalsql = "SELECT count(*) FROM (" + sql + ") AS total";
        try{
            setTotalRows(jdbcTemplate.queryForObject(totalsql, Integer.class));
        }catch (Exception e){
            e.printStackTrace();
        }

        setTotalPages();

        setBeginLine();

        setEndLine();

        String paginationSql = sql + " limit " + numPerPage + " offset " + getBeginLine();

        try{
            setResultList(jdbcTemplate.query(paginationSql, rowMapper));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pagination(String sql, JdbcTemplate jdbcTemplate, int numPerPage, int currentPage) {

        if (jdbcTemplate == null){
            throw new IllegalArgumentException("JdbcTemplate can't be null");
        }
        else if (sql==null || "".equals(sql)){
            throw new IllegalArgumentException("SQL statement can't be empty");
        }

        this.numPerPage = numPerPage;
        this.currentPage = currentPage;

        //获取查询数据的总行数
        String totalsql = "SELECT count(*) FROM (" + sql + ") AS total";
        try{
            setTotalRows(jdbcTemplate.queryForObject(totalsql, Integer.class));
        }catch (Exception e){
            e.printStackTrace();
        }

        setTotalPages();

        setBeginLine();

        setEndLine();

        String paginationSql = sql + " ORDER BY pk" + " limit " + numPerPage + " offset " + getBeginLine();

        try{
            setResultList(jdbcTemplate.queryForList(paginationSql));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages() {
        if (totalRows % numPerPage == 0){
            this.totalPages = totalRows / numPerPage;
        }
        else{
            this.totalPages = totalRows / numPerPage + 1;
        }
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getBeginLine() {
        return beginLine;
    }

    public void setBeginLine() {
        this.beginLine = (currentPage - 1) * numPerPage;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine() {
        if (totalRows < numPerPage){
            this.endLine = totalRows;
        }
        else if ((totalRows % numPerPage == 0) || (totalRows % numPerPage != 0 && currentPage < totalPages)){
            this.endLine = currentPage * numPerPage;
        }
        else{
            this.endLine = totalRows;
        }
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }


    public void setResultMap(Map<String, Object> resultMap){
        this.resultMap = resultMap;
    }

    public Map<String, Object> getResultMap(){
        return resultMap;
    }

}
