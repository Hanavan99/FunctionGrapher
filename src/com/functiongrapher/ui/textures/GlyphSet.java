package com.functiongrapher.ui.textures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GlyphSet {

	private final int charWidth = 16;
	private final int charHeight = 16;
	private final int verticalChars = 8;
	private final int horizontalChars = 16;

	private BufferedImage img;

	public GlyphSet() {
		img = new BufferedImage(charWidth * horizontalChars, charHeight * verticalChars, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, horizontalChars * charWidth, verticalChars * charHeight);
	}

	public void drawGlyph(char c) {
		if (c < 128) {
			Graphics g = img.getGraphics();
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(c), (c % horizontalChars) * charWidth, (c / horizontalChars) * charHeight);
		}
	}

	public BufferedImage get(char c) {
		return img.getSubimage((c % horizontalChars) * charWidth, (c / horizontalChars) * charHeight, charWidth,
				charHeight);
	}

	public BufferedImage getString(String s) {
		BufferedImage img = new BufferedImage(s.length() * charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		for (char c : s.toCharArray()) {
			if (c < 128) {
				g.drawImage(get(c), c * charWidth, 0, null);
			}
		}
		return img;
	}

}
