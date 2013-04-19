package com.jackmoxley.moxy.renderer.javafx.node;

import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.rule.Rule;

public abstract class NodeFactory {

	private static NodeFactory instance;

	public static NodeFactory getInstance() {
		if(instance == null){
			setInstance(new SimpleNodeFactory());
		}
		return instance;
	}

	public static void setInstance(NodeFactory instance) {
		NodeFactory.instance = instance;
	}

	public abstract RuleNode<?> getNodeFor(RuleGraphNode graph,ParentNode parent,  Rule subRule);
	
	
	public abstract RuleGraphNode getGraphFor(RuleGraph graph);
}
