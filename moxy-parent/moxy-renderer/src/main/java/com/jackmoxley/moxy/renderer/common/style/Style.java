package com.jackmoxley.moxy.renderer.common.style;

import java.awt.Color;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class Style {

	public final static double MARGIN = 10;
	public final static double QUARTER_MARGIN = MARGIN / 4;
	public final static double HALF_MARGIN = MARGIN / 2;
	public final static double DOUBLE_MARGIN = MARGIN * 2;
	protected Style style = BASE_STYLE;
	protected Style highlight = BASE_HIGHLIGHT;
	/**
	 * The hue color.
	 */
	private static float hue = 0.15f;
	/**
	 * The background color for terminal rectangles.
	 */
	public static Color bgTerm = Color.getHSBColor(hue, 0.5f, 0.3f);
	/**
	 * The foreground color for terminals.
	 */
	public static Color fg = Color.getHSBColor(hue, 0.1f, 1.0f);
	/**
	 * The background color for nonterminal rectangles.
	 */
	public static Color bgFunc = Color.getHSBColor(hue, 0.5f, 0.9f);
	/**
	 * The border color for all rectangles. Also used for shadows.
	 */
	public static Color shadow = Coloured.changeAlpha( Color.getHSBColor(hue, 0.3f, 0.5f), 200);
	
	public static LineStyle BASE_LINE = new LineStyle(bgTerm,MARGIN,MARGIN);
	public static PolygonStyle BASE_POLYGON = new PolygonStyle(fg,BASE_LINE);
	public static ShadowStyle BASE_SHADOW = new ShadowStyle(shadow,QUARTER_MARGIN,QUARTER_MARGIN);
	public static TextStyle BASE_TEXT = new TextStyle(bgTerm,"Arial",10,false,false,MARGIN);
	public static LineStyle HIGHLIGHT_LINE = new LineStyle(fg,QUARTER_MARGIN,QUARTER_MARGIN);
	public static PolygonStyle HIGHLIGHT_POLYGON = new PolygonStyle(bgTerm,HIGHLIGHT_LINE);


	public static final Style BASE_STYLE = new Style(BASE_LINE,BASE_POLYGON,BASE_SHADOW,BASE_TEXT,MARGIN);
	public static final Style BASE_HIGHLIGHT = new Style(HIGHLIGHT_LINE,HIGHLIGHT_POLYGON,BASE_SHADOW,BASE_TEXT,MARGIN);
	
	public final LineStyle lineStyle;
	public final PolygonStyle polygonStyle;
	public final ShadowStyle shadowStyle;
	public final TextStyle textStyle;
	public final double margin;

	/**
	 * @param lineStyle
	 * @param polygonStyle
	 * @param shadowStyle
	 * @param textStyle
	 * @param margin
	 */
	public Style(LineStyle lineStyle, PolygonStyle polygonStyle, ShadowStyle shadowStyle, TextStyle textStyle,
			double margin) {
		super();
		this.lineStyle = lineStyle;
		this.polygonStyle = polygonStyle;
		this.shadowStyle = shadowStyle;
		this.textStyle = textStyle;
		this.margin = margin;
	}

	public Style withLine(LineStyle lineStyle) {
		return new Style(lineStyle, polygonStyle, shadowStyle, textStyle, margin);
	}

	public Style withPolygon(PolygonStyle polygonStyle) {
		return new Style(lineStyle, polygonStyle, shadowStyle, textStyle, margin);
	}

	public Style withShadow(ShadowStyle shadowStyle) {
		return new Style(lineStyle, polygonStyle, shadowStyle, textStyle, margin);
	}

	public Style withText(TextStyle textStyle) {
		return new Style(lineStyle, polygonStyle, shadowStyle, textStyle, margin);
	}

	public Style withMargin(double margin) {
		return new Style(lineStyle, polygonStyle, shadowStyle, textStyle, margin);
	}

	public Style withHue(float hue) {
		return new Style(lineStyle.withHue(hue), polygonStyle.withHue(hue).withLineHue(
				hue), shadowStyle.withHue(hue), textStyle.withHue(hue), margin);
	}

	public Style withHue(int hue) {
		return new Style(lineStyle.withHue(hue), polygonStyle.withHue(hue).withLineHue(
				hue), shadowStyle.withHue(hue), textStyle.withHue(hue), margin);
	}

	public Style withCornerRadius(Vertex cornerRadius) {
		return new Style(lineStyle.withCornerRadius(cornerRadius),
				polygonStyle.withCornerRadius(cornerRadius), shadowStyle, textStyle, margin);
	}

	public Style withCornerRadius(double cornerRadius) {
		return new Style(lineStyle.withCornerRadius(cornerRadius),
				polygonStyle.withCornerRadius(cornerRadius), shadowStyle, textStyle, margin);
	}

	public Style withCornerRadius(double cornerRadiusX, double cornerRadiusY) {
		return new Style(lineStyle.withCornerRadius(cornerRadiusX, cornerRadiusX),
				polygonStyle.withCornerRadius(cornerRadiusX, cornerRadiusX), shadowStyle, textStyle, margin);
	}

	public Style withCornerRadiusX(double cornerRadiusX) {
		return new Style(lineStyle.withCornerRadiusX(cornerRadiusX),
				polygonStyle.withCornerRadiusX(cornerRadiusX), shadowStyle, textStyle, margin);
	}

	public Style withCornerRadiusY(double cornerRadiusY) {
		return new Style(lineStyle.withCornerRadiusY(cornerRadiusY),
				polygonStyle.withCornerRadiusY(cornerRadiusY), shadowStyle, textStyle, margin);
	}

}
