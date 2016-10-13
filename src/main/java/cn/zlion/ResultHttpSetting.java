package cn.zlion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zzs on 10/11/16.
 */
@ConfigurationProperties(prefix = "resultHttp")
public class ResultHttpSetting {

    private String url;
    private HttpMethod httpMethod;
    private int rowsForPerPage;
    private List<String> tables;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod){
        httpMethod = httpMethod.toLowerCase();
        if (httpMethod == "get"){
            this.httpMethod = HttpMethod.GET;
        }
        else if (httpMethod == "post"){
            this.httpMethod = HttpMethod.POST;
        }
        else if (httpMethod == "put"){
            this.httpMethod = HttpMethod.PUT;
        }
        else if (httpMethod == "delete"){
            this.httpMethod = HttpMethod.DELETE;
        }
        else if (httpMethod == "patch"){
            this.httpMethod = HttpMethod.PATCH;
        }
        else if (httpMethod == "head"){
            this.httpMethod = HttpMethod.HEAD;
        }
        else{
            this.httpMethod = HttpMethod.GET;
        }
    }

    public void setTables(String string){
        this.tables = Arrays.asList(string.split(","));
    }

    public List<String> getTables(){
        return tables;
    }

    public int getRowsForPerPage() {
        return rowsForPerPage;
    }

    public void setRowsForPerPage(int rowsForPerPage) {
        this.rowsForPerPage = rowsForPerPage;
    }
}
