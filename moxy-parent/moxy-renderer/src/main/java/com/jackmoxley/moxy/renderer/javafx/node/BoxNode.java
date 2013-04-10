package com.jackmoxley.moxy.renderer.javafx.node;

import com.jackmoxley.moxy.renderer.javafx.component.TextWithRectangle;
import com.jackmoxley.moxy.rule.Rule;

public abstract class BoxNode<R extends Rule> extends RuleNode<R>{
	public final TextWithRectangle text;
	
	public BoxNode(R rule) {
		super(rule);
		text = new TextWithRectangle();
		text.setString(generateText());
		text.getStyleClass().add("textbox");
		this.getRuleNode().getChildren().add(text);
	}


	protected abstract String generateText();
	
}
