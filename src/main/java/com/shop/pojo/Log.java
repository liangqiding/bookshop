package com.shop.pojo;

public class Log {
  private   Integer logid;
  private  String log_id;
  private  String log_date;
  private String log_name;
    private String log_state;
    private  String log;
    private Integer log_orderid;

    public Integer getLog_orderid() {
        return log_orderid;
    }

    public void setLog_orderid(Integer log_orderid) {
        this.log_orderid = log_orderid;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getLog_date() {
        return log_date;
    }

    public void setLog_date(String log_date) {
        this.log_date = log_date;
    }

    public String getLog_name() {
        return log_name;
    }

    public void setLog_name(String log_name) {
        this.log_name = log_name;
    }

    public String getLog_state() {
        return log_state;
    }

    public void setLog_state(String log_state) {
        this.log_state = log_state;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logid=" + logid +
                ", log_id='" + log_id + '\'' +
                ", log_date='" + log_date + '\'' +
                ", log_name='" + log_name + '\'' +
                ", log_state='" + log_state + '\'' +
                ", log='" + log + '\'' +
                ", log_orderid=" + log_orderid +
                '}';
    }
}
