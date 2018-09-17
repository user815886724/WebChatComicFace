package com.face.math.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import com.face.math.model.Line;
import com.face.math.model.Point;
import sun.misc.BASE64Encoder;

public class ImageUtil {
	
	private static int mark = 0x0000ff;
	
	//图片转Base64
	public static String getBase64Str(String imgPath) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgPath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}catch (Exception e) {
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
	//圆圈标记
	public static Set<Point> getMarkCricle(Point circleCenter, int r) {
		Set<Point> points = new HashSet<>();  
		for(int i = 0;i < 360; i++) {	
			int x = (int) Math.round((circleCenter.getX() + r * Math.sin(Math.PI * (i - 90) / 180)));
			int y = (int) Math.round((circleCenter.getY() + r * Math.cos(Math.PI * (i - 90) / 180)));
			points.add(new Point(x , y));
		}
		return points;
	}
	//两点之间的连线方程
	public static Line getLine(Point start, Point end) {
		Line line = new Line();
		double k = (double)(end.getY() - start.getY()) / (end.getX() - start.getX());
		int d =  start.getY() - (int)Math.round((start.getX() * k));
		line.setD(d);
		line.setK(k);
		return line;
	}
	//方程中所有的点
	public static List<Point> getLineAllPoint(Line line,Point start,Point end){
		List<Point> points = new ArrayList<Point>();
		double x = (start.getX() < end.getX() ? start.getX() : end.getX());
		while( x < (start.getX() > end.getX() ? start.getX() : end.getX())) {
			int y = (int) Math.round((line.getK() * x + line.getD()));
			Point point = new Point((int) x, y);
			points.add(point);
			x = (double) (x + 0.01); 
		}
		return points;
	}
	//直线标记
	public static List<Line> drawAndReturnLine(BufferedImage imageBuffered, List<Point> points,String outUrl) throws Exception{
		List<Line> lines = new ArrayList<Line>();
		for(int i = 0; i < points.size(); i++) {
			List<Point> linePoints = new ArrayList<>();
			Line line = new Line();
			if(i < points.size() - 1) {
				line = ImageUtil.getLine(points.get(i), points.get(i + 1));
				linePoints= ImageUtil.getLineAllPoint(line, points.get(i), points.get(i + 1));
				
			}else {
				line = ImageUtil.getLine(points.get(0), points.get(i));
				linePoints= ImageUtil.getLineAllPoint(line, points.get(0), points.get(i));
			}
			for(Point point : linePoints) {
				imageBuffered.setRGB(point.getX(),point.getY(),mark);
			}
			lines.add(line);
		}
		ImageIO.write(imageBuffered, "jpg", new File(outUrl));
		return lines;
	}
	//求多个数值的比例
	public static List<Integer> getReduction(double[] values){
		List<Integer> result = doubleChangeInt(values);
		int min = getMinValue(result);
		boolean simplest = false;
		outer:
		for(int i = min;i > 1; i--) {
			if(canDivisibleByMin(result,i)) {
				for(int j = 0; j < result.size(); j++) {
					result.set(j, result.get(j) / i) ;
					if(result.get(j) == 1) {
						simplest = true;
					}
				}
				i = min = getMinValue(result);
				if(simplest) {
					break outer;
				}
			}
		}
		return result;
	}
	//将double值四舍五入转为int
	public static List<Integer> doubleChangeInt(double[] values){
		List<Integer> list = new ArrayList<>();
		for(double value : values) {
			int result = (int) Math.round(value);
			list.add(result);
		}
		return list;
	}
	//计算最小值
	public static int getMinValue(List<Integer> values) {
		int min = values.get(0);
		for(Integer value : values) {
			if(value < min) {
				min = value;
			}
		}
		return min;
	}
	//计算是否能被整除
	public static boolean canDivisible(int Divisor,int beDivisor) {
		if(Divisor > 0 && beDivisor > 0 && Divisor % beDivisor == 0) {
			return true;
		}
		return false;
	}
	//计算所有的数值能否被数整除
	public static boolean canDivisibleByMin(List<Integer> values,int min) {
		for(Integer value : values) {
			if(!canDivisible(value, min)) {
				return false;
			}
		}
		return true;
	}
}
