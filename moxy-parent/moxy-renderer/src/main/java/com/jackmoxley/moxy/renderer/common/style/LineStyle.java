package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class LineStyle extends Coloured<LineStyle> {

	public final Vertex cornerRadius;
	
	/**
	 * @param color
	 * @param cornerRadius
	 */
	public LineStyle(Color color, Vertex cornerRadius) {
		super(color);
		this.cornerRadius = cornerRadius;
	}
	public LineStyle(Color color, double cornerRadiusX, double cornerRadiusY) {
		super(color);
		this.cornerRadius = new Vertex(cornerRadiusX, cornerRadiusY);
	}
	
	public LineStyle(Color color, double cornerRadius) {
		super(color);
		this.cornerRadius = new Vertex(cornerRadius, cornerRadius);
	}

	@Override
	public LineStyle withColor(Color color) {
		return new LineStyle(color,cornerRadius);
	}
	
	public LineStyle withCornerRadius(Vertex cornerRadius){
		return new LineStyle(color,cornerRadius);
	}

	public LineStyle withCornerRadius(double cornerRadius){
		return new LineStyle(color,cornerRadius);
	}
	

	public LineStyle withCornerRadius( double cornerRadiusX, double cornerRadiusY){
		return new LineStyle(color,cornerRadiusX, cornerRadiusY);
	}
	
	public LineStyle withCornerRadiusX( double cornerRadiusX){
		return new LineStyle(color,cornerRadius.setX(cornerRadiusX));
	}

	public LineStyle withCornerRadiusY( double cornerRadiusY){
		return new LineStyle(color,cornerRadius.setY(cornerRadiusY));
	}

}
