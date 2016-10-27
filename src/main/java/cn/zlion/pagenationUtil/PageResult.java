package cn.zlion.pagenationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzs on 10/9/16.
 */
public class PageResult extends HashMap<String, Object> {

    public PageResult(int totalRows, int totalPages, List paginationList) {
        put("totalRows", totalRows);
        put("totalPages", totalPages);
        put("data", paginationList);
    }

    public PageResult(int totalRows, int totalPages, Map<String, Object> paginationMap){
        put("totalRows", totalRows);
        put("totalPages", totalPages);
        put("data", paginationMap);
    }
}
