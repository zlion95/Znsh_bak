package cn.zlion.domain;

import java.math.BigDecimal;

/**
 * Created by zzs on 9/30/16.
 */
//@Entity
//@Table
public class Course {
    public static String TABLE_NAME = "Course";

    private String pk;

    private String pk_j;
    private BigDecimal id;
    private String name;
    private BigDecimal maxLearners;
    private BigDecimal curLearners;

    public Course(String pk, String pk_j, BigDecimal id, String name, BigDecimal maxLearners, BigDecimal curLearners) {
        this.pk = pk;
        this.pk_j = pk_j;
        this.id = id;
        this.name = name;
        this.maxLearners = maxLearners;
        this.curLearners = curLearners;
    }

    public Course() {
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk_j() {
        return pk_j;
    }

    public void setPk_j(String pk_j) {
        this.pk_j = pk_j;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaxLearners() {
        return maxLearners;
    }

    public void setMaxLearners(BigDecimal maxLearners) {
        this.maxLearners = maxLearners;
    }

    public BigDecimal getCurLearners() {
        return curLearners;
    }

    public void setCurLearners(BigDecimal curLearners) {
        this.curLearners = curLearners;
    }

    @Override
    public String toString() {
        return "Course{" +
                "pk='" + pk + '\'' +
                ", pk_j='" + pk_j + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", maxLearners=" + maxLearners +
                ", curLearners=" + curLearners +
                '}';
    }
}
