package com.functiongrapher.ui.textures;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class TextureManager {

	private static final int internalFormat = GL11.GL_RGB;
	private static final int bytesPerPixel = 3;
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static void createTexture(String name, BufferedImage img) {
		try {
			int tex = GL11.glGenTextures();
			System.out.println(tex);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
			GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			float[] data = new float[img.getWidth() * img.getHeight() * bytesPerPixel];
			for (int i = 0; i < img.getWidth() * img.getHeight(); i++) {
				int x = i % img.getWidth();
				int y = i / img.getWidth();
				Color c = new Color(img.getRGB(x, y));
				data[i * bytesPerPixel + 0] = (float) c.getRed() / 255;
				data[i * bytesPerPixel + 1] = (float) c.getGreen() / 255;
				data[i * bytesPerPixel + 2] = (float) c.getBlue() / 255;
				//data[i * 4 + 3] = (float) c.getAlpha() / 255;
				
			}
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, img.getWidth(), img.getHeight(), 0, GL11.GL_RGB, GL11.GL_FLOAT, data);
			Texture t = new Texture(img.getWidth(), img.getHeight(), tex);
			System.out.println(t.getTextureID());
			textures.put(name, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Texture getTexture(String name) {
		return textures.get(name);
	}
	
	public static void generateTexturesForFont(Font f) {
		for (int i = 32; i <= 127; i++) {
			
		}
	}
	
	
	
}
