package com.functiongrapher.ui;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.functiongrapher.callbacks.KeyCallback;
import com.functiongrapher.function.FunctionManager;

public class GraphWindow {

	private static long windowID;
	private static boolean is3D = false;
	private static boolean isfullscreen = false;
	private static boolean controlslocked = false;
	private static boolean takescreenshot = false;
	private static BufferedImage screenshot = null;
	private static float yawspeed = 0.0f;

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

	private static int mouse1state;
	private static int prevmouse1state;

	public static void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		boolean initResult = GLFW.glfwInit();
		if (initResult == false) {
			throw new IllegalStateException("GLFW init failed");
		}

		windowID = GLFW.glfwCreateWindow(640, 480, "FunctionGrapher", MemoryUtil.NULL, MemoryUtil.NULL);
		if (windowID == MemoryUtil.NULL) {
			throw new IllegalStateException("Window creation failed");
		}

		GLFW.glfwSetKeyCallback(windowID, new KeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				System.out.println(String.valueOf(key) + " pressed");
				if (action == GLFW.GLFW_PRESS) {

					switch (key) {
					case GLFW.GLFW_KEY_F11:
						if (isfullscreen) {
							GLFW.glfwSetWindowMonitor(windowID, GLFW.glfwGetPrimaryMonitor(), 0, 0, 1920, 1080, (int) MemoryUtil.NULL);
						} else {
							GLFW.glfwSetWindowMonitor(windowID, MemoryUtil.NULL, 100, 100, 640, 480, (int) MemoryUtil.NULL);
						}
						isfullscreen = !isfullscreen;
						break;
					}
				}
			}

		});
		try {
			/*
			InputStream s = GraphWindow.class.getResourceAsStream("/assets/images/fgicon32.png");
			BufferedImage i = ImageIO.read(s);
			byte[] pixelData = ((DataBufferByte) i.getRaster().getDataBuffer()).getData();
			ByteBuffer buf = ByteBuffer.allocateDirect(pixelData.length);
			buf.order(ByteOrder.nativeOrder());
			buf.put(pixelData);
			buf.flip();
			Buffer img = new Buffer(buf);
			GLFW.glfwSetWindowIcon(windowID, img);
			*/
		} catch (Exception e) {
			System.out.println("Could not set window icon!");
			e.printStackTrace();
		}
		GLFW.glfwMakeContextCurrent(windowID);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(windowID);

		GL.createCapabilities();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

		loop();
	}

	public static void setIs3D(boolean is3D) {
		GraphWindow.is3D = is3D;
	}

	public static void loop() {

		while (!glfwWindowShouldClose(windowID)) {

			int[] width = new int[1], height = new int[1];
			GLFW.glfwGetWindowSize(windowID, width, height);
			double dwidth = (double) width[0], dheight = (double) height[0];
			GL11.glViewport(0, 0, (int) dwidth, (int) dheight);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();

			if (is3D) {
				createPerspective((double) camerafov, dwidth / dheight, 0.1, 1000);
			} else {
				createOrthographic(width[0], height[0], -10.0f, 10.0f, -10.0f, 10.0f);
			}

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();

			// ------------ TRANSFORMATION ------------

			if (is3D) {
				GL11.glTranslatef(0.0f, 0.0f, -camerazoom);
				GL11.glRotatef(camerapitch + temppitch, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(cameraroll, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(camerayaw + tempyaw, 0.0f, 0.0f, 1.0f);
			}

			// ------------ -------------- ------------

			// ------------ RENDERING CODE ------------

			FunctionManager.drawFunctions(is3D, (double) totfps / 10);

			camerayaw += yawspeed;

			// ------------ -------------- ------------

			GL11.glPopMatrix();
			GLFW.glfwSwapBuffers(windowID);
			GLFW.glfwPollEvents();

			// ------------ MOUSE FUNCTION ------------

			mouse1state = GLFW.glfwGetMouseButton(windowID, GLFW.GLFW_MOUSE_BUTTON_1);
			boolean zoomin = GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_RIGHT_BRACKET) == 1;
			boolean zoomout = GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_LEFT_BRACKET) == 1;

			if (!controlslocked) {
				if (prevmouse1state == 0 && mouse1state == 1) {
					double[] xpos = new double[1];
					double[] ypos = new double[1];
					GLFW.glfwGetCursorPos(windowID, xpos, ypos);
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
					GLFW.glfwGetCursorPos(windowID, xpos, ypos);
					tempyaw = (float) (((double) (xpos[0] - mousex)) / 3);
					temppitch = (float) (((double) (ypos[0] - mousey)) / 3);
				}
			}

			if (zoomin && camerazoom > 5) {
				camerazoom /= 1.05;
			} else if (zoomout && camerazoom < 300) {
				camerazoom *= 1.05;
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

		GLFW.glfwDestroyWindow(windowID);
		WindowManager.killWindows();

	}

	private static void createPerspective(double fovy, double aspect, double znear, double zfar) {
		double fh = Math.tan(fovy / 360 * Math.PI) * znear;
		double fw = fh * aspect;
		GL11.glFrustum(-fw, fw, -fh, fh, znear, zfar);
	}

	private static void createOrthographic(int width, int height, float xmin, float xmax, float ymin, float ymax) {
		double ow = (-xmin + xmax) / 2;
		double oh = (-ymin + ymax) / 2;
		GL11.glOrtho(-ow, ow, -oh, oh, -100, 100);
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
		isfullscreen = flag;
		if (flag) {
			GLFW.glfwSetWindowMonitor(windowID, GLFW.glfwGetPrimaryMonitor(), 0, 0, 1920, 1080, (int) MemoryUtil.NULL);
		} else {
			GLFW.glfwSetWindowMonitor(windowID, MemoryUtil.NULL, 100, 100, 640, 480, (int) MemoryUtil.NULL);
		}
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
