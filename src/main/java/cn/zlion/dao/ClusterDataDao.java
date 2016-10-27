package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;

/**
 * Created by zzs on 10/25/16.
 */
public interface ClusterDataDao {

    public PageResult findByPage(String app_id, int curPage, int pageRows, String tableName);


    public boolean checkTableExist(String appId, String tableName);
}
