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
package com.jackmoxley.moxy.renderer.node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.renderable.TextBox;
import com.jackmoxley.moxy.rule.Rule;

public class LinkToNode extends Node<Rule> {

	private final Node<?> link;
	

	private final TextBox box;
	public LinkToNode(Spatial parent, Node<?> link) {
		super(parent, link.getRule());
		this.link = link;
		box = new TextBox(this,link.getRule().toString());
		add(box);
	}



	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		final Vertex linkIn = link.worldTranslation();
		final Vertex linkOut = link.worldTranslation().addX(link.size().x);
		final double tx = g.getTransform().getTranslateX();
		final double ty = g.getTransform().getTranslateY();
		final Vertex world = new Vertex(tx+xOffset(),ty);//box.worldTranslation().add(new Vertex(xOffset(),-yOffset()));
//		System.out.println("Translation x:"+tx+" y:"+ty+" this:"+world+" size:"+size()+" linkSize:"+link.size()+" LinkIn:"+linkIn+" LinkOut:"+linkOut);
		
		g.translate(-tx, -ty);
		g.setColor(Color.green);
		draw(g,world,linkIn);
		g.setColor(Color.red);
		draw(g,world.addX(box.size().x),linkOut);
		g.translate(tx, ty);
		
	}
	
	protected void draw(Graphics2D g, Vertex v1, Vertex v2){
		Vertex left = null;
		Vertex right = null;
		if(v1.x < v2.x){
			left = v1;
			right = v2;
		} else {
			right = v1;
			left = v2;
		}
		Vertex leftControl = left.sub(50,50);
		Vertex rightControl = right.add(50,50);
//		Vertex halfWay = link.sub(me).div(2).flip().add(link);
		CubicCurve2D curve = new CubicCurve2D.Double(left.x,
				left.y, leftControl.x, leftControl.y, rightControl.x, rightControl.y, right.x, right.y);
//		Line2D curve = new Line2D.Double(me.x,me.y,link.x,link.y);
		g.draw(curve);
	}



	@Override
	protected void prepareNode(Graphics2D g) {
		box.prepare(g);
		this.copyBounds(box);
		
	}

}
