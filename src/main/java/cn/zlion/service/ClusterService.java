package cn.zlion.service;

import cn.zlion.ResultHttpSetting;
import cn.zlion.dao.ClusterDataDao;
import cn.zlion.dao.DataResultDao;
import cn.zlion.domain.Field;
import cn.zlion.domain.Sheet;
import cn.zlion.pagenationUtil.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uestc.ercl.znsh.common.constant.DataType;

import java.util.*;

/**
 * Created by zzs on 10/11/16.
 */
@Service
public class ClusterService {

//    private final static SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DataResultDao dataResultDao;
    private ClusterDataDao clusterDataDao;
    @Autowired
    private ResultHttpSetting resultHttpSetting;

    @Autowired
    public ClusterService(DataResultDao dataResultDao, ClusterDataDao clusterDataDao) {
        this.dataResultDao = dataResultDao;
        this.clusterDataDao = clusterDataDao;
    }

    @Transactional
    public PageResult getPageResultByTableName(String appId, String resultTableName, int page, int rows) throws TableNameException{
        List<String> tables = resultHttpSetting.getTables();
        if (tables.contains(resultTableName)){
            //获取静态表审核结果表的数据
            PageResult pageResult = clusterDataDao.findByPage(appId, page, rows, resultTableName);
            return pageResult;
        }
        else{
            //获取动态表的数据
            PageResult pageResult = getResultSetByTableName(appId, resultTableName, page, rows);
            return pageResult;
        }
    }


    public PageResult getResultSetByTableName(String appId, String dynamicTableName, int page, int rows) throws TableNameException{

        PageResult pageResult = null;

        if (clusterDataDao.checkTableExist(appId, dynamicTableName)){
            pageResult = clusterDataDao.findByPage(appId, page, rows, dynamicTableName);
        }
        else{
            throw new TableNameException("Table named " + dynamicTableName + " isn't exist!");
        }

        return pageResult;
    }


    public PageResult getPageResultByTableNameAndTime(String appId, String tableName, int page, int rows, Date updateTime)
        throws TableNameException{

        PageResult pageResult = null;
        if (clusterDataDao.checkTableExist(appId, tableName)){
            pageResult = clusterDataDao.findByTimeAndPage(appId, page, rows, tableName, updateTime);

        }else{
            throw new TableNameException("Table named " + tableName + " isn't exist!");
        }

        return pageResult;
    }

    public PageResult getPageResultByTableNameAndPk(String appId, String tableName, int page, int rows, String pk)
        throws TableNameException{

        PageResult pageResult = null;
        if (clusterDataDao.checkTableExist(appId, tableName)){
            pageResult = clusterDataDao.findResultByPk(appId, page, rows, tableName, pk);

        }else{
            throw new TableNameException("Table named " + tableName + " isn't exist!");
        }

        return pageResult;
    }




}
