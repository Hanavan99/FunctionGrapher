package com.functiongrapher.service;

import com.functiongrapher.ui.gfx.DrawMode;

public class PropertyServiceImpl implements IPropertyService {

	private double xmin = -10, xmax = 10;
	private double ymin = -10, ymax = 10;
	private double gridx = 1, gridy = 1;
	private double camerapitch = -50, camerayaw = 0, cameraroll = 0, camerazoom = 30;
	private boolean is3D = false;
	private boolean drawTiles = true;
	private boolean drawLines = true;
	private DrawMode drawMode = DrawMode.LINES_WITH_FILL;

	@Override
	public void setXMin(double value) {
		xmin = value;
	}

	@Override
	public void setXMax(double value) {
		xmax = value;
	}

	@Override
	public void setYMin(double value) {
		ymin = value;
	}

	@Override
	public void setYMax(double value) {
		ymax = value;
	}

	@Override
	public void setGridX(double value) {
		gridx = value;
	}

	@Override
	public void setGridY(double value) {
		gridy = value;
	}

	@Override
	public void setCameraPitch(double value) {
		camerapitch = value;
	}

	@Override
	public void setCameraYaw(double value) {
		camerayaw = value;
	}

	@Override
	public void setCameraRoll(double value) {
		cameraroll = value;
	}

	@Override
	public void setCameraZoom(double value) {
		camerazoom = value;
	}

	@Override
	public double getXMin() {
		return xmin;
	}

	@Override
	public double getXMax() {
		return xmax;
	}

	@Override
	public double getYMin() {
		return ymin;
	}

	@Override
	public double getYMax() {
		return ymax;
	}

	@Override
	public double getGridX() {
		return gridx;
	}

	@Override
	public double getGridY() {
		return gridy;
	}

	@Override
	public double getCameraPitch() {
		return camerapitch;
	}

	@Override
	public double getCameraYaw() {
		return camerayaw;
	}

	@Override
	public double getCameraRoll() {
		return cameraroll;
	}

	@Override
	public double getCameraZoom() {
		return camerazoom;
	}

	@Override
	public boolean is3D() {
		return is3D;
	}

	@Override
	public void setIs3D(boolean value) {
		is3D = value;
	}

	@Override
	public boolean areTilesEnabled() {
		return drawTiles;
	}

	@Override
	public void setTilesEnabled(boolean value) {
		drawTiles = value;
	}

	@Override
	public boolean areLinesEnabled() {
		return drawLines;
	}

	@Override
	public DrawMode getDrawMode() {
		return drawMode;
	}

	@Override
	public void setLinesEnabled(boolean value) {
		drawLines = value;
	}

	@Override
	public void setDrawMode(DrawMode value) {
		drawMode = value;
		
	}

}
