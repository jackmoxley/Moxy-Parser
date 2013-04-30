package com.jackmoxley.moxy.renderer.javafx.node.functional;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.single.SingleRule;

public abstract class SingleFunctionalNode<FR extends SingleRule> extends
		FunctionalNode<FR> {
	private RuleNode<?> childNode;
	private List<Path> paths;
	protected Rectangle outline;

	public SingleFunctionalNode(FR fRule,ParentNode parent, RuleGraphNode graph) {
		super(fRule,parent, graph);
		this.getStyleClass().add("single");
	}

	protected void setup() {
		paths = new ArrayList<Path>();
		super.setup();
	}

	public void constructNode() {
		paths.clear();
		Rule subRule = rule.getRule();
		RuleNode<?> node = NodeFactory.getInstance().getNodeFor(graph, this,subRule);
		setChildNode(node);
		node.getStyleClass().add("child");
	}

	public RuleNode<?> getChildNode() {
		return childNode;
	}

	protected void setChildNode(RuleNode<?> node) {
		RuleNode<?> old = this.childNode;
		this.childNode = node;
		if (old != null) {
			unbindChild(old);
			this.getRuleNode().getChildren().remove(old);
			old.boundsInLocalProperty().removeListener(this);
		}
		this.getRuleNode().getChildren().add(node);
		bindChild(node);
		node.boundsInLocalProperty().addListener(this);
	}

	protected Path getPath(int index) {
		if (index >= paths.size()) {
			return null;
		}
		return paths.get(index);
	}

	protected void removePath(Path path) {

		path.getElements().clear();

		if (paths.remove(path)) {
			this.getRuleNode().getChildren().remove(path);
		}
	}

	protected void addPath(Path path) {
		paths.add(path);
		path.getStyleClass().add("path");
		this.getRuleNode().getChildren().add(path);
	}

}
