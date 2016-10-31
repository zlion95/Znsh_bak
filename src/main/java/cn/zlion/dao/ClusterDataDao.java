package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;

import java.util.Date;

/**
 * Created by zzs on 10/25/16.
 */
public interface ClusterDataDao {

    public PageResult findByPage(String app_id, int curPage, int pageRows, String tableName);

//    public PageResult findByTimeAndPage(String app_id, int curPage, int pageRows, String tableName, Date lastUpdateTime);

    //只有TaskResult
    public PageResult findByTimeAndPage(String app_id, int curPage, int pageRows, String tableName, Date lastUpdateTime, String foreignPk);

    public PageResult findResultByPk(String app_id, int curPage, int pageRows, String tableName, String pk);

    public boolean checkTableExist(String appId, String tableName);
}
