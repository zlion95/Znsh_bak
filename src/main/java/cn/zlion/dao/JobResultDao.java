package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;

/**
 * Created by zzs on 10/9/16.
 */
public interface JobResultDao {

    public PageResult findByPage(String app_id, int curPage, int pageRows);
}
