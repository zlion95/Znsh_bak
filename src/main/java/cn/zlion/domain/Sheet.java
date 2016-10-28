package cn.zlion.domain;

import uestc.ercl.znsh.common.data.DataUtil;
//import uestc.ercl.znsh.common.entity.Field;
import cn.zlion.domain.Field;
//import uestc.ercl.znsh.common.exception.ZNSH_IllegalFieldValueException;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by zzs on 10/27/16.
 */
@Entity
@Table(
        name = "T_SHEET"
)
public class Sheet {
    public static final String TABLE_NAME = "T_SHEET";
    @Id
    @GeneratedValue
    @Column(
            name = "pk"
    )
    private long pk;
    @Column(
            name = "app_id",
            nullable = false
    )
    private String appId;
    @Column(
            name = "id",
            nullable = false,
            length = 32
    )
    private String id;
    @Column(
            name = "name",
            nullable = false,
            length = 32
    )
    private String name;
    @Column(
            name = "descr",
            nullable = true,
            length = 255
    )
    private String desc;
    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "tablePk"
    )
    private List<Field> fields;

    public Sheet() {
    }

    public String toString() {
        return "Sheet{pk=" + this.pk + ", appId=\'" + this.appId + '\'' + ", id=\'" + this.id + '\'' + ", name=\'" + this.name + '\'' + ", desc=\'" + this.desc + '\'' + ", fields=" + this.fields + '}';
    }

    public long getPk() {
        return this.pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        if(this.fields == null) {
            this.fields = new ArrayList();
        }

        this.fields.add(field);
    }
}
