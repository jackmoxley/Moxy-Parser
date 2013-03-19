	package com.jackmoxley.moxy.renderer.node;

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.renderable.TextBox;
import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;

public class SymbolNode extends Node<SymbolRule> {

	private final TextBox box;


	public SymbolNode(Spatial parent, SymbolRule rule) {
		super(parent, rule);
		box = new TextBox(this, ((SymbolRule) rule).getPointer());
		add(box);
	}

	@Override
	protected void prepareNode(Graphics2D g) {
		box.prepare(g);
		this.copyBounds(box);
	}
}
