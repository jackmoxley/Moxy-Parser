package com.jackmoxley.moxy.renderer.node;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

public abstract class FunctionalNode<FN extends FunctionalRule> extends Node<FN> {

	protected List<Node<?>> nodes = new ArrayList<Node<?>>();

	public FunctionalNode(Spatial parent, FN rule) {
		super(parent, rule);
		
	}
	
	protected void constructNodes(){

		clearNodes();
		for (Rule subRule : rule) {
			this.add(NodeFactory.getInstance().getNodeFor(this,subRule));
		}
	}

	protected void clearNodes() {
		while(!nodes.isEmpty()){
			this.remove(last());
		}
	}

	protected void remove(Node<?> node) {
		nodes.remove(node);
		super.remove(node);
	}

	public void add(Node<?> node) {
		nodes.add(node);
		super.add(node);
	}


	@Override
	public void prepareNode(Graphics2D g) {
		for (Node<?> node : nodes) {
			node.prepare(g);
		}
	}
	
	protected Node<?> first(){
		return nodes.get(0);
	}
	
	protected Node<?> last(){
		return nodes.get(nodes.size()-1);
	}
}
