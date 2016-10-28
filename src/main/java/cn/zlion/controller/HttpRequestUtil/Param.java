package cn.zlion.controller.HttpRequestUtil;

import java.util.List;

/**
 * An entity for for Get method uri builder.
 * Created by zzs on 10/12/16.
 */
public class Param {


    private String key;
    private String value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, int value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(int value){
        this.value = String.valueOf(value);
    }

    public String getUriParam(){
        return key + "=" + value;
    }


    public Param(String key, List<String> values){
        this.key = key;

    }
}
