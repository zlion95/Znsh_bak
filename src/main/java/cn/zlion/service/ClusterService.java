package cn.zlion.service;

import cn.zlion.dao.JobResultDao;
import cn.zlion.dao.RuleResultDao;
import cn.zlion.dao.TaskResultDao;
import cn.zlion.domain.JobResult;
import cn.zlion.domain.RuleResult;
import cn.zlion.domain.TaskResult;
import cn.zlion.pagenationUtil.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zzs on 10/11/16.
 */
@Service
public class ClusterService {

    private JobResultDao jobResultDao;
    private RuleResultDao ruleResultDao;
    private TaskResultDao taskResultDao;

    @Autowired
    public ClusterService(JobResultDao jobResultDao, RuleResultDao ruleResultDao, TaskResultDao taskResultDao) {
        this.jobResultDao = jobResultDao;
        this.ruleResultDao = ruleResultDao;
        this.taskResultDao = taskResultDao;
    }

    @Transactional
    public PageResult getPageResultByTableName(String appId, String resultTableName, int page, int rows) throws TableNameException{
        PageResult pageResult = null;
        if (resultTableName.equals(JobResult.TABLE_NAME)){
            pageResult = jobResultDao.findByPage(appId, page, rows);
        }
        else if (resultTableName.equals(TaskResult.TABLE_NAME)){
            pageResult = taskResultDao.findByPage(appId, page, rows);
        }
        else if (resultTableName.equals(RuleResult.TABLE_NAME)){
            pageResult = ruleResultDao.findByPage(appId, page, rows);
        }
        else{
            throw new TableNameException("Table Name not found!");
        }
        return pageResult;
    }
}
