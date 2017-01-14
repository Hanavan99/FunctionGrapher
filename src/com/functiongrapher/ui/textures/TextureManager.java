package com.functiongrapher.ui.textures;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextureManager {

	private static HashMap<String, GlyphSet> fonts = new HashMap<String, GlyphSet>();
	
	public static void generateGlyphSet(Font f, String name) {
		GlyphSet s = new GlyphSet();
		for (char c = 32; c < 127; c++) {
			s.drawGlyph(c);
		}
		fonts.put(name, s);
	}
	
	public static BufferedImage getGlyph(String glyphName, char c) {
		return fonts.get(glyphName).get(c);
	}
	
}
