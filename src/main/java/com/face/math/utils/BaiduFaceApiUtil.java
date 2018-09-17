package com.face.math.utils;

import java.util.ArrayList;
import java.util.List;

import com.face.math.model.AccuratePoint;
import com.face.math.model.Point;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaiduFaceApiUtil {
	public static JSONArray getLandmark72(JSONObject res) {
		JSONObject result = (JSONObject) res.get("result");
		
		JSONArray face_list = (JSONArray) result.get("face_list");
		
		JSONObject face_list_item = (JSONObject) face_list.get(0);
		
		JSONArray landmark72 = (JSONArray) face_list_item.get("landmark72");
		
		return landmark72;
	}
	
	public static JSONArray getLandmark(JSONObject res) {
		JSONObject result = (JSONObject) res.get("result");
		
		JSONArray face_list = (JSONArray) result.get("face_list");
		
		JSONObject face_list_item = (JSONObject) face_list.get(0);
		
		JSONArray landmark = (JSONArray) face_list_item.get("landmark");
		
		return landmark;
	}
	
	
	public static List<Point> getLeftEye(JSONArray landmark72) {
		/*
		 * 左眼是13-20
		 * 21是中心点
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 13; i < 21; i++) {
			JSONObject location = (JSONObject) landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	public static List<Point> getRightEye(JSONArray landmark72){
		/*
		 * 右眼是30-37
		 * 38是中心点
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 30; i < 38; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	public static List<Point> getNose(JSONArray landmark72){
		/*
		 * 鼻子是47 - 57
		 * 57为中心点
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 47; i < 57; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	public static List<Point> getMouth(JSONArray landmark72){
		/*
		 * 嘴巴是58 - 71
		 * 其中66 - 71是嘴巴开裂程度
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 58; i < 66; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	
	public static List<Point> getFace(JSONArray landmark72){
		/*
		 * 脸是0-12
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 0; i < 13; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	//需要精确计算的face
	public static List<AccuratePoint> getAccurateFace(JSONArray landmark72){
		/*
		 * 脸是0-12
		 */
		List<AccuratePoint> list = new ArrayList<>();
		for(int i = 0; i < 13; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			AccuratePoint point = new AccuratePoint(location.getDouble("x"),location.getDouble("y"));
			list.add(point);
		}
		return list;
	}
	
	public static List<Point> getLeftEyebrow(JSONArray landmark72){
		/*
		 * 左眉毛是22-29
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 22; i < 30; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	public static List<Point> getRightEyebrow(JSONArray landmark72){
		/*
		 * 右眉毛是39 - 46
		 */
		List<Point> list = new ArrayList<>();
		for(int i = 39; i < 47; i++) {
			JSONObject location = (JSONObject)landmark72.get(i);
			Point point = new Point(location.getInt("x"), location.getInt("y"));
			list.add(point);
		}
		return list;
	}
	
	
	
}
