package com.jackmoxley.moxy.renderer.javafx.node;

import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;

public class SymbolNode extends BoxNode<SymbolRule>{
	
	public SymbolNode(SymbolRule rule,ParentNode parent,RuleGraphNode graph) {
		super(rule,parent,graph);
	}


	protected String generateText() {
		return rule.getPointer();
	}
	
	
}
