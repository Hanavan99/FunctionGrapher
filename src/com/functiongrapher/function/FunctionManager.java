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

	public static void drawFunctions(boolean is3D, double t) {

		if (is3D) {
			// Draw 3D axes
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
		} else {
			// Draw 2D gridlines
			GL11.glLineWidth(1f);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3d(0.5d, 0.5d, 0.5d);
			for (double x = xmin; x < xmax; x += 1) {
				GL11.glVertex2d(x, ymin);
				GL11.glVertex2d(x, ymax);
			}
			for (double y = ymin; y < ymax; y += 1) {
				GL11.glVertex2d(xmin, y);
				GL11.glVertex2d(xmax, y);
			}
			GL11.glEnd();
			
			// Draw 2D axes
			GL11.glLineWidth(3f);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3d(0, 0, 0);
			GL11.glVertex2d(xmin, 0);
			GL11.glVertex2d(xmax, 0);
			GL11.glVertex2d(0, ymin);
			GL11.glVertex2d(0, ymax);
			GL11.glEnd();

			
		}
		
		GL11.glLineWidth(3f);
		if (functions.size() > 0) {
			for (double x = xmin; x < xmax; x += delta) {
				for (double y = ymin; y < ymax; y += delta) {
					for (Function f : functions) {
						if (is3D) {
							double basepoint = f.evalPoint(x, y, t);
							double xoffset = f.evalPoint(x + delta, y, t);
							double yoffset = f.evalPoint(x, y + delta, t);
							GL11.glBegin(f.getDrawMode());
							GL11.glColor3b(f.getGraphColor()[0], (f.getGraphColor()[1]), f.getGraphColor()[2]);
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d(x, (y + delta), yoffset);
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d((x + delta), y, xoffset);
							GL11.glEnd();
						} else {
							double basepoint = f.evalPoint(x, 0, t);
							double xoffset = f.evalPoint(x + delta, 0, t);
							GL11.glBegin(f.getDrawMode());
							GL11.glColor3b(f.getGraphColor()[0], (f.getGraphColor()[1]), f.getGraphColor()[2]);
							GL11.glVertex2d(x, basepoint);
							GL11.glVertex2d(x + delta, xoffset);
							GL11.glEnd();
						}
					}
				}
			}
		}
	}

}
