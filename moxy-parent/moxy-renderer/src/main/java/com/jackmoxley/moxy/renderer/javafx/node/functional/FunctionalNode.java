package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Rectangle;

import com.jackmoxley.moxy.renderer.javafx.component.TextWithRectangle;
import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.renderer.javafx.property.math.PropertyMath;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

public abstract class FunctionalNode<FR extends FunctionalRule> extends
		RuleNode<FR> implements ParentNode {
	protected Rectangle outline;
	protected TextWithRectangle info;

	public FunctionalNode(FR fRule,ParentNode parent) {
		super(fRule,parent);
		this.getStyleClass().add("functional");
	}

	protected void setup() {
		outline = new Rectangle();
		info = new TextWithRectangle();
		info.textProperty().set("");
		outline.visibleProperty().bind(info.textProperty().isNotEmpty());
		info.visibleProperty().bind(outline.visibleProperty());
		outline.getStyleClass().add("outline");
		info.getStyleClass().add("info");
		this.getChildren().add(outline);
		this.getChildren().add(info);

		outline.layoutXProperty().bind(ruleNode.layoutXProperty());
		info.layoutXProperty().bind(ruleNode.layoutXProperty());
//		outline.layoutYProperty().bind(ruleNode.layoutYProperty());
		outline.widthProperty().bind(PropertyMath.max(ruleNode.widthProperty(),info.widthProperty()));
		outline.heightProperty().bind(ruleNode.heightProperty().add(info.heightProperty()));
//		ruleNode.layoutYProperty().bind(info.heightProperty());
		
		ruleNode.boundsInLocalProperty().addListener(this);
		super.setup();
	}
	
	@Override
	protected DoubleExpression generateLayoutYToStubYExpression() {
		return PropertyMath.max(in.layoutToStubYProperty(),out.layoutToStubYProperty(),ruleNode.layoutToStubYProperty()).add(info.heightProperty());
	}

	protected abstract void bindChild(RuleNode<?> node);

	protected abstract void unbindChild(RuleNode<?> node);
	
	public StringProperty infoTextProperty(){
		return info.textProperty();
	}

}
