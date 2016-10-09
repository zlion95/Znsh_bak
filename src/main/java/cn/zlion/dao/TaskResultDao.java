package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;

/**
 * Created by zzs on 10/8/16.
 */
public interface TaskResultDao {

    public PageResult findByPage(String app_id, int page, int rows);


}
