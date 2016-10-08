package com.functiongrapher.function;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class FunctionManager {

	private static ArrayList<Function> functions = new ArrayList<Function>();

	private static double xmin = -10;
	private static double xmax = 10;
	private static double ymin = -10;
	private static double ymax = 10;
	private static double delta = 0.25d;

	public static void addFunction(Function f) {
		functions.add(f);
	}

	public static void drawFunctions() {
		
		GL11.glLineWidth(3f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(0, 0, 0);
		GL11.glVertex3d(xmin, 0, 0);
		GL11.glVertex3d(xmax, 0, 0);
		GL11.glVertex3d(0, ymin, 0);
		GL11.glVertex3d(0, ymax, 0);
		GL11.glVertex3d(0, 0, -10);
		GL11.glVertex3d(0, 0, 10);
		GL11.glEnd();
		
		if (functions.size() > 0) {
			for (double x = xmin; x < xmax; x += delta) {
				for (double y = ymin; y < ymax; y += delta) {
					for (Function f : functions) {
						GL11.glBegin(f.getDrawMode());
						GL11.glColor3b(f.getGraphColor()[0], f.getGraphColor()[1], f.getGraphColor()[2]);
						GL11.glVertex3d(x, y, f.evalPoint(x, y));
						GL11.glVertex3d(x, (y + delta), f.evalPoint(x, y + delta));
						GL11.glVertex3d(x, y, f.evalPoint(x, y));
						GL11.glVertex3d((x + delta), y, f.evalPoint(x + delta, y));
						GL11.glEnd();
					}
				}
			}
		}
	}

}
