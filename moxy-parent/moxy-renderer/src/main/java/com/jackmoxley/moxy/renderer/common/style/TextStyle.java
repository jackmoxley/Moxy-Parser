package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;
import java.awt.Font;

public class TextStyle extends Coloured<TextStyle> {

	public final Font font;
	public final double margin;

	public TextStyle(Color color, String fontName, int fontSize, boolean bold,
			boolean italic, double margin) {
		super(color);
		int fontType = Font.PLAIN;
		if (bold) {
			fontType = fontType & Font.BOLD;
		}
		if (italic) {
			fontType = fontType & Font.ITALIC;
		}

		this.font = new Font(fontName, fontType, fontSize);
		this.margin = margin;

	}

	public TextStyle(Color color, Font font, double margin) {
		super(color);
		this.font = font;
		this.margin = margin;

	}

	@Override
	public TextStyle withColor(Color color) {
		return new TextStyle(color, font, margin);
	}

	public TextStyle withFont(Font font) {
		return new TextStyle(color, font, margin);
	}

	public TextStyle withFont(String fontStyle, int fontSize, boolean bold,
			boolean italic) {
		return new TextStyle(color, fontStyle, fontSize, bold, italic, margin);
	}

	public TextStyle withFontSize(float fontSize) {
		return new TextStyle(color, font.deriveFont(fontSize), margin);
	}

	public TextStyle withFontName(String fontName) {
		return new TextStyle(color, fontName, font.getSize(), font.isBold(),
				font.isItalic(), margin);
	}

	public TextStyle withBold(boolean bold) {
		return new TextStyle(color, font.getName(), font.getSize(), bold,
				font.isItalic(), margin);
	}

	public TextStyle withItalic(boolean italic) {
		return new TextStyle(color, font.getName(), font.getSize(), font.isBold(),
				italic, margin);
	}

	public TextStyle withPlain() {
		return new TextStyle(color, font.getName(), font.getSize(), false, false,
				margin);
	}

	public TextStyle withMargin(double margin) {
		return new TextStyle(color, font, margin);
	}
}
