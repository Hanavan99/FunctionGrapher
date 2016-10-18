package com.functiongrapher.util;

public class ScreenResolution {

	private final int width, height;
	private final double aspect;
	private final String saspect;

	public ScreenResolution(int width, int height, String aspect) {
		this.width = width;
		this.height = height;
		this.aspect = ((double) width) / height;
		saspect = aspect;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getAspect() {
		return aspect;
	}

	public String getAspectAsString() {
		return saspect;
	}

	@Override
	public String toString() {
		return width + "x" + height + " (" + saspect + ")";
	}

}
