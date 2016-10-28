package cn.zlion.dao;

import cn.zlion.pagenationUtil.PageResult;
import cn.zlion.service.TableNameException;

import java.util.Date;
import java.util.List;

/**
 * Created by zzs on 10/13/16.
 */
public interface DataResultDao {

//    public PageResult findByPage(String app_id, int curPage, int pageRows, String tableName);

    public void createAppSchema(String appId, String newSchemaPass) throws TableNameException;

    public boolean checkTableExist(String appId, String tableName);

    public int[] updateData(final List datas, String appId, String tableName);

    public int[] saveDate(final List datas, String appId, String tableName);

    public boolean checkUserExist(String appId);

    public void createTableOnAppSchema(String appId, String tableName, boolean isDynamic) throws TableNameException;

    public void createResultTableOnSchema(String appId);

    public int totalInTable(String appId, String table);

    //后面需要将这个方法改为从配置表中读取，直接通过全局的检索效率太差。
    Date getTaskLastUpdateTime(String appId);
}

