package com.functiongrapher.ui;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.main.ProgramInfo;

public class GraphWindow {

	private static long window;
	private static boolean is3D = false;
	private static boolean isfullscreen = false;
	private static boolean controlslocked = false;
	private static boolean takescreenshot = false;
	private static BufferedImage screenshot = null;
	private static float yawspeed = 0.0f;

	private static int cameraxpos = 0;
	private static int cameraypos = 0;
	private static float camerapitch = -50.0f;
	private static float camerayaw = 0.0f;
	private static float cameraroll = 0.0f;
	private static float camerafov = 70.0f;
	private static float camerazoom = 30.0f;

	private static int fpscnt = 0;
	private static long totfps = 0;

	private static float temppitch;
	private static float tempyaw;

	private static int mousex;
	private static int mousey;
	
	private static int windowx;
	private static int windowy;
	private static int windowwidth;
	private static int windowheight;

	private static int mouse1state;
	private static int prevmouse1state;

	public static void mouseScrolled(double amount) {
		if (camerazoom >= 5 && camerazoom <= 300) {
			camerazoom /= (amount * 0.25) + 1;
		}
		if (camerazoom < 5) {
			camerazoom = 5;
		}
		if (camerazoom > 300) {
			camerazoom = 300;
		}
	}

	public static void keyStateChanged(int key, int action) {
		if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
			switch (key) {
				case GLFW.GLFW_KEY_A :
					cameraxpos++;
					break;
				case GLFW.GLFW_KEY_D :
					cameraxpos--;
					break;
				case GLFW.GLFW_KEY_S :
					cameraypos++;
					break;
				case GLFW.GLFW_KEY_W :
					cameraypos--;
					break;
				case GLFW.GLFW_KEY_LEFT :
					camerayaw -= 3;
					break;
				case GLFW.GLFW_KEY_RIGHT :
					camerayaw += 3;
					break;
				case GLFW.GLFW_KEY_DOWN :
					camerapitch -= 3;
					break;
				case GLFW.GLFW_KEY_UP :
					camerapitch += 3;
					break;
				case GLFW.GLFW_KEY_RIGHT_BRACKET :
					if (camerazoom >= 5) {
						camerazoom /= 1.2;
					}
					if (camerazoom < 5) {
						camerazoom = 5;
					}
					break;
				case GLFW.GLFW_KEY_LEFT_BRACKET :
					if (camerazoom <= 300) {
						camerazoom *= 1.2;
					}
					if (camerazoom > 300) {
						camerazoom = 300;
					}
					break;
				case GLFW.GLFW_KEY_F7 :
					WindowManager.setWindowVisibility("vars", true);
					break;
				case GLFW.GLFW_KEY_F11 :
					isfullscreen = !isfullscreen;
					setIsFullscreen(isfullscreen);
					break;
			}
		}
	}

	public static void setIs3D(boolean is3D) {
		GraphWindow.is3D = is3D;
	}

	public static void loop() {

		while (!glfwWindowShouldClose(window)) {

			int[] width = new int[1], height = new int[1];
			GLFW.glfwGetWindowSize(window, width, height);
			double dwidth = (double) width[0], dheight = (double) height[0];
			GL11.glViewport(0, 0, (int) dwidth, (int) dheight);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();

			if (is3D) {
				createPerspective((double) camerafov, dwidth / dheight, 0.1, 1000);
			} else {
				createOrthographic(width[0], height[0], (float) FunctionManager.getXmin(), (float) FunctionManager.getXmax(), (float) FunctionManager.getYmin(), (float) FunctionManager.getYmax());
			}

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();

			// ------------ TRANSFORMATION ------------

			if (is3D) {
				GL11.glTranslatef(0.0f, 0.0f, -camerazoom);
				GL11.glRotatef(camerapitch + temppitch, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(cameraroll, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(camerayaw + tempyaw, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(cameraxpos, cameraypos, 0.0f);
			}

			// ------------ -------------- ------------

			// ------------ RENDERING CODE ------------

			FunctionManager.drawFunctions(is3D, (double) totfps / 10);

			camerayaw += yawspeed;

			// ------------ -------------- ------------

			GL11.glPopMatrix();
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();

			// ------------ MOUSE FUNCTION ------------

			mouse1state = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_1);

			if (!controlslocked) {
				if (prevmouse1state == 0 && mouse1state == 1) {
					double[] xpos = new double[1];
					double[] ypos = new double[1];
					GLFW.glfwGetCursorPos(window, xpos, ypos);
					mousex = (int) xpos[0];
					mousey = (int) ypos[0];
				} else if (prevmouse1state == 1 && mouse1state == 0) {
					camerayaw += tempyaw;
					camerapitch += temppitch;
					tempyaw = 0;
					temppitch = 0;
				}
				if (mouse1state == 1) {
					double[] xpos = new double[1];
					double[] ypos = new double[1];
					GLFW.glfwGetCursorPos(window, xpos, ypos);
					tempyaw = (float) (((double) (xpos[0] - mousex)) / 3);
					temppitch = (float) (((double) (ypos[0] - mousey)) / 3);
				}
			}

			prevmouse1state = mouse1state;

			// ------------ -------------- ------------

			// check if we need to create a screenshot.
			if (takescreenshot) {
				takescreenshot = false;
				GL11.glReadBuffer(GL11.GL_FRONT);
				int pixelformat = 4;
				ByteBuffer buffer = BufferUtils.createByteBuffer(width[0] * height[0] * pixelformat);
				GL11.glReadPixels(0, 0, width[0], height[0], GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
				screenshot = new BufferedImage(width[0], height[0], BufferedImage.TYPE_INT_RGB);
				for (int x = 0; x < width[0]; x++) {
					for (int y = 0; y < height[0]; y++) {
						int i = (x + (width[0] * y)) * pixelformat;
						int r = buffer.get(i) & 0xFF;
						int g = buffer.get(i + 1) & 0xFF;
						int b = buffer.get(i + 2) & 0xFF;
						screenshot.setRGB(x, height[0] - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
					}
				}
			}

			fpscnt++;
			totfps++;

		}

	}

	private static void createPerspective(double fovy, double aspect, double znear, double zfar) {
		double fh = Math.tan(fovy / 360 * Math.PI) * znear;
		double fw = fh * aspect;
		GL11.glFrustum(-fw, fw, -fh, fh, znear, zfar);
	}

	private static void createOrthographic(int width, int height, float xmin, float xmax, float ymin, float ymax) {
		GL11.glOrtho(xmin, xmax, ymin, ymax, -1, 1);
	}

	public static void setControlState(boolean controllable) {
		controlslocked = !controllable;
	}

	public static void setCameraYaw(float yaw) {
		camerayaw = yaw;
	}

	public static void setCameraPitch(float pitch) {
		camerapitch = pitch;
	}

	public static void setCameraRoll(float roll) {
		cameraroll = roll;
	}

	public static void setYawRotateSpeed(float speed) {
		yawspeed = speed;
	}

	public static void setIsFullscreen(boolean flag) {
		if (flag) {
			int[] xpos = new int[1]; int[] ypos = new int[1];
			GLFW.glfwGetWindowPos(window, xpos, ypos);
			windowx = xpos[0];
			windowy = ypos[0];
			int[] width = new int[1], height = new int[1];
			GLFW.glfwGetWindowSize(window, width, height);
			windowwidth = width[0];
			windowheight = height[0];
			GLFW.glfwSetWindowMonitor(window, ((VarsWindow) WindowManager.getWindow("vars")).getSelectedMonitor(), 0, 0, ((VarsWindow) WindowManager.getWindow("vars")).getSelectedResolution().getWidth(), ((VarsWindow) WindowManager.getWindow("vars")).getSelectedResolution().getHeight(), (int) MemoryUtil.NULL);
		} else {
			GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, windowx, windowy, windowwidth, windowheight, (int) MemoryUtil.NULL);
		}
	}

	public static boolean isWindowVisible() {
		try {
			int flag = GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_VISIBLE);
			return flag == GLFW.GLFW_TRUE;
		} catch (Exception e) {
			return false;
		}
	}

	public static void setWindowID(long window) {
		GraphWindow.window = window;
	}

	public static int getFPS() {
		return fpscnt;
	}

	public static void resetFPS() {
		fpscnt = 0;
	}

	public static void takeScreenshot() {
		takescreenshot = true;
	}

	public static BufferedImage getScreenshot() {
		return screenshot;
	}

}
