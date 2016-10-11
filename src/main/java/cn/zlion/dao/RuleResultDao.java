package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;

/**
 * Created by zzs on 10/11/16.
 */
public interface RuleResultDao {

    public PageResult findByPage(String app_id, int curPage, int pageRows);

}
