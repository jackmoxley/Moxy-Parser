package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;

public abstract class Coloured<C extends Coloured<C>> {

	public final Color color;
	
	
	/**
	 * @param color
	 */
	public Coloured(Color color) {
		super();
		this.color = color;
	}

	public static Color changeHue(Color color, float hue) {
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),
				hsbvals);
		return changeAlpha(Color.getHSBColor(hue, hsbvals[1], hsbvals[2]), color.getAlpha());
	}
	
	public static Color changeAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public  C withHue(float hue){
		return withColor(changeHue(color,hue));
	}
	
	public  C withHue(int hue){
		return withColor(changeHue(color,((float)hue)/255.0f));
	}
	
	public  C withAlpha(int alpha){
		return withColor(changeAlpha(color,alpha));
	}
	public  C withAlpha(float alpha){
		return withAlpha((int)(alpha*255));
	}
	
	public abstract C withColor(Color color);
	
}
