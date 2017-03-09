package com.functiongrapher.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import com.functiongrapher.lang.LanguageManager;
import com.functiongrapher.lang.LanguageManager.Language;
import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.service.ServiceManager;
import com.functiongrapher.threading.ThreadManager;
import com.functiongrapher.ui.gfx.GraphicsController;
import com.functiongrapher.ui.textures.TextureManager;
import com.functiongrapher.ui.windows.GraphWindow;
import com.functiongrapher.ui.windows.TableWindow;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;

public class Main {

	public static void main(String[] args) {

		System.setProperty("org.lwjgl.librarypath", ".");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		ServiceManager.init();

		LanguageManager.setLanguage(Language.EN_US);
		ProgramLogger.init();

		ProgramLogger.info("Starting program...");
		WindowManager.showSplashScreen(000, 1000);
		ProgramLogger.setSplashScreenSubtext("Loading lang file...");
		LanguageManager.loadLangFile();
		ProgramLogger.setSplashScreenSubtext("Opening vars window...");
		WindowManager.addWindow(new VarsWindow(), ProgramInfo.WINDOW_VARS_NAME_INTERNAL);
		WindowManager.addWindow(new TableWindow(), ProgramInfo.WINDOW_TABLE_NAME_INTERNAL);

		// TODO remember the FPS timer...

		ThreadManager.createAndStartThread(() -> {
			GraphicsController.initGLFW();
			GraphWindow.setWindowID(GraphicsController.getWindowID());
			GraphicsController.attachCallbacks();
			GraphicsController.initGL();

			BufferedImage i = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Graphics g = i.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 15, 15);
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, 15, 15);
			g.drawString("test", 0, 8);
			// GL11.glEnable(GL11.GL_TEXTURE_2D);
			try {
				BufferedImage i2 = ImageIO.read(new File("C:/Users/Hanavan/Desktop/Images/test_item.png"));
				TextureManager.createTexture("test", i);
			} catch (Exception e) {
				e.printStackTrace();
			}

			GraphWindow.loop();
			ProgramLogger.info("Beginning shutdown...");
			GraphicsController.shutdown();
			ThreadManager.stopThreads();
			ProgramLogger.info("Shutdown successful. Goodbye!");
			ProgramLogger.shutdown();
		});
	}

}