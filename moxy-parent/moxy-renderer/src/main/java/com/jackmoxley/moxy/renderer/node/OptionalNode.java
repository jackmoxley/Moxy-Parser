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

import static com.jackmoxley.moxy.renderer.common.dimensions.Vertex.v;

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.renderable.Line;
import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;

public class OptionalNode extends FunctionalNode<OptionalRule> {

	Line line = new Line(this, v(0, 0), v(0, 0), v(0, 0), v(0, 0), v(0, 0), v(0, 0));

	public OptionalNode(Spatial parent, OptionalRule rule) {
		super(parent, rule);
		add(line);
	}

	@Override
	public void prepareNode(Graphics2D g) {

		Node<?> node = first();
		node.prepare(g);
		Style style = StyleManager.currentStyle();
		double cornerX = style.lineStyle.cornerRadius.x;
		double cornerY = style.lineStyle.cornerRadius.y;
		double internalOffset = node.yOffset()+(cornerY/2);
//		System.out.println(nodeBounds);
		node.localTranslation(new Vertex(0,internalOffset));
		line.set(0, new Vertex(cornerX,internalOffset));
		line.set(1,  new Vertex(0,internalOffset));
		line.set(2, new Vertex(0, -(cornerY/2)));
		line.set(3, new Vertex(node.size().x, -(cornerY/2)));
		line.set(4, new Vertex(node.size().x, internalOffset));
		line.set(5, new Vertex(node.size().x-cornerX, internalOffset));
		line.prepare(g);
		this.size(new Vertex(node.size().x, node.size().y+cornerY));
	}



}
