package com.functiongrapher.main;

import org.lwjgl.opengl.GL11;

import com.functiongrapher.function.Function;
import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.ui.GraphWindow;
import com.functiongrapher.ui.VarsWindow;
import com.functiongrapher.ui.WindowManager;

public class Main {

	public static void main(String[] args) {
		
		WindowManager.showSplashScreen(3000);
		WindowManager.addWindow(new VarsWindow());
		
		FunctionManager.addFunction(new Function() {
			@Override
			public double evalPoint(double x, double y) {
				return Math.sin(x + y);
			}
			@Override
			public int getDrawMode() {
				return GL11.GL_LINES;
			}
			@Override
			public byte[] getGraphColor() {
				return new byte[] { 0b0, 0b0, 0b1111111 };
			}
		});

		FunctionManager.addFunction(new Function() {
			@Override
			public double evalPoint(double x, double y) {
				return Math.pow(x, 2);
			}
			@Override
			public int getDrawMode() {
				return GL11.GL_LINES;
			}
			@Override
			public byte[] getGraphColor() {
				return new byte[] { 0b1111111, 0b0, 0b0 };
			}
		});

		Thread graphwindowthread = new Thread(() -> GraphWindow.init());
		graphwindowthread.start();

	}

}
