package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

import com.jackmoxley.moxy.renderer.javafx.component.EquilateralTriangle;
import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.functional.single.MinMaxRule;

public class MinMaxNode<R extends MinMaxRule> extends SingleFunctionalNode<R> {


	public EquilateralTriangle arrow = new EquilateralTriangle();
	public MinMaxNode(R rule,ParentNode parent, RuleGraphNode graph) {
		super(rule,parent,graph);
		this.ruleNode.getChildren().add(arrow);
		
		this.infoTextProperty().setValue(getInfoString());
	}
	
	public String getInfoString(){
		int min = rule.getMin();
		int max = rule.getMax();
		if(min == max){
			if(min < 0){
				return "N";
			} else {
				return Integer.toString(min);
			}
		}
		StringBuilder sb = new StringBuilder();
		if(min < 0){
			min = 0;
		}
		sb.append(min);
		sb.append(" ... ");
		if(max < 0){
			sb.append("*");
		} else {
			sb.append(max);
		}
		return sb.toString();
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
		leftMoveTo.xProperty().bind(fullCurve.multiply(2));
		leftMoveTo.setAbsolute(true);

		ArcTo bypassArcSourceLeft = new ArcTo();
		bypassArcSourceLeft.radiusXProperty().bind(fullCurve);
		bypassArcSourceLeft.radiusYProperty().bind(fullCurve);
		bypassArcSourceLeft.xProperty().bind(curveNegated);
		bypassArcSourceLeft.yProperty().bind(fullCurve);
		bypassArcSourceLeft.setSweepFlag(false);
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


		arrow.layoutXProperty().bind((node.endXProperty().subtract(node.layoutXProperty()).add(arrow.topXProperty().negate())).divide(2).add(node.layoutXProperty()));
		arrow.layoutYProperty().bind(node.heightProperty().add(gapProperty()));
		arrow.topXProperty().set(-7);
		arrow.topYProperty().set(0);
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
		bypassArcDestinationRight.xProperty().bind(curveNegated);
		bypassArcDestinationRight.yProperty().bind(curveNegated);
		bypassArcDestinationRight.setSweepFlag(false);
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
