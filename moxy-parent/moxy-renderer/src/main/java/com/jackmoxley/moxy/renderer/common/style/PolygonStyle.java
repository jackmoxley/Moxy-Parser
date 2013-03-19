package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class PolygonStyle extends Coloured<PolygonStyle> {

	public final LineStyle lineStyle;
	/*
	 * is merely a clone to the cornerRadius on the lineStyle style 
	 */
	public final Vertex cornerRadius;

	/**
	 * @param color
	 * @param lineStyle
	 */
	public PolygonStyle(Color color, LineStyle lineStyle) {
		super(color);
		this.lineStyle = lineStyle;
		this.cornerRadius = lineStyle.cornerRadius;
	}
	
	public PolygonStyle(Color fillColor, Color lineColor, Vertex cornerRadius) {
		this(fillColor, new LineStyle(lineColor,cornerRadius));
	}
	
	public PolygonStyle(Color fillColor, Color lineColor,  double cornerRadiusX, double cornerRadiusY) {
		this(fillColor, new LineStyle(lineColor,cornerRadiusX, cornerRadiusY));
	}

	@Override
	public PolygonStyle withColor(Color color) {
		return new PolygonStyle(color, lineStyle);
	}

	public PolygonStyle withLine(LineStyle lineStyle) {
		return new PolygonStyle(color, lineStyle);
	}

	public PolygonStyle withLineColor(Color color) {
		return new PolygonStyle(color,lineStyle.withColor(color));
	}

	public PolygonStyle withCornerRadius(Vertex cornerRadius) {
		return new PolygonStyle(color,lineStyle.withCornerRadius(cornerRadius));
	}
	public PolygonStyle withCornerRadius(double cornerRadius) {
		return new PolygonStyle(color,lineStyle.withCornerRadius(cornerRadius));
	}

	public PolygonStyle withLineHue(float hue) {
		return new PolygonStyle(color,lineStyle.withHue(hue));
	}

	public PolygonStyle withCornerRadius(double cornerRadiusX, double cornerRadiusY) {
		return new PolygonStyle(color,lineStyle.withCornerRadius(cornerRadiusX, cornerRadiusY));
	}

	public PolygonStyle withLineHue(int hue) {
		return new PolygonStyle(color,lineStyle.withHue(hue));
	}

	public PolygonStyle withLineAlpha(int alpha) {
		return new PolygonStyle(color,lineStyle.withAlpha(alpha));
	}

	public PolygonStyle withCornerRadiusX(double cornerRadiusX) {
		return new PolygonStyle(color,lineStyle.withCornerRadiusX(cornerRadiusX));
	}

	public PolygonStyle withLineAlpha(float alpha) {
		return new PolygonStyle(color,lineStyle.withAlpha(alpha));
	}

	public PolygonStyle withCornerRadiusY(double cornerRadiusY) {
		return new PolygonStyle(color,lineStyle.withCornerRadiusY(cornerRadiusY));
	}

	
}
