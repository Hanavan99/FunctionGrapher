package com.functiongrapher.ui.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.GraphWindow;
import com.functiongrapher.ui.VarsWindow;
import com.functiongrapher.ui.WindowManager;

public class GraphicsController {

	private static long window;

	private static GLFWKeyCallback keycb;
	private static GLFWMouseButtonCallback mousecb;
	private static GLFWScrollCallback scrollcb;
	private static GLFWWindowCloseCallback windowcb;

	public static long getWindowID() {
		return window;
	}

	public static void initGLFW() {
		ProgramLogger.setSplashScreenSubtext("Initializing GLFW...");
		GLFWErrorCallback.createPrint(System.err).set();

		if (GLFW.glfwInit() == false) {
			throw new IllegalStateException("GLFW init failed");
		}

		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 16);
		GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);

		try {
			InputStream s = GraphWindow.class.getResourceAsStream(ProgramInfo.ICON64_PATH);
			BufferedImage i = ImageIO.read(s);
			byte[] pixelData = ((DataBufferByte) i.getRaster().getDataBuffer()).getData();
			ByteBuffer buf = ByteBuffer.allocateDirect(pixelData.length);
			buf.order(ByteOrder.nativeOrder());
			buf.put(pixelData);
			buf.flip();
			Buffer img = new Buffer(buf);
			GLFW.glfwSetWindowIcon(window, img);
		} catch (Exception e) {
			ProgramLogger.LOGGER.warning("Could not set window icon!");
			e.printStackTrace();
		}
		
		window = GLFW.glfwCreateWindow(640, 480, ProgramInfo.PROGRAM_NAME, MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL) {
			throw new IllegalStateException("Window creation failed");
		}

		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);

		PointerBuffer b = GLFW.glfwGetMonitors();
		long[] monitors = new long[b.capacity()];
		for (int i = 0; i < b.capacity(); i++) {
			monitors[i] = b.get(i);
		}
		((VarsWindow) WindowManager.getWindow("vars")).setMonitorPointers(monitors);
	}

	public static void attachCallbacks() {
		ProgramLogger.setSplashScreenSubtext("Attaching Callbacks...");
		GLFW.glfwSetKeyCallback(window, keycb = GLFWKeyCallback.create((window, key, scancode, action, mods) -> GraphWindow.keyStateChanged(key, action)));
		GLFW.glfwSetMouseButtonCallback(window, mousecb = GLFWMouseButtonCallback.create((window, button, action, mods) -> {

		}));
		GLFW.glfwSetScrollCallback(window, scrollcb = GLFWScrollCallback.create((window, xoffset, yoffset) -> GraphWindow.mouseScrolled(yoffset)));
		GLFW.glfwSetWindowCloseCallback(window, windowcb = GLFWWindowCloseCallback.create((window) -> GraphWindow.windowClosing()));
	}

	public static void initGL() {
		ProgramLogger.setSplashScreenSubtext("Initializing OpenGL...");
		GL.createCapabilities();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void shutdown() {
		GLFW.glfwDestroyWindow(window);
		keycb.free();
		mousecb.free();
		scrollcb.free();
		windowcb.free();
		WindowManager.killWindows();
		GLFW.glfwTerminate();
	}
}
