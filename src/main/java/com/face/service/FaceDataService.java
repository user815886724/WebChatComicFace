package com.face.service;

import com.face.entity.FaceData;
import com.face.entity.PageInfo;
import com.face.model.FaceDataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaceDataService {

    PageInfo<FaceDataModel> getFaceDataModelList(Pageable pageable);

    long getFaceDataCount();
}
