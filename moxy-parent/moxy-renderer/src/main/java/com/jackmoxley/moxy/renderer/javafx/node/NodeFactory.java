package com.jackmoxley.moxy.renderer.javafx.node;

import javafx.scene.Scene;

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

	public abstract RuleNode<?> getNodeFor(Scene scene,ParentNode parent,  Rule subRule);
	
	
	
}
