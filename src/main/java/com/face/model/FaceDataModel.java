package com.face.model;

import java.util.Date;

public class FaceDataModel {
    public FaceDataModel() {
    }

    public FaceDataModel(String id, Double eyeCheekbonesTRatio, Double cheekbonesChinTvRatio, Double cheekbonesJawboneVRatio, String faceShape, Date createTime, Date updateTime) {
        this.id = id;
        this.eyeCheekbonesTRatio = eyeCheekbonesTRatio;
        this.cheekbonesChinTvRatio = cheekbonesChinTvRatio;
        this.cheekbonesJawboneVRatio = cheekbonesJawboneVRatio;
        this.faceShape = faceShape;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    private String id;
    private Double eyeCheekbonesTRatio;
    private Double cheekbonesChinTvRatio;
    private Double cheekbonesJawboneVRatio;
    private String faceShape;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getEyeCheekbonesTRatio() {
        return eyeCheekbonesTRatio;
    }

    public void setEyeCheekbonesTRatio(Double eyeCheekbonesTRatio) {
        this.eyeCheekbonesTRatio = eyeCheekbonesTRatio;
    }

    public Double getCheekbonesChinTvRatio() {
        return cheekbonesChinTvRatio;
    }

    public void setCheekbonesChinTvRatio(Double cheekbonesChinTvRatio) {
        this.cheekbonesChinTvRatio = cheekbonesChinTvRatio;
    }

    public Double getCheekbonesJawboneVRatio() {
        return cheekbonesJawboneVRatio;
    }

    public void setCheekbonesJawboneVRatio(Double cheekbonesJawboneVRatio) {
        this.cheekbonesJawboneVRatio = cheekbonesJawboneVRatio;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
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
}
