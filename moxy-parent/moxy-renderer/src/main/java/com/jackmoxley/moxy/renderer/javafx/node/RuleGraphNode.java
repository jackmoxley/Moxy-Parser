package com.jackmoxley.moxy.renderer.javafx.node;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;

import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.renderer.javafx.component.StubPane;

public class RuleGraphNode extends StubPane implements ParentNode{

	protected RuleGraph ruleGraph;
	protected RuleNode<?> ruleNode;

	protected Circle in;
	protected Circle out;


	public RuleGraphNode(Scene scene, RuleGraph ruleGraph) {
		super();
		this.ruleGraph = ruleGraph;
		this.getStyleClass().add("RuleGraph");
		in = new Circle();
		in.getStyleClass().add("in");
		in.radiusProperty().bind(this.gapProperty());
		out = new Circle();
		out.getStyleClass().add("out");
		out.radiusProperty().bind(this.gapProperty());
		ruleNode = NodeFactory.getInstance().getNodeFor(scene, this,ruleGraph.getRule());
		ruleNode.getStyleClass().add("root");

		this.getChildren().add(ruleNode);
		this.getChildren().add(in);
		this.getChildren().add(out);
		in.centerXProperty().bind(gapProperty().multiply(2));
		ruleNode.layoutXProperty().bind(in.centerXProperty().add(gapProperty()));
		out.centerXProperty().bind(ruleNode.endXProperty().add(gapProperty()));
		in.centerYProperty().bind(ruleNode.stubYProperty());
		out.centerYProperty().bind(ruleNode.stubYProperty());
	}
	
	
}
