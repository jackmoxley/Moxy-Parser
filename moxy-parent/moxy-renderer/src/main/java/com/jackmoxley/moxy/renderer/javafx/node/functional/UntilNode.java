package com.jackmoxley.moxy.renderer.javafx.node.functional;

import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.single.UntilRule;

public class UntilNode extends MinMaxNode<UntilRule> {


	private RuleNode<?> untilNode;
	public UntilNode(UntilRule rule, ParentNode parent, RuleGraphNode graph) {
		super(rule, parent, graph);
		
	}
	public void constructNode() {
		super.constructNode();
		Rule subRule = rule.getUntil();

		if (untilNode != null) {
			unbindUntil(untilNode);
			this.getRuleNode().getChildren().remove(untilNode);
			untilNode.boundsInLocalProperty().removeListener(this);
		}
		untilNode = NodeFactory.getInstance().getNodeFor(graph, this,subRule);
		bindUntil(untilNode);
		this.getRuleNode().getChildren().add(untilNode);
		untilNode.boundsInLocalProperty().addListener(this);
		untilNode.getStyleClass().add("until");
	}
	
	protected void unbindUntil(RuleNode<?> untilNode) {
		// TODO Auto-generated method stub
		
	}
	protected void bindUntil(RuleNode<?> untilNode) {
		untilNode.stubYProperty().bind(this.getChildNode().stubYProperty());
		untilNode.layoutXProperty().bind(this.getChildNode().endXProperty().add(gapProperty()));
	}
	
	
}
