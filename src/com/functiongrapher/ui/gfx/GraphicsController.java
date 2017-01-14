package com.functiongrapher.ui.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
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
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.textures.TextureManager;
import com.functiongrapher.ui.windows.GraphWindow;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;

public class GraphicsController {

	private static long window;

	private static int[] textures;

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
			ProgramLogger.warning("Could not set window icon!");
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		TextureManager.generateGlyphSet(new Font("Arial", Font.PLAIN, 12), "Arial");
		
		textures = new int[] { createTexture("test", GL13.GL_TEXTURE0) };
	}

	public static int[] getTextures() {
		return textures;
	}

	public static int createTexture(String text, int textureUnit) {
		int tWidth = 64;
		int tHeight = 64;

		BufferedImage img = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().setColor(Color.RED);
		img.getGraphics().fillRect(0, 0, tWidth, tHeight);
		img.getGraphics().setColor(Color.BLACK);
		img.getGraphics().drawString(text, 4, 4);
		DataBuffer data = img.getData().getDataBuffer();
		byte[] b = new byte[data.getSize() * 4];
		for (int i = 0; i < b.length; i += 4) {
			b[i] = (byte) (data.getElem(i / 4) >> 16);
			b[i + 1] = (byte) (data.getElem(i / 4) >> 8);
			b[i + 2] = (byte) (data.getElem(i / 4) >> 0);
			b[i + 3] = (byte) (data.getElem(i / 4) >> 24);
		}
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.flip();

		// Create a new texture object in memory and bind it
		int texId = GL11.glGenTextures();
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

		// All RGB bytes are aligned to each other and each component is 1 byte
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		// Upload the texture data and generate mip maps (for scaling)
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, tWidth, tHeight, 0, GL11.GL_RGBA8, GL11.GL_UNSIGNED_BYTE, bb);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		// Setup the ST coordinate system
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		// Setup what to do when the texture has to be scaled
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

		return texId;
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
