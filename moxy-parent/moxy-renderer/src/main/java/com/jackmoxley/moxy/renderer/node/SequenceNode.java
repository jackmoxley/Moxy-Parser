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

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;

public class SequenceNode extends FunctionalNode<SequenceRule> {

	/**
	 * @param rule
	 */
	public SequenceNode(Spatial parent, SequenceRule rule) {
		super(parent, rule);
	}

	@Override
	public void prepareNode(Graphics2D g) {
		Vertex size = Vertex.ZERO;
		double currentStart = 0;
		for (Node<?> node : nodes) {
			node.prepare(g);
			size = size.max(node.size());
		}
		for (Node<?> node : nodes) {
			node.localTranslation(new Vertex(currentStart,size.y/2));
			currentStart = currentStart+node.size().x;
//			System.out.println(currentStart);
		}
//		System.out.println(size());
		this.size(size.setX(currentStart));
//		System.out.println(size());
	}


}
