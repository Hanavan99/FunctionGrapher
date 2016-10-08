package com.functiongrapher.function;

public interface Function {

	public double evalPoint(double x, double y);
	
	public int getDrawMode();
	
	public byte[] getGraphColor();
	
}
