package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.TextAlignment;

import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.renderer.javafx.property.math.MaximumDoubleBinding;
import com.jackmoxley.moxy.renderer.javafx.property.math.MinimumDoubleBinding;
import com.jackmoxley.moxy.renderer.javafx.property.math.PropertyMath;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;

public class LogicalListNode extends ListFunctionalNode<LogicalListRule> {

	
	protected MaximumDoubleBinding maxWidth;
	protected DoubleBinding halfWidth;
	protected DoubleBinding fullCurve;
	protected ObjectProperty<TextAlignment> alignmentProperty = new SimpleObjectProperty<TextAlignment>(TextAlignment.LEFT);

	public LogicalListNode(LogicalListRule rule,ParentNode parent) {
		super(rule,parent);
		
	}

	@Override
	public void constructNodes(Scene scene) {
		maxWidth = PropertyMath.max();
		halfWidth = maxWidth.divide(2).add(gapProperty());
		fullCurve = gapProperty().divide(2);
		super.constructNodes(scene);

	}

	@Override
	protected void onMouseClicked(MouseEvent event) {
		switch(alignmentProperty.get()){
		case LEFT:
			alignmentProperty.set(TextAlignment.CENTER);
			break;
		case CENTER:
			alignmentProperty.set(TextAlignment.RIGHT);
			break;
		case RIGHT:
			default:
			alignmentProperty.set(TextAlignment.LEFT);
			break;
		}
		System.out.println("LogicalList Size "+rule.size());
		super.onMouseClicked(event);
	}

	@Override
	protected void bindChildren(RuleNode<?> first, RuleNode<?> second) {
		if(first == null){
			second.layoutYProperty().set(0);
		} else if(second != null){
			second.layoutYProperty().bind(first.endYProperty().add(gapProperty()));
		}
	}

	@Override
	protected void unbindChildren(RuleNode<?> first, RuleNode<?> second) {
		if(second != null){
			second.layoutYProperty().unbind();
		}
	}

	@Override
	protected void bindChild(RuleNode<?> node) {
		if(rule.size()==1){
			System.out.println("Do the singular");
		}
		maxWidth.addExpression(node.widthProperty());

		NumberBinding x = new When(alignmentProperty.isEqualTo(TextAlignment.LEFT))
			.then(gapProperty())
		.otherwise(new When(alignmentProperty.isEqualTo(TextAlignment.RIGHT))
			.then(maxWidth.add(gapProperty()).subtract(node.widthProperty()))
		.otherwise(halfWidth.subtract(node.widthProperty().divide(2))));
		
		node.layoutXProperty().bind(x);
		DoubleBinding y = node.stubYProperty().subtract(
				ruleNode.layoutToStubYProperty());
		DoubleBinding direction = PropertyMath.abs(y).divide(y);
		MinimumDoubleBinding curve = PropertyMath.min(fullCurve,
				PropertyMath.abs(y.divide(2)));
		DoubleBinding curveWithDirection = curve.multiply(direction);
		DoubleBinding curveWithDirectionNegated = curve.multiply(direction)
				.negate();

		Path path1 = getPath(node, 0);
		if (path1 == null) {
			path1 = new Path();
			addPath(path1, node);
		} else {
			path1.getElements().clear();
		}
		
		MoveTo moveLeft = new MoveTo();
		ArcTo arcSourceLeft = new ArcTo();
		VLineTo leftVertical = new VLineTo();
		ArcTo arcDestinationLeft = new ArcTo();
		HLineTo leftHorizontal = new HLineTo();

		path1.getElements().add(moveLeft);
		path1.getElements().add(arcSourceLeft);
		path1.getElements().add(leftVertical);
		path1.getElements().add(arcDestinationLeft);
		path1.getElements().add(leftHorizontal);

		moveLeft.yProperty().bind(ruleNode.layoutToStubYProperty());

		arcSourceLeft.radiusXProperty().bind(fullCurve);
		arcSourceLeft.radiusYProperty().bind(curve);
		arcSourceLeft.xProperty().bind(fullCurve);
		arcSourceLeft.yProperty().bind(curveWithDirection);
		arcSourceLeft.sweepFlagProperty().bind(
				direction.greaterThanOrEqualTo(0));
		arcSourceLeft.setLargeArcFlag(false);
		arcSourceLeft.setAbsolute(false);

		leftVertical.yProperty().bind(
				node.stubYProperty().subtract(curveWithDirection));
		leftVertical.setAbsolute(true);

		arcDestinationLeft.radiusXProperty().bind(fullCurve);
		arcDestinationLeft.radiusYProperty().bind(curve);
		arcDestinationLeft.xProperty().bind(fullCurve);
		arcDestinationLeft.yProperty().bind(curveWithDirection);
		arcDestinationLeft.sweepFlagProperty().bind(direction.lessThan(0));
		arcDestinationLeft.setLargeArcFlag(false);
		arcDestinationLeft.setAbsolute(false);

		leftHorizontal.xProperty().bind(node.layoutXProperty());
		leftHorizontal.setAbsolute(true);

		Path path2 = getPath(node, 1);
		if (path2 == null) {
			path2 = new Path();
			addPath(path2, node);
		} else {
			path2.getElements().clear();
		}
		
		MoveTo moveRight = new MoveTo();
		HLineTo rightHorizontal = new HLineTo();
		ArcTo arcSourceRight = new ArcTo();
		VLineTo rightVertical = new VLineTo();
		ArcTo arcDestinationRight = new ArcTo();

		path2.getElements().add(moveRight);
		path2.getElements().add(rightHorizontal);
		path2.getElements().add(arcSourceRight);
		path2.getElements().add(rightVertical);
		path2.getElements().add(arcDestinationRight);

		moveRight.xProperty().bind(node.endXProperty());
		moveRight.yProperty().bind(node.stubYProperty());
		moveRight.setAbsolute(true);

		rightHorizontal.xProperty().bind(maxWidth.add(gapProperty()));
		rightHorizontal.setAbsolute(true);

		arcSourceRight.radiusXProperty().bind(fullCurve);
		arcSourceRight.radiusYProperty().bind(curve);
		arcSourceRight.xProperty().bind(fullCurve);
		arcSourceRight.yProperty().bind(curveWithDirectionNegated);
		arcSourceRight.sweepFlagProperty().bind(direction.lessThan(0));
		arcSourceRight.setLargeArcFlag(false);
		arcSourceRight.setAbsolute(false);

		rightVertical.yProperty().bind(
				ruleNode.layoutToStubYProperty().add(curveWithDirection));
		rightVertical.setAbsolute(true);

		arcDestinationRight.radiusXProperty().bind(fullCurve);
		arcDestinationRight.radiusYProperty().bind(curve);
		arcDestinationRight.xProperty().bind(fullCurve);
		arcDestinationRight.yProperty().bind(curveWithDirectionNegated);
		arcDestinationRight.xProperty().bind(curve);
		arcDestinationRight.sweepFlagProperty().bind(
				direction.greaterThanOrEqualTo(0));
		arcDestinationRight.setLargeArcFlag(false);
		arcDestinationRight.setAbsolute(false);

	}

	@Override
	protected void unbindChild(RuleNode<?> node) {
		maxWidth.removeExpression(node.widthProperty());

		Path path1 = getPath(node, 0);

		MoveTo moveLeft = (MoveTo) path1.getElements().get(0);
		moveLeft.yProperty().unbind();

		ArcTo arcSourceLeft = (ArcTo) path1.getElements().get(1);
		arcSourceLeft.radiusXProperty().unbind();
		arcSourceLeft.radiusYProperty().unbind();
		arcSourceLeft.xProperty().unbind();
		arcSourceLeft.yProperty().unbind();
		arcSourceLeft.sweepFlagProperty().unbind();

		VLineTo leftVertical = (VLineTo) path1.getElements().get(2);
		leftVertical.yProperty().unbind();

		ArcTo arcDestinationLeft = (ArcTo) path1.getElements().get(3);
		arcDestinationLeft.radiusXProperty().unbind();
		arcDestinationLeft.radiusYProperty().unbind();
		arcDestinationLeft.xProperty().unbind();
		arcDestinationLeft.yProperty().unbind();
		arcDestinationLeft.sweepFlagProperty().unbind();

		HLineTo leftHorizontal = (HLineTo) path1.getElements().get(4);
		leftHorizontal.xProperty().unbind();

		removePath(path1, node);

		Path path2 = getPath(node, 1);
		MoveTo moveRight = (MoveTo) path1.getElements().get(0);
		moveRight.xProperty().unbind();
		moveRight.yProperty().unbind();

		HLineTo rightHorizontal = (HLineTo) path1.getElements().get(1);
		rightHorizontal.xProperty().unbind();

		ArcTo arcSourceRight = (ArcTo) path1.getElements().get(2);
		arcSourceRight.radiusXProperty().unbind();
		arcSourceRight.radiusYProperty().unbind();
		arcSourceRight.xProperty().unbind();
		arcSourceRight.yProperty().unbind();
		arcSourceRight.sweepFlagProperty().unbind();

		VLineTo rightVertical = (VLineTo) path1.getElements().get(3);
		rightVertical.yProperty().unbind();

		ArcTo arcDestinationRight = (ArcTo) path1.getElements().get(4);
		arcDestinationRight.radiusXProperty().unbind();
		arcDestinationRight.radiusYProperty().unbind();
		arcDestinationRight.xProperty().unbind();
		arcDestinationRight.yProperty().unbind();
		arcDestinationRight.sweepFlagProperty().unbind();

		removePath(path2, node);
	}

}
