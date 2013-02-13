package com.jackmoxley.moxy.renderer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.jackmoxley.moxy.rule.Rule;

public class RulePanel extends JPanel {

	private static final long serialVersionUID = 6663828151603122932L;

	Point2D lastMouseLocation = new Point2D.Double(-1, -1);
	boolean setup = true;
	boolean mouseClicked = true;
	Node<?> node;
	Rule rule;
	Point2D oldSize = new Point2D.Double(0, 0);
	double stubY = 0;
	ScrolledPanel parent;
	Map<Key, Object> hints;
	Font base = new Font("Arial", Font.PLAIN, 10);
	Font largeBold = new Font("Arial", Font.BOLD, 12);
	boolean odd;

	String name;
	String syntax;

	public RulePanel(Rule rule, ScrolledPanel parent) {
		this(rule, parent, rule.toString(), null);
	}

	public RulePanel(Rule rule, ScrolledPanel parent, String name, String syntax) {
		super();
		this.parent = parent;
		this.rule = rule;
		this.name = name;
		this.syntax = syntax;
		MouseAdapter listener = new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				lastMouseLocation = new Point2D.Double(e.getX(), e.getY());
				super.mouseMoved(e);

				if (node.mouseOver(lastMouseLocation)) {
					RulePanel.this.repaint();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				mouseMoved(e);
				mouseClicked = true;
			}

		};
		this.addMouseMotionListener(listener);

		this.addMouseListener(listener);

		setup();
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);

		parent.updatePreferedSize(this, preferredSize);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		Graphics2D g2D = (Graphics2D) g;
		g2D.addRenderingHints(hints);
		if(odd){
			g2D.setColor(Color.LIGHT_GRAY);
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			g2D.setColor(Color.WHITE);
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g2D.setColor(Color.BLACK);
		g2D.setFont(base);
		prerender(g2D);
		
		Polygon rightArrow = new Polygon();
		rightArrow.addPoint(0, (int)(stubY+ Node.HALF_MARGIN + Node.DOUBLE_MARGIN));
		rightArrow.addPoint(0, (int)((stubY- Node.HALF_MARGIN) + Node.DOUBLE_MARGIN));
		rightArrow.addPoint((int)Node.MARGIN, (int)(stubY+ Node.DOUBLE_MARGIN));
		g2D.fill(rightArrow);
		rightArrow.translate((int)Node.MARGIN, 0);
		g2D.fill(rightArrow);
		rightArrow.translate((int)(node.dim.size.getX() + Node.MARGIN), 0);
		g2D.fill(rightArrow);
		
		Polygon leftArrow = new Polygon();
		leftArrow.addPoint((int)Node.MARGIN, (int)(stubY+ Node.HALF_MARGIN + Node.DOUBLE_MARGIN));
		leftArrow.addPoint((int)Node.MARGIN, (int)((stubY- Node.HALF_MARGIN) + Node.DOUBLE_MARGIN));
		leftArrow.addPoint(0, (int)(stubY+ Node.DOUBLE_MARGIN));
		leftArrow.translate((int)(node.dim.size.getX() + Node.DOUBLE_MARGIN + Node.MARGIN), 0);
		g2D.fill(leftArrow);
		
		node.render(g2D);

		g2D.setColor(Color.BLACK);
		g2D.setFont(largeBold);
		g2D.drawString(name, (int) Node.MARGIN , (int) Node.MARGIN + largeBold.getSize());
		

		g2D.setFont(base);
		g2D.drawString(syntax, (int) Node.MARGIN , (int) (largeBold.getSize() + Node.DOUBLE_MARGIN + base.getSize() + node.dim.size.getY()));
		
		System.out.println("Drew "+name);
	}
	
	private void prerender(Graphics2D g2D) {
		node.prerender(g2D,
				new Point2D.Double(Node.DOUBLE_MARGIN, stubY + Node.DOUBLE_MARGIN));
		stubY = node.dim.localStubY;
		if (!fuzzyEquals(oldSize, node.dim.size, Node.DOUBLE_MARGIN)) {
			Dimension swingSize = new Dimension();
			swingSize.setSize(Math.max(base.getStringBounds(name, g2D.getFontRenderContext()).getWidth(), node.dim.size.getX())  + Node.DOUBLE_MARGIN + Node.DOUBLE_MARGIN,
					node.dim.size.getY()  + (largeBold.getSize() + Node.DOUBLE_MARGIN + base.getSize()) + Node.DOUBLE_MARGIN);
			this.setPreferredSize(swingSize);
			oldSize = node.dim.size;
			prerender(g2D);
		}
	}

	NodeFactory factory = new NodeFactory();

	public void setup() {
		this.setDoubleBuffered(true);

		hints = new HashMap<Key, Object>();
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		node = factory.createNode(rule, null);

	}

	private static final boolean fuzzyEquals(Point2D p1, Point2D p2,
			double threshold) {
		if (Math.abs(p1.getX() - p2.getX()) > threshold) {
			return false;
		}
		if (Math.abs(p1.getY() - p2.getY()) > threshold) {
			return false;
		}
		return true;
	}

	public void setOdd(boolean odd) {
		this.odd = odd;
	}

}
