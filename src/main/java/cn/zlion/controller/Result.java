package cn.zlion.controller;

import java.util.HashMap;

/**
 * Created by zzs on 10/9/16.
 */
public class Result extends HashMap<String, Object> {

    public Result() {
        put("Code", 100);
        put("Msg", "OK");
    }

}