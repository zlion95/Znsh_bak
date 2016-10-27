package cn.zlion;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zzs on 10/14/16.
 */
@ConfigurationProperties(prefix = "schemaSetting")
public class SchemaSetting {

    private String newSchemaPass;

    private String originSchemaName;

    public String getNewSchemaPass() {
        return newSchemaPass;
    }

    public void setNewSchemaPass(String newSchemaPass) {
        this.newSchemaPass = newSchemaPass;
    }

    public String getOriginSchemaName() {
        return originSchemaName;
    }

    public void setOriginSchemaName(String originSchemaName) {
        this.originSchemaName = originSchemaName;
    }
}
