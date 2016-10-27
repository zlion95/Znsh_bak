package cn.zlion.domain;

import java.sql.Timestamp;

/**
 * Created by zzs on 10/8/16.
 */
public class JobResult {
    public static String TABLE_NAME = "T_RESULT_JOB";
    public static String TABLE_CREATE_SQL = "( pk VARCHAR(32) PRIMARY KEY NOT NULL," +
            " pk_t VARCHAR(32) NOT NULL," +
            " job_id VARCHAR(20) NOT NULL," +
            " msg VARCHAR(128)," +
            " summary CLOB," +
            " time_start TIMESTAMP WITH TIME ZONE NOT NULL," +
            " time_finish TIMESTAMP WITH TIME ZONE NOT NULL )";

    public static String TABLE_DATA_INSERT_SQL = "(pk, pk_t, job_id, msg, summary, time_start, time_finish) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String pk;

    private String pk_t;
    private String job_id;
    private String msg;
    private String summary;
    private Timestamp time_start;
    private Timestamp time_finish;

    public JobResult(String pk, String pk_t, String job_id, String msg, String summary, Timestamp time_start, Timestamp time_finish) {
        this.pk = pk;
        this.pk_t = pk_t;
        this.job_id = job_id;
        this.msg = msg;
        this.summary = summary;
        this.time_start = time_start;
        this.time_finish = time_finish;
    }

    public JobResult() {
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk_t() {
        return pk_t;
    }

    public void setPk_t(String pk_t) {
        this.pk_t = pk_t;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Timestamp getTime_start() {
        return time_start;
    }

    public void setTime_start(Timestamp time_start) {
        this.time_start = time_start;
    }

    public Timestamp getTime_finish() {
        return time_finish;
    }

    public void setTime_finish(Timestamp time_finish) {
        this.time_finish = time_finish;
    }

    @Override
    public String toString() {
        return "JobResultDao{" +
                "pk='" + pk + '\'' +
                ", pk_t='" + pk_t + '\'' +
                ", job_id='" + job_id + '\'' +
                ", msg='" + msg + '\'' +
                ", summary='" + summary + '\'' +
                ", time_start=" + time_start +
                ", time_finish=" + time_finish +
                '}';
    }
}

