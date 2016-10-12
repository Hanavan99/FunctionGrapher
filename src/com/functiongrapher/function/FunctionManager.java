package com.functiongrapher.function;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class FunctionManager {

	private static ArrayList<Function> functions = new ArrayList<Function>();

	private static double xmin = -10;
	private static double xmax = 10;
	private static double ymin = -10;
	private static double ymax = 10;
	private static double delta2D = 0.0625d;
	private static double delta3D = 0.5d;

	public static void addFunction(Function f) {
		functions.add(f);
	}
	
	public static void removeFunction(Function f) {
		functions.remove(f);
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
			GL11.glColor3d(0.7d, 0.7d, 0.7d);
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

		if (functions.size() == 0) {
			return;
		}

		GL11.glLineWidth(3f);
		if (is3D) {
			for (Function f : functions) {
				GL11.glBegin(f.getDrawMode());
				GL11.glColor3dv(f.getGraphColor());
				for (double x = xmin; x < xmax; x += delta3D) {
					for (double y = ymin; y < ymax; y += delta3D) {
						double basepoint = f.evalPoint(x, y, t);
						double xoffset = f.evalPoint(x + delta3D, y, t);
						double yoffset = f.evalPoint(x, y + delta3D, t);
						double bothoffset = f.evalPoint(x + delta3D, y + delta3D, t);
						GL11.glVertex3d(x, y, basepoint);
						GL11.glVertex3d(x, y + delta3D, yoffset);
						GL11.glVertex3d(x, y, basepoint);
						GL11.glVertex3d(x + delta3D, y, xoffset);
						GL11.glVertex3d(x + delta3D, y + delta3D, bothoffset);
						GL11.glVertex3d(x, y + delta3D, yoffset);
						GL11.glVertex3d(x + delta3D, y + delta3D, bothoffset);
						GL11.glVertex3d(x + delta3D, y, xoffset);
					}
				}
				GL11.glEnd();
			}
		} else {
			for (Function f : functions) {
				GL11.glBegin(f.getDrawMode());
				GL11.glColor3dv(f.getGraphColor());
				for (double x = xmin; x < xmax; x += delta2D) {
					double basepoint = f.evalPoint(x, 0, t);
					double xoffset = f.evalPoint(x + delta2D, 0, t);
					GL11.glVertex2d(x, basepoint);
					GL11.glVertex2d(x + delta2D, xoffset);
				}
				GL11.glEnd();
			}
		}
	}

}
