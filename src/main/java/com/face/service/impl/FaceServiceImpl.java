package com.face.service.impl;

import com.baidu.aip.face.AipFace;
import com.face.commom.PropertiesUtil;
import com.face.dao.FaceDao;
import com.face.dao.FaceDataDao;
import com.face.dao.FaceShapeDao;
import com.face.dao.PictureDao;
import com.face.entity.Face;
import com.face.entity.FaceData;
import com.face.entity.FaceShape;
import com.face.entity.Picture;
import com.face.math.model.AccuratePoint;
import com.face.math.model.FaceDivision;
import com.face.math.model.Ratio;
import com.face.math.utils.BaiduFaceApiUtil;
import com.face.math.utils.ImageUtil;
import com.face.service.FaceService;
import com.face.utils.BaiduFaceClient;
import com.face.utils.DecimalCalculate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class FaceServiceImpl implements FaceService{

    /**
     * 百度人脸识别的服务器配置
     */
    private static String APP_ID = "Face_APP_ID";

    private static String API_KEY = "Face_API_KEY";

    private static String SECRET_KEY = "Face_SECRET_KEY";


    /**
     * 识别结果返回的参数名
     */
    private static String RESULT_URL = "resultUrl";

    private static String RESULT = "result";


    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private FaceDao faceDao;

    @Autowired
    private FaceDataDao faceDataDao;

    @Autowired
    private FaceShapeDao faceShapeDao;



    @Override
    public void addData(String pictureId, String shapeId) throws Exception {
        Picture picture = pictureDao.getOne(pictureId);
        String path = picture.getAbsolutePath();
        String userId = picture.getUserId();

        //调用API识别图片
        JSONObject res = BaiduFaceClient.execute(path);

        //添加训练集用，不加入Face

        //获得比例
        Map<String,Double> ratioMap = getFaceRatio(BaiduFaceApiUtil.getLandmark72(res));
        Double eyeCheekbonesTRatio = ratioMap.get("eyeCheekbonesTRatio");
        Double cheekbonesChinTvRatio = ratioMap.get("cheekbonesChinTvRatio");
        Double cheekbonesJawboneVRatio = ratioMap.get("cheekbonesJawboneVRatio");


        //加入到FaceData表中
        FaceData faceData = new FaceData();
        faceData.setId(UUID.randomUUID().toString());
        faceData.setEyeCheekbonesTRatio(eyeCheekbonesTRatio);
        faceData.setCheekbonesChinTvRatio(cheekbonesChinTvRatio);
        faceData.setCheekbonesJawboneVRatio(cheekbonesJawboneVRatio);
        faceData.setFaceShapeId(shapeId);
        faceData.setCreateTime(new Date());
        faceData.setUpdateTime(new Date());
        faceDataDao.save(faceData);


    }

    /**
     *
     * @param pictureId
     * @return 返回识别结果和转化的图片地址
     * @throws Exception
     */
    @Override
    public Map recognize(String pictureId) throws Exception {
        //查询图片路径
        Picture picture = pictureDao.getOne(pictureId);
        String path = picture.getAbsolutePath();
        String userId = picture.getUserId();

        //调用API识别图片
        JSONObject res = BaiduFaceClient.execute(path);

        //先加入Face数据
        Face face = new Face();
        String faceId = UUID.randomUUID().toString();
        face.setId(UUID.randomUUID().toString());
        face.setPictureId(pictureId);
        face.setUserId(userId);
        face.setRecognizeResult(res.toString(2));
        faceDao.save(face);

        //获得比例
        Map<String,Double> ratioMap = getFaceRatio(BaiduFaceApiUtil.getLandmark72(res));
        Double eyeCheekbonesTRatio = ratioMap.get("eyeCheekbonesTRatio");
        Double cheekbonesChinTvRatio = ratioMap.get("cheekbonesChinTvRatio");
        Double cheekbonesJawboneVRatio = ratioMap.get("cheekbonesJawboneVRatio");


        //拿出训练集
        List<FaceData> faceDataList = faceDataDao.findAll();
        ArrayList<ArrayList<String>> datas = new ArrayList<>();
        for(FaceData faceData : faceDataList){
            ArrayList<String> data = new ArrayList<>();
            data.add(String.valueOf(faceData.getEyeCheekbonesTRatio()));
            data.add(String.valueOf(faceData.getCheekbonesChinTvRatio()));
            data.add(String.valueOf(faceData.getCheekbonesJawboneVRatio()));
            FaceShape faceShape = faceShapeDao.getOne(faceData.getFaceShapeId());
            data.add(faceShape.getShapeName());
            datas.add(data);
        }

        //TODO 分析数据得出结果

        //处理加入FaceData数据
        FaceData faceData = new FaceData();
        faceData.setEyeCheekbonesTRatio(eyeCheekbonesTRatio);
        faceData.setCheekbonesChinTvRatio(cheekbonesChinTvRatio);
        faceData.setCheekbonesJawboneVRatio(cheekbonesJawboneVRatio);

        return null;
    }


    //取得三个比例值
    private static Map<String,Double> getFaceRatio(JSONArray landmarkFace)throws Exception{
        Map<String,Double> result = new HashMap<>();
        List<AccuratePoint> points = BaiduFaceApiUtil.getAccurateFace(landmarkFace);
        Map<String, Ratio> maps = FaceDivision.getRatio(points);
        Double eyeCheekbonesTRatio = DecimalCalculate.div(maps.get(FaceDivision.TransverseRatio).getFristNum(),maps.get(FaceDivision.TransverseRatio).getSecondNum(),3);
        Double cheekbonesChinTvRatio = DecimalCalculate.div(maps.get(FaceDivision.TransversePortraitRatio).getFristNum(),maps.get(FaceDivision.TransversePortraitRatio).getSecondNum(),3);
        Double cheekbonesJawboneVRatio = DecimalCalculate.div(maps.get(FaceDivision.TransversePortraitRatioMin).getFristNum(),maps.get(FaceDivision.TransversePortraitRatioMin).getSecondNum(),3);
        result.put("eyeCheekbonesTRatio",eyeCheekbonesTRatio);
        result.put("cheekbonesChinTvRatio",cheekbonesChinTvRatio);
        result.put("cheekbonesJawboneVRatio",cheekbonesJawboneVRatio);
        return result;
    }
}
