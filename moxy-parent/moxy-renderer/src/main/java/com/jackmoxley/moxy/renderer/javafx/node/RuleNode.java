package com.jackmoxley.moxy.renderer.javafx.node;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;

import com.jackmoxley.moxy.renderer.javafx.component.LineWithText;
import com.jackmoxley.moxy.renderer.javafx.component.StubPane;
import com.jackmoxley.moxy.renderer.javafx.property.math.PropertyMath;
import com.jackmoxley.moxy.rule.Rule;


public class RuleNode<R extends Rule> extends StubPane {

	protected LineWithText in;
	protected LineWithText out;
	protected StubPane ruleNode;
	protected ParentNode parentNode;
	protected final R rule;
	protected final RuleGraphNode graph;
	
	/**
	 * 
	 */
	public RuleNode(R rule, ParentNode parentNode, RuleGraphNode graph) {
		super();
		this.graph = graph;
		this.parentNode = parentNode;
		this.rule = rule;
		this.ruleNode = new StubPane();
		in = new LineWithText();
		out = new LineWithText();
		setup();
	}
	
	protected void setup(){

		in.stubYProperty().bind(this.layoutToStubYProperty());
		ruleNode.stubYProperty().bind(this.layoutToStubYProperty());
		out.stubYProperty().bind(this.layoutToStubYProperty());
		ruleNode.layoutXProperty().bind(in.endXProperty());
		out.layoutXProperty().bind(ruleNode.endXProperty());

		in.getStyleClass().add("in");
		out.getStyleClass().add("out");
		ruleNode.getStyleClass().add("contents");

		this.getChildren().add(in);
		this.getChildren().add(out);
		this.getChildren().add(ruleNode);
		
		this.getStyleClass().add(getClass().getSimpleName());
		this.getStyleClass().add("rule");
		this.getStyleClass().add(rule.getClass().getSimpleName());
	}
	
	@Override
	public void changed(ObservableValue<? extends Bounds> observable,
			Bounds oldValue, Bounds newValue) {
		graph.requestLayout();
	}
	
	@Override
	protected DoubleExpression generateLayoutYToStubYExpression() {
		return PropertyMath.max(in.layoutToStubYProperty(),out.layoutToStubYProperty(),ruleNode.layoutToStubYProperty());
	}

	public LineWithText getIn() {
		return in;
	}


	public LineWithText getOut() {
		return out;
	}


	public StubPane getRuleNode() {
		return ruleNode;
	}


	public R getRule() {
		return rule;
	}

	@Override
	protected void onMouseClicked(MouseEvent event) {
		event.consume();
		super.onMouseClicked(event);
	}
	
	public StringProperty inTextProperty(){
		return in.textProperty();
	}

	public StringProperty outTextProperty(){
		return out.textProperty();
	}

	public ParentNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(ParentNode parentNode) {
		this.parentNode = parentNode;
	}

	public RuleGraphNode getGraph() {
		return graph;
	}
	
	public double getXRelativeToGraph(){
		return this.ruleNode.getLocalToSceneTransform().getTx()
		- this.graph.getLocalToSceneTransform().getTx();
	}
	public double getYRelativeToGraph(){
		return this.ruleNode.getLocalToSceneTransform().getTy()
		- this.graph.getLocalToSceneTransform().getTy();
	}
	
	
}
