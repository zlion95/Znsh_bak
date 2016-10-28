package cn.zlion.controller.HttpRequestUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple Get params builder.
 * Created by zzs on 10/9/16.
 */
public class RequestParamUtil {
    /**
     * Todo: 工具类的通用化
     * 1.Complete the encode for url creating.
     * 2.Complete uniform type of data transfer to url params.
     * TODO: 需要合并addparam 和 setparam方法。
     */
    private String URL = "";
    private Map<String, Object> paraments = new HashMap<String, Object>();

    public RequestParamUtil(String url) {
        this.URL = url;
    }

    public void addParam(Param param){
        addParam(param.getKey(), param.getValue());
    }

    public void addParam(String key, String value){
        paraments.put(key, value);
    }

    public void setParam(String key, String value){
        paraments.replace(key, value);
    }

    public void setParam(Param param){
        setParam(param.getKey(), param.getValue());
    }

    public void removeParam(String key) {
        paraments.remove(key);
    }


    public String getURI(){
        if (paraments.isEmpty()){
            return this.URL;
        }
        else{
            String uri = this.URL + "?";
            for (Map.Entry<String, Object> entry : paraments.entrySet()){
                uri = uri + entry.getKey() + "=" + entry.getValue() + "&";
            }
            return uri.substring(0, uri.length()-1);
        }
    }
}
