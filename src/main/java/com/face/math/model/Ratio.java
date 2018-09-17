package com.face.math.model;

public class Ratio {
	private Integer fristNum;
	
	private Integer secondNum;

	public Integer getFristNum() {
		return fristNum;
	}

	public void setFristNum(Integer fristNum) {
		this.fristNum = fristNum;
	}

	public Integer getSecondNum() {
		return secondNum;
	}

	public void setSecondNum(Integer secondNum) {
		this.secondNum = secondNum;
	}

	@Override
	public String toString() {
		return fristNum + ":" + secondNum ;
	}
	
	
}
