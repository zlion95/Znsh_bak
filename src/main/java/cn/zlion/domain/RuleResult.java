package cn.zlion.domain;

/**
 * Created by zzs on 10/8/16.
 */
public class RuleResult {
    public static String TABLE_NAME = "T_RESULT_RULE";
    public static String TABLE_CREATE_SQL = "( pk VARCHAR(32) PRIMARY KEY NOT NULL," +
            " pk_j VARCHAR(32) NOT NULL," +
            " rule_id VARCHAR(20) NOT NULL," +
            " rule_info VARCHAR(256) NOT NULL," +
            " warn_level SMALLINT NOT NULL," +
            " advice VARCHAR(128)," +
            " msg VARCHAR(128)," +
            " returns CLOB )";
    public static String TABLE_DATA_INSERT_SQL = "(pk, pk_j, rule_id, rule_info, warn_level, advice, msg, returns) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private String pk;
    private String pk_j;
    private String rule_id;
    private String rule_info;
    private short warn_level;
    private String advice;
    private String msg;
    private String returns;

    public RuleResult(String pk, String pk_j, String rule_id, String rule_info, short warn_level, String advice, String msg, String returns) {
        this.pk = pk;
        this.pk_j = pk_j;
        this.rule_id = rule_id;
        this.rule_info = rule_info;
        this.warn_level = warn_level;
        this.advice = advice;
        this.msg = msg;
        this.returns = returns;
    }

    public RuleResult() {
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

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public String getRule_info() {
        return rule_info;
    }

    public void setRule_info(String rule_info) {
        this.rule_info = rule_info;
    }

    public short getWarn_level() {
        return warn_level;
    }

    public void setWarn_level(short warn_level) {
        this.warn_level = warn_level;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    @Override
    public String toString() {
        return "RuleResult{" +
                "pk='" + pk + '\'' +
                ", pk_j='" + pk_j + '\'' +
                ", rule_id='" + rule_id + '\'' +
                ", rule_info='" + rule_info + '\'' +
                ", warn_level=" + warn_level +
                ", advice='" + advice + '\'' +
                ", msg='" + msg + '\'' +
                ", returns='" + returns + '\'' +
                '}';
    }
}



