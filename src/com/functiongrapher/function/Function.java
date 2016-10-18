package com.functiongrapher.function;

public interface Function {

	public double evalPoint(double x, double y, double t);
	
	public double[] getGraphColor();
	
	public boolean useMethodForColor();
	
	public double getEvaluatedHue(double x, double y, double t);
}
