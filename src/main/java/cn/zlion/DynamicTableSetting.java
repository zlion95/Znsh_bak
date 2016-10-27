package cn.zlion;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zzs on 10/17/16.
 */
@ConfigurationProperties(prefix = "dataTable")
public class DynamicTableSetting {

    private String tableName;
    private String tableField;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }
}
