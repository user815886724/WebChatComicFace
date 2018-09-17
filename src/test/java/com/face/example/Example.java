package com.face.example;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.face.math.model.*;
import com.face.math.utils.BaiduFaceApiUtil;
import com.face.math.utils.FittingMathUtil;
import com.face.math.utils.ImageUtil;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class Example {
	
	private final static  int size = 900;
	
	private final static String imageUrl = "C:/Users/PabloWayne/Desktop/test.jpg";
	
	private final static String outUrl = "C:/Users/PabloWayne/Desktop/out.jpg";
	
	private JFrame frame;
	
	public static final String APP_ID = "11419428";
	
	public static final String API_KEY = "z2yLENMLZhb36DcILorb64gO";
	
	public static final String SECRET_KEY = "LTREsidBK1tyOvGA4Gf4dOPphhfDwmc2";
	
	public static void main(String[] args) throws Exception {
		
		
		AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
		
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(6000);
		//设置传入参数调用接口
		//询问接口参数情况
		HashMap<String, String> options = new HashMap<>();
		/*
		 * age年龄;beauty美丽程度;expression表情;faceshape脸型;gender性别;glasses眼镜;
		 * landmark：标记点landmark72：标记72个特征点;race人种;facetype物种;quality照片人脸质量
		 * 多个用逗号隔开
		 */
		options.put("face_field", "landmark,age");
		/*
		 * 最高可以检测的人数
		 */
		options.put("max_face_num", "2");
		/*
		 * 类型：LIVE生活照；IDCARD身份证芯片照；WATERMARK水印证件照；CERT证件照片
		 * 默认为LIVE
		 */
		options.put("face_type", "LIVE");

		JSONObject res = client.detect(ImageUtil.getBase64Str(imageUrl), "BASE64", options);
		
		System.out.println(res.toString(2));
		/*
		 * 左眼画线圈出
		 */
		getLeftEye(imageUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 左眼圆圈标记
		 */
		//getLeftEyeCricle(outUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 右眼直线圈出
		 */
		getRightEye(outUrl,BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 鼻子直线圈出
		 */
		getNose(outUrl,BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 嘴巴直线圈出
		 */
		getMouth(outUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		
		/*
		 * 脸型直线圈出
		 */
		getFace(outUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		
		/*
		 * 脸部轮廓的拟合函数
		 * 而后改用比例，比较精确，简单，容易比较
		 */
		//getFaceFunction(BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 脸部各个部位的比例
		 */
		//getFaceProportion(BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 脸部轮廓的比例
		 */
		getFaceRatio(BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 左眼眉毛直线圈出
		 */
		getLeftEyebrow(outUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		
		/*
		 * 右眼眉毛直线圈出
		 */
		getRightEyebrow(outUrl, BaiduFaceApiUtil.getLandmark72(res));
		
		
		
		/*
		 * 72个标记点
		 */
		createResultImage(outUrl,BaiduFaceApiUtil.getLandmark72(res));
		
		
		/*
		 * 窗口弹出
		 */
		new Example();
	}
	
	//设置Applet的大小
	public Example() {
		frame = new JFrame("图片测试");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭方式
		frame.setSize(2 * size + 30, size);// 设置窗体大小
		int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setLocation((screen_width - frame.getWidth()) / 2,
				(screen_height - frame.getHeight()) / 2);
		frame.setLayout(new BorderLayout());
		addExampleImage();
		addResultImage();
		frame.setVisible(true);
	}
	
	//Applet中加入样本图片
	public void addExampleImage() {
		ImageIcon icon = new ImageIcon(imageUrl);
		icon.setImage(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		JLabel lb = new JLabel(icon);
		frame.add(lb,"West");
	}
	//处理结果图
	public void addResultImage() {
		ImageIcon icon = new ImageIcon(outUrl);
		icon.setImage(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		JLabel lb = new JLabel(icon);
		frame.add(lb,"East");
	}
	
	
	private static void createResultImage(String path,JSONArray arrays) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path)); 
		for(int i = 0;i < arrays.length(); i++) {
			JSONObject location = (JSONObject) arrays.get(i);
			int x =  location.getInt("x");
			int y = location.getInt("y");
			Set<Point> lists = ImageUtil.getMarkCricle(new Point(x, y), 5);
			int mark = 0x0000ff;
			for(Point point : lists) {
				imageBuffered.setRGB(point.getX(),point.getY(),mark);
			}
			
		}
		/*int mark = 0x0000ff;
		for(Point point : BaiduFaceApiUtil.getLeftEye(arrays)) {
			Set<Point> lists = Util.getMarkCricle(point, 5);
			for(Point item : lists) {
				imageBuffered.setRGB(item.getX(),item.getY(),mark);
			}
		}*/
		ImageIO.write(imageBuffered, "jpg", new File(outUrl));
	}
	
	private static void getLeftEyeCricle(String path,JSONArray arrays) throws Exception {
		BufferedImage imageBuffered = ImageIO.read(new File(path)); 
		int mark = 0x0000ff;
		for(Point point : BaiduFaceApiUtil.getLeftEye(arrays)) {
			Set<Point> lists = ImageUtil.getMarkCricle(point, 5);
			for(Point item : lists) {
				imageBuffered.setRGB(item.getX(),item.getY(),mark);
			}
		}
		ImageIO.write(imageBuffered, "jpg", new File(outUrl));
	}
	
	
	//左眼直线圈出
	private static void getLeftEye(String path,JSONArray landmarkEye) throws Exception {
		BufferedImage imageBuffered = ImageIO.read(new File(path)); 
		List<Point> points = BaiduFaceApiUtil.getLeftEye(landmarkEye);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	
	//右眼直线圈出
	private static void getRightEye(String path,JSONArray landmarkEye) throws Exception {
		BufferedImage imageBuffered = ImageIO.read(new File(path)); 
		List<Point> points = BaiduFaceApiUtil.getRightEye(landmarkEye);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	
	//鼻子直线圈出
	private static void getNose(String path,JSONArray landmarkNose) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path)); 
		List<Point> points = BaiduFaceApiUtil.getNose(landmarkNose);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	
	//嘴巴直线圈出
	private static void getMouth(String path,JSONArray landmarkMouth) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path));
		List<Point> points = BaiduFaceApiUtil.getMouth(landmarkMouth);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	
	//脸型直线圈出
	private static void getFace(String path,JSONArray landmarkFace) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path));
		List<Point> points = BaiduFaceApiUtil.getFace(landmarkFace);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	//左眉毛直线圈出
	private static void getLeftEyebrow(String path,JSONArray landmarkEyebrow) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path));
		List<Point> points = BaiduFaceApiUtil.getLeftEyebrow(landmarkEyebrow);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	//右眉毛直线圈出
	private static void getRightEyebrow(String path,JSONArray landmarkEyebrow) throws Exception{
		BufferedImage imageBuffered = ImageIO.read(new File(path));
		List<Point> points = BaiduFaceApiUtil.getRightEyebrow(landmarkEyebrow);
		List<Line> lines = ImageUtil.drawAndReturnLine(imageBuffered, points, outUrl);
	}
	//得到脸轮廓的拟合函数
	private static void getFaceFunction(JSONArray landmarkFace) throws Exception{
		List<AccuratePoint> points = BaiduFaceApiUtil.getAccurateFace(landmarkFace);
		WeightedObservedPoints weightedObservedPoints = new WeightedObservedPoints();
		for(AccuratePoint point : points) {
			weightedObservedPoints.add(point.getX(), point.getY());
		}
		FittingMathUtil mathUtil = new FittingMathUtil();
		double[] result = mathUtil.crateFuction(3, weightedObservedPoints);
		for(double a : result) {
			System.out.println(a);
		}
	}
	//得到脸轮廓的6个纵向比例
	private static void getFaceProportion(JSONArray landmarkFace) throws Exception{
		List<AccuratePoint> points = BaiduFaceApiUtil.getAccurateFace(landmarkFace);
		List<Integer> result = FaceDivision.getFaceProportion(points);
		for(Integer a : result) {
			System.out.print(a + " ");
		}
	}
	//得到脸轮廓的3个横纵比例
	private static void getFaceRatio(JSONArray landmarkFace)throws Exception{
		List<AccuratePoint> points = BaiduFaceApiUtil.getAccurateFace(landmarkFace);
		Map<String, Ratio> maps = FaceDivision.getRatio(points);
		System.out.println(maps.get(FaceDivision.TransverseRatio).toString());
		System.out.println(maps.get(FaceDivision.TransversePortraitRatio).toString());
		System.out.println(maps.get(FaceDivision.TransversePortraitRatioMin).toString());
		System.out.print((double)Math.round((double)maps.get(FaceDivision.TransverseRatio).getFristNum() / maps.get(FaceDivision.TransverseRatio).getSecondNum() * 100) / 100+",");
		System.out.print((double)Math.round((double)maps.get(FaceDivision.TransversePortraitRatio).getFristNum() / maps.get(FaceDivision.TransversePortraitRatio).getSecondNum() * 100) / 100+",");
		System.out.print((double)Math.round((double)maps.get(FaceDivision.TransversePortraitRatioMin).getFristNum() / maps.get(FaceDivision.TransversePortraitRatioMin).getSecondNum() * 100) / 100);
	}
}
