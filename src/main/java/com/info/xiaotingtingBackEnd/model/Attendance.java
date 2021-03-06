package com.info.xiaotingtingBackEnd.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) 2018, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2018/1/3 13:42:08
 * Description：考勤打卡信息
 * Email: xiaoting233zhang@126.com
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(generator = "attendanceIdGenerator")
    @GenericGenerator(name = "attendanceIdGenerator", strategy = "com.info.xiaotingtingBackEnd.model.base.IdGenerator")
    @Column(columnDefinition = "char(20)", nullable = false)
    private String attendanceId;

    private String teamId;

    private String userId;

    private String punchInPicture;//上班照片

    private String punchOutPicture;//下班照片

    private String punchInAddress;//上班地址

    private String punchOutAddress;//下班地址

    private Date punchInTime;//上班打卡时间

    private Date punchOutTime;//下班打卡时间

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPunchInPicture() {
        return punchInPicture;
    }

    public void setPunchInPicture(String punchInPicture) {
        this.punchInPicture = punchInPicture;
    }

    public String getPunchOutPicture() {
        return punchOutPicture;
    }

    public void setPunchOutPicture(String punchOutPicture) {
        this.punchOutPicture = punchOutPicture;
    }

    public String getPunchInAddress() {
        return punchInAddress;
    }

    public void setPunchInAddress(String punchInAddress) {
        this.punchInAddress = punchInAddress;
    }

    public String getPunchOutAddress() {
        return punchOutAddress;
    }

    public void setPunchOutAddress(String punchOutAddress) {
        this.punchOutAddress = punchOutAddress;
    }

    public Date getPunchInTime() {
        return punchInTime;
    }

    public void setPunchInTime(Date punchInTime) {
        this.punchInTime = punchInTime;
    }

    public Date getPunchOutTime() {
        return punchOutTime;
    }

    public void setPunchOutTime(Date punchOutTime) {
        this.punchOutTime = punchOutTime;
    }
}
