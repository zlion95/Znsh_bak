package cn.zlion.domain;

import uestc.ercl.znsh.common.constant.DataType;
import uestc.ercl.znsh.common.data.DataUtil;

import javax.persistence.*;

/**
 * Created by zzs on 10/27/16.
 */
@Entity
@Table(
        name = "T_FIELD"
)
public class Field {
    public static final String TABLE_NAME = "T_FIELD";
    @Id
    @GeneratedValue
    @Column(
            name = "pk"
    )
    private long pk;
    @Column(
            name = "sheet_pk",
            nullable = false
    )
    private long sheetPk;
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
    @Column(
            name = "type",
            nullable = false,
            length = 8
    )
    private String type;
    @Column(
            name = "nullable",
            nullable = false
    )
    private boolean nullable;

    public Field() {
    }

    public String toString() {
        return "Field{pk=" + this.pk + ", sheetPk=" + this.sheetPk + ", id=\'" + this.id + '\'' + ", name=\'" + this.name + '\'' + ", desc=\'" + this.desc + '\'' + ", type=\'" + this.type + '\'' + ", nullable=" + this.nullable + '}';
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

    public long getSheetPk() {
        return this.sheetPk;
    }

    public void setSheetPk(long sheetPk) {
        this.sheetPk = sheetPk;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = DataType.valueOf(type).name();
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public DataType getDataType() {
        return DataType.valueOf(this.type);
    }

    public void setDataType(DataType dataType) {
        this.type = dataType.name();
    }
}
