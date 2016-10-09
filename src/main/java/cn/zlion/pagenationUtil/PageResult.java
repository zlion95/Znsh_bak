package cn.zlion.pagenationUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zzs on 10/9/16.
 */
public class PageResult extends HashMap<String, Object> {

    public PageResult(int totalRows, List paginationList) {
        put("totalRows", totalRows);
        put("data", paginationList);
    }
}
