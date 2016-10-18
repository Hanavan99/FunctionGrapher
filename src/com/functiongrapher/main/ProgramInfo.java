package com.functiongrapher.main;

import com.functiongrapher.util.ScreenResolution;

public class ProgramInfo {

	public static final String PROGRAM_NAME = "FunctionGrapher";
	public static final String PROGRAM_VERSION = "2.0.0 Beta v4";
	public static final String COPYRIGHT_INFO = "Copyright ©2016. All rights reserved.";
	
	public static final String ICON32_PATH = "/assets/images/fgicon32.png";
	public static final String ICON64_PATH = "/assets/images/fgicon64.png";
	
	public static final int GRAPHWINDOW_WIDTH = 500;
	public static final int GRAPHWINDOW_HEIGHT = 500;
	
	public static final int VARSWINDOW_WIDTH = 500;
	public static final int VARSWINDOW_HEIGHT = 500;
	
	public static final ScreenResolution[] SUPPORTED_RESOLUTIONS = new ScreenResolution[] {
		new ScreenResolution(640, 480, "4:3"),
		new ScreenResolution(1024, 768, "4:3"),
		new ScreenResolution(1280, 1024, "5:4"),
		new ScreenResolution(1440, 900, "16:10"),
		new ScreenResolution(1920, 1080, "16:9"),
		new ScreenResolution(1920, 1200, "16:10")
	};
	
}
