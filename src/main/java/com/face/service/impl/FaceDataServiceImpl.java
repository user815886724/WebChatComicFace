package com.face.service.impl;

import com.face.dao.FaceDataDao;
import com.face.dao.FaceShapeDao;
import com.face.entity.FaceData;
import com.face.entity.FaceShape;
import com.face.entity.PageInfo;
import com.face.model.FaceDataModel;
import com.face.service.FaceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FaceDataServiceImpl implements FaceDataService{

    @Autowired
    private FaceDataDao faceDataDao;

    @Autowired
    private FaceShapeDao faceShapeDao;

    @Override
    public PageInfo<FaceDataModel> getFaceDataModelList(Pageable pageable) {
        Page<FaceData> page =  faceDataDao.findAll(pageable);
        PageInfo<FaceDataModel> pageInfo = new PageInfo<>(page);
        List<FaceDataModel> faceDataModels = new ArrayList<>();
        for(FaceData faceData :page){
            String shapeName = faceShapeDao.getOne(faceData.getFaceShapeId()).getShapeName();
            FaceDataModel faceDataModel = new FaceDataModel(faceData.getId(),faceData.getEyeCheekbonesTRatio(),faceData.getCheekbonesChinTvRatio(),
                    faceData.getCheekbonesJawboneVRatio(),shapeName,faceData.getCreateTime(),faceData.getUpdateTime());
            faceDataModels.add(faceDataModel);
        }
        pageInfo.setDatas(faceDataModels);
        return pageInfo;
    }

    @Override
    public long getFaceDataCount() {
        return faceDataDao.count();
    }

    @Override
    public void deleteFaceData(String id) {
        faceDataDao.deleteById(id);
    }
}
