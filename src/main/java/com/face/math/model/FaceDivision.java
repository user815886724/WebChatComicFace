package com.face.math.model;

import com.face.math.utils.ImageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceDivision {
	
	public static String TransverseRatio = "TRANSVERSE_RATIO";
	
	public static String TransversePortraitRatio = "TRANSVERSE_PORTAIN_RATIO";
	
	public static String TransversePortraitRatioMin = "TRANSVERSE_PORTAIN_RATIO_MIN";

	/*
	 * 根据传入的值对应返回切割比例
	 * 6为脸的轮廓的中心点（即下巴尖点）
	 * 由划分的点可知3-9为颧骨
	 * 0-12为眼骨附近宽度
	 * 
	 */
	public static List<Integer> getFaceProportion(List<AccuratePoint> points) {
		double line1 = Pythagorean(points.get(0).getX(), points.get(0).getY(), points.get(12).getX(), points.get(12).getY());
		double line2 = Pythagorean(points.get(1).getX(), points.get(1).getY(), points.get(11).getX(), points.get(11).getY());
		double line3 = Pythagorean(points.get(2).getX(), points.get(2).getY(), points.get(10).getX(), points.get(10).getY());
		double line4 = Pythagorean(points.get(3).getX(), points.get(3).getY(), points.get(9).getX(), points.get(9).getY());
		double line5 = Pythagorean(points.get(4).getX(), points.get(4).getY(), points.get(8).getX(), points.get(8).getY());
		double line6 = Pythagorean(points.get(5).getX(), points.get(5).getY(), points.get(7).getX(), points.get(7).getY());
		double[] tmp = { line1, line2, line3, line4, line5, line6};
		List<Integer> result = ImageUtil.getReduction(tmp);
		return result;
	}
	
	public static Map<String , Ratio> getRatio(List<AccuratePoint> points){
		Map<String , Ratio> ratioMap = new HashMap<>();
		double line1 = Pythagorean(points.get(0).getX(), points.get(0).getY(), points.get(12).getX(), points.get(12).getY());
		double line2 = Pythagorean(points.get(3).getX(), points.get(3).getY(), points.get(9).getX(), points.get(9).getY());
		double lineY = Math.abs((points.get(3).getY() + points.get(9).getY()) / 2 - points.get(6).getY());
		double lineYmin = Math.abs((points.get(5).getY() + points.get(7).getY()) / 2 - points.get(6).getY());
		
		//眼角和颧骨的横比
		double[] tmp1 = {line1,line2};
		List<Integer> result1 = ImageUtil.getReduction(tmp1);
		Ratio ratio1 = new Ratio();
		ratio1.setFristNum(result1.get(0));
		ratio1.setSecondNum(result1.get(1));
		ratioMap.put(TransverseRatio, ratio1);
		
		//颧骨到下巴底部的横纵比
		double[] tmp2 = {line2,lineY};
		List<Integer> result2 = ImageUtil.getReduction(tmp2);
		Ratio ratio2 = new Ratio();
		ratio2.setFristNum(result2.get(0));
		ratio2.setSecondNum(result2.get(1));
		ratioMap.put(TransversePortraitRatio, ratio2);
		
		
		//颧骨到下巴底部与下颚骨到下巴底部的纵比
		double[] tmp3 = {lineY,lineYmin};
		List<Integer> result3 = ImageUtil.getReduction(tmp3);
		Ratio ratio3 = new Ratio();
		ratio3.setFristNum(result3.get(0));
		ratio3.setSecondNum(result3.get(1));
		ratioMap.put(TransversePortraitRatioMin, ratio3);
		return ratioMap;
	}
	
	private static double Pythagorean(double x1,double y1,double x2,double y2) {
		double result = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return result;
	}
	
}
