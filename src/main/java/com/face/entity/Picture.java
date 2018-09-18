package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "picture")
public class Picture {

    public Picture() {
    }

    public Picture(String id, String userId, String picPath, String resultPath, String absolutePath, String fileName, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.picPath = picPath;
        this.resultPath = resultPath;
        this.absolutePath = absolutePath;
        this.fileName = fileName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Id
    private String id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "picPath")
    private String picPath;

    @Column(name = "resultPath")
    private String resultPath;

    @Column(name = "absolutePath")
    private String absolutePath;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
