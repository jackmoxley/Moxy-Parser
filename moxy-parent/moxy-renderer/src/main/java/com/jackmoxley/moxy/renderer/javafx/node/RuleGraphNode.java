package com.jackmoxley.moxy.renderer.javafx.node;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.scene.shape.Circle;

import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.renderer.javafx.component.StubPane;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssMetaData;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssMetaDataList;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssDoubleProperty;

public class RuleGraphNode extends StubPane implements ParentNode, Styleable {

	private final DoubleProperty radiusProperty = new RegisteredCssDoubleProperty<RuleGraphNode>(
			this, "radius", 5);

	private static final RegisteredCssMetaDataList cssMetaDataList = new RegisteredCssMetaDataList(
			StubPane.getClassCssMetaData(), new RegisteredCssMetaData<>(
					"-moxy-radius", StyleConverter.getSizeConverter(),
					RuleGraphNode.class, "radius"));

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return cssMetaDataList.getMetaData();
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return getClassCssMetaData();
	}

	protected RuleGraph ruleGraph;
	protected RuleNode<?> ruleNode;

	protected Circle in;
	protected Circle out;

	public RuleGraphNode(RuleGraph ruleGraph) {
		super();
		this.ruleGraph = ruleGraph;
		this.getStyleClass().add("RuleGraph");
		in = new Circle();
		out = new Circle();
	}

	public void setup() {

		in.radiusProperty().bind(this.radiusProperty());
		out.radiusProperty().bind(this.radiusProperty());
		ruleNode = NodeFactory.getInstance().getNodeFor(this, this,
				ruleGraph.getRule());
		this.getChildren().add(ruleNode);
		this.getChildren().add(in);
		this.getChildren().add(out);
		in.centerXProperty().bind(radiusProperty().multiply(2));
		ruleNode.layoutXProperty().bind(
				in.centerXProperty().add(radiusProperty()));
		out.centerXProperty().bind(
				ruleNode.endXProperty().add(radiusProperty()));
		in.centerYProperty().bind(ruleNode.stubYProperty());
		out.centerYProperty().bind(ruleNode.stubYProperty());

		in.getStyleClass().add("in");
		out.getStyleClass().add("out");
		ruleNode.getStyleClass().add("ruleRoot");
	}

	public DoubleProperty radiusProperty() {
		return radiusProperty;
	}

	public double getRadius() {
		return radiusProperty.get();
	}

	public void setRadius(double value) {
		radiusProperty.set(value);
	}

}
