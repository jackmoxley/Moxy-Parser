/**
 * Copyright (C) 2013  John Orlando Keleshian Moxley
 * 
 * Unless otherwise stated by the license provided by the copyright holder.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jackmoxley.moxy.renderer.swing.node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jackmoxley.moxy.renderer.swing.Dimensions;
import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.rule.functional.list.OrRule;

public class OptionNode extends FunctionalNode {

	public OptionNode(OrRule rule, Node<?> parent) {
		super(rule, parent);
	}

	@Override
	public Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft) {

		double w = 0;
		double h = 0;
		for (Node<?> node : nodes) {
			h += MARGIN;
			node.prerender(g, new Point2D.Double(stubLeft.getX() + MARGIN, h));
			h += node.dim.size.getY();
			w = Math.max(w, node.dim.size.getX());
		}

		w += DOUBLE_MARGIN;
		h += MARGIN;

		double cX = w / 2 + stubLeft.getX();

		double localStubY = h / 2;
		for (Node<?> node : nodes) {
			double nX = cX - (node.dim.size.getX() / 2);
			double nY = (stubLeft.getY() - localStubY)
					+ node.dim.stubLeft.getY() + node.dim.localStubY;
			node.prerender(g, new Point2D.Double(nX, nY));
		}

		Point2D size = new Point2D.Double(w, h);
		Point2D stubRight = new Point2D.Double(stubLeft.getX() + w,
				stubLeft.getY());
		Rectangle2D bounds = new Rectangle2D.Double(stubLeft.getX(),
				stubLeft.getY() - localStubY, w, h);

		return new Dimensions(size, bounds, stubLeft, stubRight, localStubY);
	}

	@Override
	public void render(Graphics2D g) {
		double leftConnect = dim.stubLeft.getX() + HALF_MARGIN;
		double rightConnect = dim.stubRight.getX() - HALF_MARGIN;

		List<Node<?>> nodes = new ArrayList<Node<?>>(this.nodes);
		Collections.sort(nodes, new Comparator<Node<?>>() {

			@Override
			public int compare(Node<?> o1, Node<?> o2) {
				if (o1.mouseOver) {
					if (o2.mouseOver) {
						return new Double(o1.dim.stubLeft.getY())
								.compareTo(o2.dim.stubLeft.getY());
					} else
						return 1;
				} else if (o2.mouseOver) {
					return -1;
				}
				return new Double(o1.dim.stubLeft.getY())
						.compareTo(o2.dim.stubLeft.getY());
			}
		});
		for (Node<?> node : nodes) {
			node.render(g);
			double difference = dim.stubLeft.getY() - node.dim.stubLeft.getY();
			double connectHeight = 0;
			if (difference >= 0) {
				connectHeight = Math.min(difference, MARGIN) / 2;
			} else {
				connectHeight = Math.max(difference, -MARGIN) / 2;
			}
			// IN
			// first turn

			if (node.mouseOver) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLACK);
			}
			g.draw(new QuadCurve2D.Double(dim.stubLeft.getX(), dim.stubLeft
					.getY(), leftConnect, dim.stubLeft.getY(), leftConnect,
					dim.stubLeft.getY() - connectHeight));

			g.draw(new QuadCurve2D.Double(leftConnect, node.dim.stubLeft.getY()
					+ connectHeight, leftConnect, node.dim.stubLeft.getY(),
					leftConnect + HALF_MARGIN, node.dim.stubLeft.getY()));
			//
			//
			g.draw(new Line2D.Double(leftConnect, dim.stubLeft.getY()
					- connectHeight, leftConnect, node.dim.stubLeft.getY()
					+ connectHeight));
			g.draw(new Line2D.Double(leftConnect + HALF_MARGIN,
					node.dim.stubLeft.getY(), node.dim.stubLeft.getX(),
					node.dim.stubLeft.getY()));

			// OUT
			g.draw(new QuadCurve2D.Double(dim.stubRight.getX(), dim.stubRight
					.getY(), rightConnect, dim.stubRight.getY(), rightConnect,
					dim.stubRight.getY() - connectHeight));
			//
			g.draw(new QuadCurve2D.Double(rightConnect, node.dim.stubRight
					.getY() + connectHeight, rightConnect, node.dim.stubRight
					.getY(), rightConnect - HALF_MARGIN, node.dim.stubRight
					.getY()));
			//
			g.draw(new Line2D.Double(rightConnect, dim.stubRight.getY()
					- connectHeight, rightConnect, node.dim.stubRight.getY()
					+ connectHeight));
			g.draw(new Line2D.Double(rightConnect - HALF_MARGIN,
					node.dim.stubRight.getY(), node.dim.stubRight.getX(),
					node.dim.stubRight.getY()));

			g.setColor(Color.BLACK);
		}
	}
}
