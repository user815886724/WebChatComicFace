package com.face.service.impl;

import com.face.dao.FaceShapeDao;
import com.face.entity.FaceShape;
import com.face.service.FaceShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FaceShapeServiceImpl implements FaceShapeService {

    @Autowired
    private FaceShapeDao faceShapeDao;

    @Override
    public String saveFaceShape(String shapeName) {
        String id = UUID.randomUUID().toString();
        faceShapeDao.save(new FaceShape(id,shapeName));
        return id;
    }

    @Override
    public List<FaceShape> getFaceShapeList() {
        return faceShapeDao.findAll();
    }

    @Override
    public String getFaceShapeName(String shapeId) {
        FaceShape faceShape = faceShapeDao.findById(shapeId).get();
        return faceShape.getShapeName();
    }
}
