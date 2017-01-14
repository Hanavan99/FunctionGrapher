package com.functiongrapher.service;

import com.functiongrapher.ui.gfx.DrawMode;

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
	
	public boolean areTilesEnabled();
	
	public boolean areLinesEnabled();
	
	public DrawMode getDrawMode();
	
	public void setIs3D(boolean value);
	
	public void setTilesEnabled(boolean value);
	
	public void setLinesEnabled(boolean value);
	
	public void setDrawMode(DrawMode value);
	
}
