package com.face.controller;

import com.face.entity.FaceData;
import com.face.entity.FaceShape;
import com.face.entity.PageInfo;
import com.face.model.FaceDataModel;
import com.face.response.CallbackResult;
import com.face.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/face/")
public class FaceController {

    @Autowired
    private FaceService faceService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private FaceShapeService faceShapeService;

    @Autowired
    private FaceDataService faceDataService;

    private static final String ADMIN_ID = "85f0750e-8879-45fa-b8ca-e7310ad9db96";

    @PostMapping("/recognize")
    @ResponseBody
    public CallbackResult recognize(String pictureId,String logId){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            //检查用户
            String userId = userService.checkUserIdByLogId(logId);
            //TODO
        }catch (Exception e){
            e.printStackTrace();
            callbackResult.setMessage(e.getMessage());
            return callbackResult;
        }
        return callbackResult;
    }


    @PostMapping("/recognizeAndUpload")
    @ResponseBody
    public CallbackResult recognizeAndUpload(@RequestParam("files")MultipartFile[] files, String logId){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            //检查用户
            String userId = userService.checkUserIdByLogId(logId);
            //上传图片
            String pictureId = pictureService.upload(userId,files);
            //TODO
        }catch (Exception e){
            e.printStackTrace();
            callbackResult.setMessage(e.getMessage());
            return callbackResult;
        }
        return callbackResult;
    }


    @PostMapping("/addFaceShape")
    @ResponseBody
    public CallbackResult addFaceShape(String shapeName){
        CallbackResult callbackResult = new CallbackResult(false);
        String id = faceShapeService.saveFaceShape(shapeName);
        callbackResult.setSuccess(true);
        callbackResult.setDetails(id);
        return callbackResult;
    }

    @PostMapping("/addData")
    @ResponseBody
    public CallbackResult addData(@RequestParam("files")MultipartFile[] files,@RequestParam("shapeId") String shapeId){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            String pictureId = pictureService.upload(ADMIN_ID,files);
            faceService.addData(pictureId,shapeId);
            callbackResult.setSuccess(true);
            callbackResult.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            callbackResult.setMessage(e.getMessage());
            return callbackResult;
        }
        return callbackResult;
    }

    @GetMapping("/getFaceShapeList")
    @ResponseBody
    public CallbackResult getFaceShapeList(){
        CallbackResult callbackResult = new CallbackResult(false);
        List<FaceShape> faceShapes = faceShapeService.getFaceShapeList();
        if(faceShapes.size() > 0){
            callbackResult.setSuccess(true);
            callbackResult.setDetails(faceShapes);
        }else{
            callbackResult.setMessage("没有数据");
        }
        return callbackResult;
    }


    @GetMapping("/getFaceDataList")
    @ResponseBody
    public PageInfo<FaceDataModel> getFaceDataList(@PageableDefault(value = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable){
        return faceDataService.getFaceDataModelList(pageable);
    }

    @GetMapping("/getFaceDataCount")
    @ResponseBody
    public long getFaceDataCount(){
        return faceDataService.getFaceDataCount();
    }

    @PostMapping("/deleteFaceData")
    @ResponseBody
    public CallbackResult deleteFaceData(String id){
        CallbackResult callbackResult = new CallbackResult(true);
        faceDataService.deleteFaceData(id);
        callbackResult.setDetails("删除成功！");
        return callbackResult;
    }
}
