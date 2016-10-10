package com.functiongrapher.ui;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.functiongrapher.callbacks.KeyCallback;
import com.functiongrapher.function.FunctionManager;

public class GraphWindow {

	private static long windowID;
	private static boolean is3D = true;
	private static boolean lastIs3D;
	private static boolean isfullscreen = false;

	private static float camerapitch = -50.0f;
	private static float camerayaw = 0.0f;
	private static float cameraroll = 0.0f;
	private static float camerafov = 70.0f;
	private static float camerazoom = 30.0f;

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
		GLFW.glfwMakeContextCurrent(windowID);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(windowID);

		if (is3D) {
			init3D();
		} else {
			init2D();
		}
		loop();
	}

	public static void setIs3D(boolean is3D) {
		GraphWindow.is3D = is3D;
	}

	public static void init2D() {

	}

	public static void init3D() {
		GL.createCapabilities();
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	public static void loop() {

		while (!glfwWindowShouldClose(windowID)) {

			int[] width = new int[1], height = new int[1];
			GLFW.glfwGetWindowSize(windowID, width, height);
			double dwidth = (double) width[0];
			double dheight = (double) height[0];
			GL11.glViewport(0, 0, (int) dwidth, (int) dheight);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT
					| GL11.GL_DEPTH_BUFFER_BIT /* | GL11.GL_TRANSFORM_BIT */);
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			createPerspective((double) camerafov, dwidth / dheight, 0.1, 1000);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();

			// ------------ TRANSFORMATION ------------

			GL11.glTranslatef(0.0f, 0.0f, -camerazoom);
			GL11.glRotatef(camerapitch + temppitch, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(cameraroll, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(camerayaw + tempyaw, 0.0f, 0.0f, 1.0f);

			// ------------ -------------- ------------

			// ------------ RENDERING CODE ------------

			FunctionManager.drawFunctions();

			// ------------ -------------- ------------

			GL11.glPopMatrix();
			GLFW.glfwSwapBuffers(windowID);
			GLFW.glfwPollEvents();

			// ------------ MOUSE FUNCTION ------------

			mouse1state = GLFW.glfwGetMouseButton(windowID, GLFW.GLFW_MOUSE_BUTTON_1);
			boolean zoomin = GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_RIGHT_BRACKET) == 1;
			boolean zoomout = GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_LEFT_BRACKET) == 1;

			if (prevmouse1state == 0 && mouse1state == 1) {
				System.out.println("Mouse Pressed");
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

			if (zoomin && camerazoom > 5) {
				camerazoom /= 1.05;
			} else if (zoomout && camerazoom < 300) {
				camerazoom *= 1.05;
			}

			prevmouse1state = mouse1state;

			// ------------ -------------- ------------

			// check if we need to change from 2D to 3D.
			if (is3D != lastIs3D) {
				if (is3D) {

				} else {

				}
			}
			lastIs3D = is3D;
		}

		GLFW.glfwDestroyWindow(windowID);
		WindowManager.killWindows();

	}

	private static void createPerspective(double fovy, double aspect, double znear, double zfar) {
		double fh = Math.tan(fovy / 360 * Math.PI) * znear;
		double fw = fh * aspect;
		GL11.glFrustum(-fw, fw, -fh, fh, znear, zfar);
	}

}
