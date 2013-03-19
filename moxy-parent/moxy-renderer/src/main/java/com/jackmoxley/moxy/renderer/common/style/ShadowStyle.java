package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class ShadowStyle extends Coloured<ShadowStyle> {

	public final Vertex shadowOffset;
	
	/**
	 * @param color
	 * @param shadowOffset
	 */
	public ShadowStyle(Color color, Vertex shadowOffset) {
		super(color);
		this.shadowOffset = shadowOffset;
	}
	public ShadowStyle(Color color, double shadowOffsetX, double shadowOffsetY) {
		super(color);
		this.shadowOffset = new Vertex(shadowOffsetX, shadowOffsetY);
	}
	public ShadowStyle(Color color, double shadowOffset) {
		super(color);
		this.shadowOffset = new Vertex(shadowOffset, shadowOffset);
	}

	@Override
	public ShadowStyle withColor(Color color) {
		return new ShadowStyle(color,shadowOffset);
	}
	
	public ShadowStyle withShadowOffset(Vertex shadowOffset){
		return new ShadowStyle(color,shadowOffset);
	}
	

	public ShadowStyle withShadowOffset( double shadowOffsetX, double shadowOffsetY){
		return new ShadowStyle(color,shadowOffsetX, shadowOffsetY);
	}
	public ShadowStyle withShadowOffset( double shadowOffset){
		return new ShadowStyle(color,shadowOffset);
	}
	
	public ShadowStyle withShadowOffsetX( double shadowOffsetX){
		return new ShadowStyle(color,shadowOffset.setX(shadowOffsetX));
	}

	public ShadowStyle withShadowOffsetY( double shadowOffsetY){
		return new ShadowStyle(color,shadowOffset.setY(shadowOffsetY));
	}

}
