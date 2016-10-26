package com.gwi.mail.entity;

/**
 * Created by Administrator on 2016-10-25.
 */
public class AttendanceEntity {
    // 工号
    private String jobNumber;
    // 打卡时间
    private String clock;

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
