package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "face_shape")
public class FaceShape {
    public FaceShape() {
    }

    public FaceShape(String id, String shapeName) {
        this.id = id;
        this.shapeName = shapeName;
    }

    @Id
    private String id;

    @Column(name = "shapeName")
    private String shapeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }
}
