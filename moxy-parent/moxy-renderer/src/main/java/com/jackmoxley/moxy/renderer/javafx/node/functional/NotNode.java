package com.jackmoxley.moxy.renderer.javafx.node.functional;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.functional.single.NotRule;

public class NotNode extends SingleFunctionalNode<NotRule> {

	
	public NotNode(NotRule rule,ParentNode parent, RuleGraphNode graph) {
		super(rule,parent,graph);

	}
	
	

	@Override
	protected void bindChild(RuleNode<?> node) {
		node.stubYProperty().bind(ruleNode.layoutToStubYProperty());
		node.layoutXProperty().set(0);
		Path path1 = getPath(0);
		if(path1 == null){
			path1 = new Path();
			addPath(path1);
		} else {
			path1.getElements().clear();
		}

		MoveTo leftMoveTo = new MoveTo();
		leftMoveTo.xProperty().bind(node.layoutXProperty().add(node.getRuleNode().layoutXProperty()));
		leftMoveTo.yProperty().bind(node.layoutYProperty().add(node.getRuleNode().layoutYProperty()));
		leftMoveTo.setAbsolute(true);


		LineTo leftLine = new LineTo();
		leftLine.xProperty().bind(node.getRuleNode().widthProperty());
		leftLine.yProperty().bind(node.getRuleNode().heightProperty());
		leftLine.setAbsolute(false);
		
		path1.getElements().add(leftMoveTo);
		path1.getElements().add(leftLine);
		
		Path path2 = getPath(1);
		if(path2 == null){
			path2 = new Path();
			addPath(path2);
		}else {
			path2.getElements().clear();
		}


		MoveTo rightMoveTo = new MoveTo();
		rightMoveTo.xProperty().bind(node.layoutXProperty().add(node.getRuleNode().layoutXProperty()));
		rightMoveTo.yProperty().bind(node.layoutYProperty().add(node.getRuleNode().layoutYProperty()).add(node.getRuleNode().heightProperty()));
		rightMoveTo.setAbsolute(true);


		LineTo rightLine = new LineTo();
		rightLine.xProperty().bind(node.getRuleNode().widthProperty());
		rightLine.yProperty().bind(node.getRuleNode().heightProperty().negate());
		rightLine.setAbsolute(false);
		
		path2.getElements().add(rightMoveTo);
		path2.getElements().add(rightLine);
	}



	@Override
	protected void unbindChild(RuleNode<?> node) {
		// TODO Auto-generated method stub
		
	}
	
	
}
