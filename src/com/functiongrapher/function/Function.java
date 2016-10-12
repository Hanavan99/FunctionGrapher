package com.functiongrapher.function;

public interface Function {

	public double evalPoint(double x, double y, double t);
	
	public int getDrawMode();
	
	public double[] getGraphColor();
	
}
