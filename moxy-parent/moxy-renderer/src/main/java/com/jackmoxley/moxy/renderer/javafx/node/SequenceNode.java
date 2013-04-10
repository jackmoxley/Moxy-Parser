package com.jackmoxley.moxy.renderer.javafx.node;

import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import com.jackmoxley.moxy.renderer.javafx.node.functional.ListFunctionalNode;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;

public class SequenceNode extends ListFunctionalNode<SequenceRule> {

	public SequenceNode(SequenceRule rule) {
		super(rule);
	}

	protected void unbindChildren(RuleNode<?> first, RuleNode<?> second) {
		
		second.layoutXProperty().unbind();

		Path path = getPath(first, 0);

		if (path == null) {
			return;
		}

		MoveTo move = (MoveTo) path.getElements().get(0);
		move.xProperty().unbind();
		move.yProperty().unbind();

		HLineTo line = (HLineTo) path.getElements().get(1);
		line.xProperty().unbind();
		
		removePath(path, first);

	}

	protected void bindChildren(RuleNode<?> first, RuleNode<?> second) {

		Path path = getPath(first, 0);

		HLineTo line = null;
		if (path == null) {
			path = new Path();
			MoveTo move = new MoveTo();
			move.xProperty().bind(first.endXProperty());
			move.yProperty().bind(first.stubYProperty());
			move.setAbsolute(true);
			path.getElements().add(move);

			line = new HLineTo();
			line.setAbsolute(true);
			path.getElements().add(line);
			addPath(path, first);
		} else {
			line = (HLineTo) path.getElements().get(1);
		}

		line.xProperty().bind(second.layoutXProperty());
		second.layoutXProperty().bind(first.endXProperty().add(gapProperty()));
	}

	@Override
	protected void bindChild(RuleNode<?> node) {
		node.stubYProperty().bind(ruleNode.layoutToStubYProperty());
	}

	@Override
	protected void unbindChild(RuleNode<?> node) {
		node.stubYProperty().unbind();
	}

}
