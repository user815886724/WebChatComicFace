package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "face")
public class Face {
    public Face() {
    }

    public Face(String id, String userId, String pictureId, String recognizeResult) {
        this.id = id;
        this.userId = userId;
        this.pictureId = pictureId;
        this.recognizeResult = recognizeResult;
    }
    @Id
    private String id;
    @Column(name = "userId")
    private String userId;
    @Column(name = "pictureId")
    private String pictureId;
    @Column(name = "recognizeResult")
    private String recognizeResult;

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

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getRecognizeResult() {
        return recognizeResult;
    }

    public void setRecognizeResult(String recognizeResult) {
        this.recognizeResult = recognizeResult;
    }
}
