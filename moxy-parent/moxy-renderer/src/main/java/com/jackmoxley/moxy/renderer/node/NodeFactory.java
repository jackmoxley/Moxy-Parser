package com.jackmoxley.moxy.renderer.node;

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

	public abstract Node<?> getNodeFor(FunctionalNode<?> parent, Rule subRule);
	
	
	
}
