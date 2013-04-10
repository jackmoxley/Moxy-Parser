package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.scene.shape.Rectangle;

import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

public abstract class FunctionalNode<FR extends FunctionalRule> extends
		RuleNode<FR> {
	protected Rectangle outline;

	public FunctionalNode(FR fRule) {
		super(fRule);
		this.getStyleClass().add("functional");
	}

	protected void setup() {
		outline = new Rectangle();
		outline.getStyleClass().add("outline");
		this.getChildren().add(outline);

		outline.layoutXProperty().bind(ruleNode.layoutXProperty());
		outline.layoutYProperty().bind(ruleNode.layoutYProperty());
		outline.widthProperty().bind(ruleNode.widthProperty());
		outline.heightProperty().bind(ruleNode.heightProperty());
		super.setup();
	}

	protected abstract void bindChild(RuleNode<?> node);

	protected abstract void unbindChild(RuleNode<?> node);

}
