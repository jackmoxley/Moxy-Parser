package com.jackmoxley.moxy.renderer.javafx.node;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import com.jackmoxley.moxy.rule.Rule;

public class LinkToNode extends BoxNode<Rule> implements InvalidationListener {
	private final RuleNode<? extends Rule> node;
	private final Path in;
	private final Path out;

	public LinkToNode(Scene scene, RuleNode<? extends Rule> node) {
		super(node.getRule());
		this.node = node;
		in = new Path();
		out = new Path();
		in.strokeProperty().set(Color.GREEN);
		in.getElements().add(new MoveTo());
		in.getElements().add(new LineTo());
		out.strokeProperty().set(Color.RED);
		MoveTo outMoveTo = new MoveTo();
		out.getElements().add(outMoveTo);
		out.getElements().add(new LineTo());

		Group root = (Group)scene.getRoot();
		root.getChildren().add(out);
		root.getChildren().add(in);
		
		node.ruleNode.stubYProperty().addListener(this);
		node.ruleNode.endXProperty().addListener(this);
		this.ruleNode.endXProperty().addListener(this);
		this.ruleNode.stubYProperty().addListener(this);
		this.ruleNode.localToSceneTransformProperty().addListener(this);
		node.ruleNode.localToSceneTransformProperty().addListener(this);
		
		calculatePaths();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			Group root = (Group)node.getScene().getRoot();
			root.getChildren().remove(out);
			root.getChildren().remove(in);
		} catch(Throwable t){ }
	}

	protected void calculatePaths() {
		MoveTo move = (MoveTo)in.getElements().get(0);
		move.setX(this.ruleNode.getLocalToSceneTransform().getTx() );
		move.setY(this.ruleNode.getLocalToSceneTransform().getTy() + this.ruleNode.layoutToStubYProperty().doubleValue());
		LineTo line = (LineTo) in.getElements().get(1);
		line.setX(node.ruleNode.getLocalToSceneTransform().getTx());
		line.setY(node.ruleNode.getLocalToSceneTransform().getTy()+ node.ruleNode.layoutToStubYProperty().doubleValue());

		move = (MoveTo)out.getElements().get(0);
		move.setX(this.ruleNode.getLocalToSceneTransform().getTx() + this.ruleNode.getWidth());
		move.setY(this.ruleNode.getLocalToSceneTransform().getTy()+ this.ruleNode.layoutToStubYProperty().doubleValue());
		line = (LineTo) out.getElements().get(1);
		line.setX(node.ruleNode.getLocalToSceneTransform().getTx() + node.ruleNode.getWidth());
		line.setY(node.ruleNode.getLocalToSceneTransform().getTy()+ node.ruleNode.layoutToStubYProperty().doubleValue());
	}

	@Override
	public void invalidated(Observable arg0) {
		calculatePaths();
	}

	@Override
	protected String generateText() {
		return rule.toString();
	}

}
