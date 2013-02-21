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
import java.awt.geom.Rectangle2D;

import com.jackmoxley.moxy.renderer.swing.Dimensions;
import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;

public class SequenceNode extends FunctionalNode{

	public SequenceNode(SequenceRule rule, Node<?> parent) {
		super(rule,parent);
	}


	@Override
	public Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft) {
		double x = stubLeft.getX();
		double y  = stubLeft.getY();
		Point2D current;
		double localStubY = 0;
		double hBottom = 0;
		double w = 0;
		for(Node<?> node: nodes){
			
			w += MARGIN;
			current = new Point2D.Double(x+w, y);
			node.prerender(g, current);
			w += node.dim.size.getX();
			
			localStubY = Math.max(localStubY, node.dim.localStubY);
			hBottom = Math.max(hBottom, node.dim.size.getY()-node.dim.localStubY);
		}
		w += MARGIN;
		
		double h = localStubY+hBottom;
		Point2D size =  new Point2D.Double(w,h);

		Point2D stubRight = new Point2D.Double(x+w, y);
		Rectangle2D bounds = new Rectangle2D.Double(x, y-localStubY, w, h); 
		
		return new Dimensions(size,bounds,stubLeft,stubRight,localStubY);
	}

	@Override
	public void render(Graphics2D g) {

		g.setColor(Color.BLACK);
		Point2D last = dim.stubLeft;
		boolean subMO = false;
		for(Node<?> node: nodes){
			
			if(node.mouseOver){
				g.setColor(Color.RED);
				subMO = true;
			} else if(subMO) {
				g.setColor(Color.RED);
				subMO = false;
			} else {
				g.setColor(Color.BLACK);
			}
			g.draw(new Line2D.Double(last, node.dim.stubLeft));
			node.render(g);
			last = node.dim.stubRight;
		}
		if(subMO) {
			g.setColor(Color.RED);
		}
		g.draw(new Line2D.Double(last, dim.stubRight));

		g.setColor(Color.BLACK);
	}

}
