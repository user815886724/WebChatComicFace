package com.face.math.utils;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class FittingMathUtil {
	
	public double[] crateFuction(int degree,WeightedObservedPoints points) throws Exception{
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
		double[] result = fitter.fit(points.toList());
		return result;
	}
}
