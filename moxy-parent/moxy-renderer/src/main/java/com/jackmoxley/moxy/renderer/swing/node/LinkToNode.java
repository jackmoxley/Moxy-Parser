package com.jackmoxley.moxy.renderer.swing.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;

import com.jackmoxley.moxy.renderer.swing.Node;

public class LinkToNode extends TerminalNode {

	private final Node<?> link;

	public LinkToNode(Node<?> link, Node<?> parent) {
		super(link.getRule(), parent);
		this.link = link;
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(g.getFont().deriveFont(Font.ITALIC));
		super.render(g);
		if (mouseOver) {
			double leftDifX = DOUBLE_MARGIN * 2;
			// Math.min(
			// Math.abs((dim.stubLeft.getX() - link.dim.stubLeft.getX()) / 2),
			// DOUBLE_MARGIN);
			double rightDifX = DOUBLE_MARGIN * 2;
			// Math
			// .min(Math.abs((dim.stubRight.getX() - link.dim.stubRight.getX())
			// / 2),
			// DOUBLE_MARGIN);
			boolean linkAbove = dim.stubLeft.getY() > link.dim.stubLeft.getY();
			double vertical = 0;
			if (linkAbove) {
				vertical = MARGIN;
			} else {
				vertical = -MARGIN;
			}

			g.setColor(Color.green);
			g.draw(new CubicCurve2D.Double(dim.stubLeft.getX(), dim.stubLeft
					.getY(), dim.stubLeft.getX() + leftDifX, dim.stubLeft
					.getY() - vertical, link.dim.stubLeft.getX() - leftDifX,
					link.dim.stubLeft.getY() + vertical, link.dim.stubLeft
							.getX(), link.dim.stubLeft.getY()));

			g.setColor(Color.red);
			g.draw(new CubicCurve2D.Double(dim.stubRight.getX(), dim.stubRight
					.getY(), dim.stubRight.getX() - rightDifX, dim.stubRight
					.getY() - vertical, link.dim.stubRight.getX() + rightDifX,
					link.dim.stubRight.getY() + vertical, link.dim.stubRight
							.getX(), link.dim.stubRight.getY()));
		}
		g.setColor(Color.black);

		g.setFont(g.getFont().deriveFont(Font.PLAIN));
	}

}
