package com.functiongrapher.ui.textures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class GlyphManager {

	private static HashMap<String, GlyphSet> glyphsets = new HashMap<String, GlyphSet>();

	public static void drawString(String name, String s, double x, double y, double z, double height) {
		double curx = x;
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		for (char c : s.toCharArray()) {
			Glyph g = glyphsets.get(name).get(c);
			g.bind();
			double width = (g.getGlyphWidth() / (double) g.getWidth());
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3d(curx, y, z);
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex3d(curx, y + height, z);
			GL11.glTexCoord2d(width, 0);
			
			GL11.glVertex3d(curx + (width * height), y + height, z);
			GL11.glTexCoord2d(width, 1);
			GL11.glVertex3d(curx + (width * height), y, z);
			GL11.glTexCoord2d(0, 1);
			
			GL11.glEnd();
			curx += (width * height);
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static double stringWidth(String name, String s, double height) {
		double curx = 0;
		for (char c : s.toCharArray()) {
			Glyph g = glyphsets.get(name).get(c);
			double width = (g.getGlyphWidth() / (double) g.getWidth());
			curx += (width * height);
		}
		return curx;
	}

	public static void generateGlyphSet(String name, Font font, int texwidth, int texheight) {
		// 32 <= x < 127
		GlyphSet set = new GlyphSet();
		for (char c = 32; c < 127; c++) {
			BufferedImage img = new BufferedImage(texwidth, texheight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) img.getGraphics();
			g.setBackground(new Color(255, 255, 255, 0));
			g.clearRect(0, 0, texwidth, texheight);
			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(String.valueOf(c), 0, texheight - (texheight / 4));

			int textureID = TextureManager.createTexture(name + "_" + String.valueOf(c), img);
			int glyphwidth = g.getFontMetrics().stringWidth(String.valueOf(c));
			Glyph glyph = new Glyph(c, glyphwidth, texwidth, texheight, textureID);
			set.add(c, glyph);
		}
		glyphsets.put(name, set);
	}

}
