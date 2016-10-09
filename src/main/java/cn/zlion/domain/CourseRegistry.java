package cn.zlion.domain;

import java.math.BigDecimal;

/**
 * Created by zzs on 10/8/16.
 */
public class CourseRegistry {

    private String pk;
    private String pk_j;
    private BigDecimal courseId;
    private String stuName;
    private BigDecimal stuGrade;
    private BigDecimal stuClass;

    public CourseRegistry(String pk, String pk_j, BigDecimal courseId, String stuName, BigDecimal stuGrade, BigDecimal stuClass) {
        this.pk = pk;
        this.pk_j = pk_j;
        this.courseId = courseId;
        this.stuName = stuName;
        this.stuGrade = stuGrade;
        this.stuClass = stuClass;
    }

    public CourseRegistry() {
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

    public BigDecimal getCourseId() {
        return courseId;
    }

    public void setCourseId(BigDecimal courseId) {
        this.courseId = courseId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public BigDecimal getStuGrade() {
        return stuGrade;
    }

    public void setStuGrade(BigDecimal stuGrade) {
        this.stuGrade = stuGrade;
    }

    public BigDecimal getStuClass() {
        return stuClass;
    }

    public void setStuClass(BigDecimal stuClass) {
        this.stuClass = stuClass;
    }

    @Override
    public String toString() {
        return "CourseRegistry{" +
                "pk='" + pk + '\'' +
                ", pk_j='" + pk_j + '\'' +
                ", courseId=" + courseId +
                ", stuName='" + stuName + '\'' +
                ", stuGrade=" + stuGrade +
                ", stuClass=" + stuClass +
                '}';
    }
}
