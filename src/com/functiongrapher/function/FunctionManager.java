package com.functiongrapher.function;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.service.IPropertyService;
import com.functiongrapher.service.ServiceManager;
import com.functiongrapher.ui.EquationEditor;
import com.functiongrapher.ui.gfx.GraphicsController;
import com.functiongrapher.ui.panels.EquationPanel;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;

public class FunctionManager {

	private static ArrayList<EquationEditor> functions = new ArrayList<EquationEditor>();

	private static double delta2D = 1d / 32;
	private static double delta3D = 1d / 2;

	public static void addFunction(EquationEditor ee) {
		functions.add(ee);
		EquationPanel panel = ((VarsWindow) WindowManager.getWindow(ProgramInfo.WINDOW_VARS_NAME_INTERNAL)).getEquationPanel();
		panel.add(ee);
		panel.updateList();

	}

	public static void removeFunction(EquationEditor ee) {
		functions.remove(ee);
		EquationPanel panel = ((VarsWindow) WindowManager.getWindow(ProgramInfo.WINDOW_VARS_NAME_INTERNAL)).getEquationPanel();
		panel.remove(ee);
		panel.updateList();
	}

	public static ArrayList<EquationEditor> getFunctions() {
		return functions;
	}

	public static void clearFunctions() {
		functions.clear();
		((VarsWindow) WindowManager.getWindow(ProgramInfo.WINDOW_VARS_NAME_INTERNAL)).getEquationPanel().updateList();
	}

	public static void drawFunctions(double t) {

		FloatBuffer axes = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1);
		
		IPropertyService svc = ServiceManager.getService();
		double xmin = svc.getXMin();
		double xmax = svc.getXMax();
		double ymin = svc.getYMin();
		double ymax = svc.getYMax();

		if (svc.is3D()) {
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
			for (double x = xmin; x <= xmax; x += svc.getGridX()) {
				GL11.glVertex2d(x - xmin % svc.getGridX(), ymin);
				GL11.glVertex2d(x - xmin % svc.getGridX(), ymax);
			}
			for (double y = ymin; y <= ymax; y += svc.getGridY()) {
				GL11.glVertex2d(xmin, y - ymin % svc.getGridY());
				GL11.glVertex2d(xmax, y - ymin % svc.getGridY());
			}
			GL11.glEnd();

			// Draw 2D numbers
			// BufferedImage img = new BufferedImage(64, 64,
			// BufferedImage.TYPE_INT_ARGB);
			// img.getGraphics().drawString("test", 4, 4);
			// DataBuffer data = img.getData().getDataBuffer();
			// byte[] b = new byte[data.getSize() * 4];
			// for (int i = 0; i < b.length; i += 4) {
			// b[i] = (byte) (data.getElem(i / 4) >> 24);
			// b[i + 1] = (byte) (data.getElem(i / 4) >> 16);
			// b[i + 2] = (byte) (data.getElem(i / 4) >> 8);
			// b[i + 3] = (byte) (data.getElem(i / 4) >> 0);
			// }
			// ByteBuffer bb = ByteBuffer.wrap(b);
			// bb.flip();
			// int tex = GL11.glGenTextures();
			// GL13.glActiveTexture(GL13.GL_TEXTURE0);
			// GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			// GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			// GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 64, 64,
			// 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
			// GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			int[] textures = GraphicsController.getTextures();
			int i = 0;
			GL13.glActiveTexture(textures[i]);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[i]);
			GL11.glBegin(GL11.GL_QUADS);
			for (double x = xmin; x <= xmax; x += svc.getGridX()) {
				GL11.glTexCoord2d(0, 0);
				GL11.glVertex2d(x - 0.25d, -0.5d);
				GL11.glTexCoord2d(0, 1);
				GL11.glVertex2d(x - 0.25d, 0d);
				GL11.glTexCoord2d(1, 1);
				GL11.glVertex2d(x + 0.25d, 0d);
				GL11.glTexCoord2d(1, 0);
				GL11.glVertex2d(x + 0.25d, -0.5d);
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

		if (svc.is3D()) {
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
			svc.setCameraYaw(svc.getCameraYaw() + (axes.get(0) * 2));
			svc.setCameraPitch(svc.getCameraPitch() + (axes.get(1) * 2));
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
			xmin -= axes.get(0) / 4;
			xmax -= axes.get(0) / 4;
			ymin -= axes.get(1) / 4;
			ymax -= axes.get(1) / 4;
			xmin *= 1 + axes.get(2);
			xmax *= 1 + axes.get(2);
			ymin *= 1 + axes.get(2);
			ymax *= 1 + axes.get(2);
		}
		ServiceManager.getService().setXMin(xmin);
		ServiceManager.getService().setXMax(xmax);
		ServiceManager.getService().setYMin(ymin);
		ServiceManager.getService().setYMax(ymax);
	}

}
