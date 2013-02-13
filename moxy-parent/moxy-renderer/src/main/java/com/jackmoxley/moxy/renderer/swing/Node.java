package com.jackmoxley.moxy.renderer.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.jackmoxley.moxy.rule.Rule;

public abstract class Node<R extends Rule> {

	public final static double MARGIN = 10;
	public final static double QUARTER_MARGIN = MARGIN / 4;
	public final static double HALF_MARGIN = MARGIN / 2;
	public final static double DOUBLE_MARGIN = MARGIN * 2;

	/**
	 * The hue color.
	 */
	private static float hue = 0.15f;
	/**
	 * The background color for terminal rectangles.
	 */
	public static Color bgTerm = Color.getHSBColor(hue, 0.1f, 1.0f);
	/**
	 * The foreground color for terminals.
	 */
	public static Color fg = Color.getHSBColor(hue, 1.0f, 0.5f);
	/**
	 * The background color for nonterminal rectangles.
	 */
	public static Color bgFunc = Color.getHSBColor(hue, 0.5f, 0.9f);
	/**
	 * The border color for all rectangles. Also used for shadows.
	 */
	public static Color border = Color.getHSBColor(hue, 1.0f, 0.1f);

	protected final R rule;

	protected final Node<?> parent;
	
	public boolean mouseOver;

	public Dimensions dim;

	public Node(R rule, Node<?> parent) {
		super();
		this.rule = rule;
		this.parent = parent;
	}

	public Node<?> getParent() {
		return parent;
	}

	public void prerender(Graphics2D g, Point2D stubLeft) {
		dim = calculateDimensions(g, stubLeft);
	}
	
	public abstract Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft);
	

	public abstract void render(Graphics2D g);

	public Rule getRule() {
		return rule;
	}


	public static Point2D add(Point2D a, Point2D b) {
		return new Point2D.Double(a.getX() + b.getX(), a.getY() + b.getY());
	}

	public static Point2D subtract(Point2D a, Point2D b) {
		return new Point2D.Double(a.getX() - b.getX(), a.getY() - b.getY());
	}
	

	public static RoundRectangle2D round(Rectangle2D rect, double arcX, double arcY) {
		return new RoundRectangle2D.Double(rect.getX(), rect.getY(),rect.getWidth(),rect.getHeight(),arcX,arcY);
	}

	/**
	 * 
	 * @param mouseLoc
	 * @return whether to redraw
	 */
	public boolean mouseOver(Point2D mouseLoc) {
		if(mouseOver != dim.bounds.contains(mouseLoc)){
			mouseOver = !mouseOver;
			return true;
		}
		return false;
	}


}
