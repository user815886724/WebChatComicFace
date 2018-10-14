package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "face_data")
public class FaceData {

    public FaceData() {
    }

    public FaceData(String id, Double eyeCheekbonesTRatio, Double cheekbonesChinTvRatio, Double cheekbonesJawboneVRatio, String faceShapeId, Date createTime, Date updateTime) {
        this.id = id;
        this.eyeCheekbonesTRatio = eyeCheekbonesTRatio;
        this.cheekbonesChinTvRatio = cheekbonesChinTvRatio;
        this.cheekbonesJawboneVRatio = cheekbonesJawboneVRatio;
        this.faceShapeId = faceShapeId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Id
    private String id;

    @Column(name = "eye_cheekbones_t_ratio")
    private Double eyeCheekbonesTRatio;

    @Column(name = "cheekbones_chin_tv_ratio")
    private Double cheekbonesChinTvRatio;

    @Column(name = "cheekbones_jawbone_v_ratio")
    private Double cheekbonesJawboneVRatio;

    @Column(name = "faceShapeId")
    private String faceShapeId;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

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

    public String getFaceShapeId() {
        return faceShapeId;
    }

    public void setFaceShapeId(String faceShapeId) {
        this.faceShapeId = faceShapeId;
    }
}
