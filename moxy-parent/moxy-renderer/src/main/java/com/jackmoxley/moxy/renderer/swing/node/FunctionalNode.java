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

}
