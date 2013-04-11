package com.jackmoxley.moxy.renderer.javafx.node;

import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;

public class SymbolNode extends BoxNode<SymbolRule>{
	
	public SymbolNode(SymbolRule rule) {
		super(rule);
	}


	protected String generateText() {
		return rule.getPointer();
	}
	
	
}
