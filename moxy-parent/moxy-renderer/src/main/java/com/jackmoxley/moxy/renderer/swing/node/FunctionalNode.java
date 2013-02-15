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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.renderer.swing.NodeFactory;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

public abstract class FunctionalNode extends Node<FunctionalRule> {

	protected List<Node<?>> nodes = new ArrayList<Node<?>>();

	public FunctionalNode(FunctionalRule rule, Node<?> parent) {
		super(rule, parent);

	}

	public void constructNodes(NodeFactory factory) {
		for (Rule sub : rule) {
			nodes.add(factory.createNode(sub, this));

		}
	}

	public boolean mouseOver(Point2D mouseLoc) {

		boolean ret = super.mouseOver(mouseLoc);
		for (Node<?> node : nodes) {
			ret = ret | node.mouseOver(mouseLoc);
		}
		return ret;
	}

	public void mouseClick() {
		mouseClick = mouseOver;
		for (Node<?> node : nodes) {
			node.mouseClick();
		}
	}

}
