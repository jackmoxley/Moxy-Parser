package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;

public class OptionalNode extends SingleFunctionalNode<OptionalRule> {

	public OptionalNode(OptionalRule rule,ParentNode parent) {
		super(rule,parent);

	}

	@Override
	protected void bindChild(RuleNode<?> node) {
		DoubleExpression maxWidth = node.widthProperty();
		DoubleBinding fullCurve = gapProperty().divide(2);

		node.layoutXProperty().bind(gapProperty());
		node.stubYProperty().bind(ruleNode.layoutToStubYProperty());


		Path path1 = getPath(0);
		if(path1 == null){
			path1 = new Path();
			addPath(path1);
		} else {
			path1.getElements().clear();
		}
		
		Path path2 = getPath(1);
		if(path2 == null){
			path2 = new Path();
			addPath(path2);
		}else {
			path2.getElements().clear();
		}

		Path path3 = getPath(2);
		if(path3 == null){
			path3 = new Path();
			addPath(path3);
		}else {
			path3.getElements().clear();
		}
		// Left


		MoveTo leftMoveTo = new MoveTo();
		leftMoveTo.yProperty().bind(ruleNode.layoutToStubYProperty());
		leftMoveTo.xProperty().set(0);
		leftMoveTo.setAbsolute(true);


		HLineTo leftHorizontal = new HLineTo();
		leftHorizontal.xProperty().bind(node.layoutXProperty());
		leftHorizontal.setAbsolute(true);
		
		path1.getElements().add(leftMoveTo);
		path1.getElements().add(leftHorizontal);

		// Right

		MoveTo rightMoveTo = new MoveTo();
		rightMoveTo.xProperty().bind(node.endXProperty());
		rightMoveTo.yProperty().bind(ruleNode.layoutToStubYProperty());
		rightMoveTo.setAbsolute(true);


		HLineTo rightHorizontal = new HLineTo();
		rightHorizontal.xProperty().bind(gapProperty());
		rightHorizontal.setAbsolute(false);

		path2.getElements().add(rightMoveTo);
		path2.getElements().add(rightHorizontal);

		// BYPASS

		DoubleBinding curveNegated = fullCurve.negate();

		// Left

		leftMoveTo = new MoveTo();
		leftMoveTo.yProperty().bind(ruleNode.layoutToStubYProperty());
		leftMoveTo.xProperty().set(0);
		leftMoveTo.setAbsolute(true);

		ArcTo bypassArcSourceLeft = new ArcTo();
		bypassArcSourceLeft.radiusXProperty().bind(fullCurve);
		bypassArcSourceLeft.radiusYProperty().bind(fullCurve);
		bypassArcSourceLeft.xProperty().bind(fullCurve);
		bypassArcSourceLeft.yProperty().bind(fullCurve);
		bypassArcSourceLeft.setSweepFlag(true);
		bypassArcSourceLeft.setLargeArcFlag(false);
		bypassArcSourceLeft.setAbsolute(false);

		VLineTo bypassLeftVertical = new VLineTo();
		bypassLeftVertical.yProperty().bind(node.heightProperty().add(gapProperty()).subtract(fullCurve));
		bypassLeftVertical.setAbsolute(true);

		ArcTo bypassArcDestinationLeft = new ArcTo();
		bypassArcDestinationLeft.radiusXProperty().bind(fullCurve);
		bypassArcDestinationLeft.radiusYProperty().bind(fullCurve);
		bypassArcDestinationLeft.xProperty().bind(fullCurve);
		bypassArcDestinationLeft.yProperty().bind(fullCurve);
		bypassArcDestinationLeft.setSweepFlag(false);
		bypassArcDestinationLeft.setLargeArcFlag(false);
		bypassArcDestinationLeft.setAbsolute(false);


		// Line

		HLineTo bypassHorizontal = new HLineTo();
		bypassHorizontal.xProperty().bind(maxWidth.add(gapProperty()));
		bypassHorizontal.setAbsolute(true);

		// Right

		ArcTo bypassArcSourceRight = new ArcTo();
		bypassArcSourceRight.radiusXProperty().bind(fullCurve);
		bypassArcSourceRight.radiusYProperty().bind(fullCurve);
		bypassArcSourceRight.xProperty().bind(fullCurve);
		bypassArcSourceRight.yProperty().bind(curveNegated);
		bypassArcSourceRight.setSweepFlag(false);
		bypassArcSourceRight.setLargeArcFlag(false);
		bypassArcSourceRight.setAbsolute(false);

		VLineTo bypassRightVertical = new VLineTo();
		bypassRightVertical.yProperty().bind(ruleNode.layoutToStubYProperty().add(fullCurve));
		bypassRightVertical.setAbsolute(true);

		ArcTo bypassArcDestinationRight = new ArcTo();
		bypassArcDestinationRight.radiusXProperty().bind(fullCurve);
		bypassArcDestinationRight.radiusYProperty().bind(fullCurve);
		bypassArcDestinationRight.xProperty().bind(fullCurve);
		bypassArcDestinationRight.yProperty().bind(curveNegated);
		bypassArcDestinationRight.setSweepFlag(true);
		bypassArcDestinationRight.setLargeArcFlag(false);
		bypassArcDestinationRight.setAbsolute(false);

		path3.getElements().add(leftMoveTo);
		path3.getElements().add(bypassArcSourceLeft);
		path3.getElements().add(bypassLeftVertical);
		path3.getElements().add(bypassArcDestinationLeft);
		path3.getElements().add(bypassHorizontal);
		path3.getElements().add(bypassArcSourceRight);
		path3.getElements().add(bypassRightVertical);
		path3.getElements().add(bypassArcDestinationRight);
	}



	@Override
	protected void unbindChild(RuleNode<?> node) {
		// TODO Auto-generated method stub
		
	}
	
	
}
