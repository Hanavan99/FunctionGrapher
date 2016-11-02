package com.functiongrapher.service;

public interface IPropertyService {

	public void setXMin(double value);
	
	public void setXMax(double value);
	
	public void setYMin(double value);
	
	public void setYMax(double value);
	
	public void setGridX(double value);
	
	public void setGridY(double value);
	
	public void setCameraPitch(double value);
	
	public void setCameraYaw(double value);
	
	public void setCameraRoll(double value);
	
	public void setCameraZoom(double value);
	
	public double getXMin();
	
	public double getXMax();
	
	public double getYMin();
	
	public double getYMax();
	
	public double getGridX();
	
	public double getGridY();
	
	public double getCameraPitch();
	
	public double getCameraYaw();
	
	public double getCameraRoll();
	
	public double getCameraZoom();
	
	public boolean is3D();
	
	public void setIs3D(boolean value);
	
}
