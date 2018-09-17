package com.face.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_log")
public class UserLog {

    @Id
    private String id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "loginTime")
    private Date loginTime;

    @Column(name = "sessionId")
    private String sessionId;

    public UserLog() {
    }

    public UserLog(String userId, Date loginTime, String sessionId) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", loginTime=" + loginTime +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
