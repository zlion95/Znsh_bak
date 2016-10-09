package cn.zlion.domain;

import java.sql.Timestamp;

/**
 * Created by zzs on 10/8/16.
 */
public class TaskResult {
    public static String TABLE_NAME = "T_RESULT_TASK";

    private String pk;

    private String app_id;
    private short status;
    private String msg;
    private String summary;
    private Timestamp time_start;
    private Timestamp time_finish;
    private Timestamp time_arrival;

    public TaskResult(String pk, String app_id, short status, String msg, String summary, Timestamp time_start, Timestamp time_finish, Timestamp time_arrival) {
        this.pk = pk;
        this.app_id = app_id;
        this.status = status;
        this.msg = msg;
        this.summary = summary;
        this.time_start = time_start;
        this.time_finish = time_finish;
        this.time_arrival = time_arrival;
    }

    public TaskResult() {
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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

    public Timestamp getTime_arrival() {
        return time_arrival;
    }

    public void setTime_arrival(Timestamp time_arrival) {
        this.time_arrival = time_arrival;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "pk='" + pk + '\'' +
                ", app_id='" + app_id + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", summary='" + summary + '\'' +
                ", time_start=" + time_start +
                ", time_finish=" + time_finish +
                ", time_arrival=" + time_arrival +
                '}';
    }
}



