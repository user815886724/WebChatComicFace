package com.face.service;

import com.face.entity.FaceShape;

import java.util.List;

public interface FaceShapeService {

    String saveFaceShape(String shapeName);

    List<FaceShape> getFaceShapeList();

    String getFaceShapeName(String shapeId);
}
