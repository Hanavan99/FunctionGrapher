package com.functiongrapher.function;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.EquationEditor;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;

public class FunctionManager {

	private static ArrayList<EquationEditor> functions = new ArrayList<EquationEditor>();

	private static double xmin = -10.0d;
	private static double xmax = 10.0d;
	private static double ymin = -10.0d;
	private static double ymax = 10.0d;
	private static double delta2D = 1d / 32;
	private static double delta3D = 1d / 2;
	private static double gridStepX2D = 1.0d;
	private static double gridStepY2D = 1.0d;

	public static void addFunction(EquationEditor ee) {
		functions.add(ee);
		((VarsWindow) WindowManager.getWindow(ProgramInfo.WINDOW_VARS_NAME_INTERNAL)).getEquationPanel();
	}

	public static void removeFunction(EquationEditor ee) {
		functions.remove(ee);
	}
	
	public static ArrayList<EquationEditor> getFunctions() {
		return functions;
	}

	public static double getXmin() {
		return xmin;
	}

	public static void setXmin(double xmin) {
		FunctionManager.xmin = xmin;
	}

	public static double getXmax() {
		return xmax;
	}

	public static void setXmax(double xmax) {
		FunctionManager.xmax = xmax;
	}

	public static double getYmin() {
		return ymin;
	}

	public static void setYmin(double ymin) {
		FunctionManager.ymin = ymin;
	}

	public static double getYmax() {
		return ymax;
	}

	public static void setYmax(double ymax) {
		FunctionManager.ymax = ymax;
	}

	public static double getGridStepX2D() {
		return gridStepX2D;
	}

	public static void setGridStepX2D(double gridStepX2D) {
		FunctionManager.gridStepX2D = gridStepX2D;
	}

	public static double getGridStepY2D() {
		return gridStepY2D;
	}

	public static void setGridStepY2D(double gridStepY2D) {
		FunctionManager.gridStepY2D = gridStepY2D;
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
			for (double x = xmin; x <= xmax; x += gridStepX2D) {
				GL11.glVertex2d(x, ymin);
				GL11.glVertex2d(x, ymax);
			}
			for (double y = ymin; y <= ymax; y += gridStepY2D) {
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

		if (is3D) {
			GL11.glLineWidth(2f);
			EquationEditor[] funcs = new EquationEditor[0];
			funcs = functions.toArray(funcs);
			for (EquationEditor ee : funcs) {
				Function f = ee.getFunction();
				GL11.glBegin(GL11.GL_LINES);
				GL11.glColor3dv(f.getGraphColor());
				for (double x = xmin; x <= xmax; x += delta3D) {
					for (double y = ymin; y <= ymax; y += delta3D) {
						double basepoint = f.evalPoint(x, y, t);
						double xoffset = f.evalPoint(x + delta3D, y, t);
						double yoffset = f.evalPoint(x, y + delta3D, t);
						if (f.useMethodForColor()) {
							Color c = Color.getHSBColor((float) f.getEvaluatedHue(x, y, t), 1.0f, 1.0f);
							GL11.glColor3d(((double) c.getRed()) / 255, ((double) c.getGreen()) / 255, ((double) c.getBlue()) / 255);
						}
						if (x > xmax - delta3D) {
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d(x, y + delta3D, yoffset);
						} else if (y > ymax - delta3D) {
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d(x + delta3D, y, xoffset);
						} else {
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d(x, y + delta3D, yoffset);
							GL11.glVertex3d(x, y, basepoint);
							GL11.glVertex3d(x + delta3D, y, xoffset);
						}
					}
				}
				GL11.glEnd();
				double[] color = f.getGraphColor();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glColor4d(color[0], color[1], color[2], 0.25d);
				for (double x = xmin; x < xmax; x += delta3D) {
					for (double y = ymin; y < ymax; y += delta3D) {
						double basepoint = f.evalPoint(x, y, t);
						double xoffset = f.evalPoint(x + delta3D, y, t);
						double yoffset = f.evalPoint(x, y + delta3D, t);
						double bothoffset = f.evalPoint(x + delta3D, y + delta3D, t);
						if (f.useMethodForColor()) {
							Color c = Color.getHSBColor((float) f.getEvaluatedHue(x, y, t), 1.0f, 1.0f);
							GL11.glColor4d(((double) c.getRed()) / 255, ((double) c.getGreen()) / 255, ((double) c.getBlue()) / 255, 0.25d);
						}
						GL11.glVertex3d(x, y, basepoint);
						GL11.glVertex3d(x + delta3D, y, xoffset);
						GL11.glVertex3d(x + delta3D, y + delta3D, bothoffset);
						GL11.glVertex3d(x, y + delta3D, yoffset);
					}
				}
				GL11.glEnd();
			}
		} else {
			GL11.glLineWidth(3f);
			EquationEditor[] funcs = new EquationEditor[0];
			funcs = functions.toArray(funcs);
			for (EquationEditor ee : funcs) {
				Function f = ee.getFunction();
				GL11.glBegin(GL11.GL_LINES);
				GL11.glColor3dv(f.getGraphColor());
				for (double x = xmin; x <= xmax; x += delta2D) {
					double basepoint = f.evalPoint(x, 0, t);
					double xoffset = f.evalPoint(x + delta2D, 0, t);
					if (f.useMethodForColor()) {
						Color c = Color.getHSBColor((float) f.getEvaluatedHue(x, 0, t), 1.0f, 1.0f);
						GL11.glColor3d(((double) c.getRed()) / 255, ((double) c.getGreen()) / 255, ((double) c.getBlue()) / 255);
					}
					GL11.glVertex2d(x, basepoint);
					GL11.glVertex2d(x + delta2D, xoffset);
				}
				GL11.glEnd();
			}
		}
	}

}
