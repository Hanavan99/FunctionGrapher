package com.functiongrapher.main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.functiongrapher.util.ScreenResolution;

public class ProgramInfo {

	public static final String PROGRAM_NAME = "FunctionGrapher";
	public static final String PROGRAM_VERSION = "2.0.0 Beta v5";
	public static final String COPYRIGHT_INFO = "Copyright ©2016. All rights reserved.";

	public static final String ICON32_PATH = "/assets/images/fgicon32.png";
	public static final String ICON64_PATH = "/assets/images/fgicon64.png";

	public static final int DEFAULTCOMPONENTHEIGHT = 24;

	public static final String WINDOW_VARS_NAME = "Vars";
	public static final String WINDOW_VARS_NAME_INTERNAL = "vars";
	public static final String WINDOW_TABLE_NAME = "Table";
	public static final String WINDOW_TABLE_NAME_INTERNAL = "table";
	
	public static final String QUIT_MESSAGE = "Are you sure you want to quit?";

	public static final int GRAPHWINDOW_WIDTH = 500;
	public static final int GRAPHWINDOW_HEIGHT = 500;

	public static final int VARSWINDOW_WIDTH = 500;
	public static final int VARSWINDOW_HEIGHT = 500;

	public static final FileFilter[] PRESETFILE_FILTERS = new FileFilter[] { new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.getName().contains(".fgp"))
				return true;
			return false;
		}

		@Override
		public String getDescription() {
			return PROGRAM_NAME + " Preset File (*.fgp)";
		}
	}, new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.getName().contains(".txt"))
				return true;
			return false;
		}

		@Override
		public String getDescription() {
			return "Text Files (*.txt)";
		}
	} };
	public static final FileFilter[] SCREENSHOT_FILTERS = new FileFilter[] { new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.getName().contains(".png"))
				return true;
			return false;
		}

		@Override
		public String getDescription() {
			return "PNG Image (*.png)";
		}
	}/*, new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.getName().contains(".gif"))
				return true;
			return false;
		}

		@Override
		public String getDescription() {
			return "GIF Image (*.gif)";
		}
	}, new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.getName().contains(".jpg") || f.getName().contains(".jpeg"))
				return true;
			return false;
		}

		@Override
		public String getDescription() {
			return "JPEG Image (*.jpg, *.jpeg)";
		}
	}*/ };

	public static final ScreenResolution[] SUPPORTED_RESOLUTIONS = new ScreenResolution[] { new ScreenResolution(640, 480, "4:3"), new ScreenResolution(1024, 768, "4:3"), new ScreenResolution(1280, 1024, "5:4"),
			new ScreenResolution(1440, 900, "16:10"), new ScreenResolution(1920, 1080, "16:9"), new ScreenResolution(1920, 1200, "16:10") };

}
