package cn.zlion.dao;

import cn.zlion.domain.TaskResult;
import cn.zlion.pagenationUtil.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zzs on 10/8/16.
 */
public interface TaskResultDao {

    public PageResult findByPage(String app_id, int page, int rows);


}
