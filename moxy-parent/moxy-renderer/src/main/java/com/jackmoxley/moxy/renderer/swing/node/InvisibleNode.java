package com.jackmoxley.moxy.renderer.swing.node;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jackmoxley.moxy.renderer.swing.Dimensions;
import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.rule.Rule;


public class InvisibleNode extends Node<Rule> {

	public InvisibleNode(Rule rule, Node<Rule> parent) {
		super(rule, parent);
	}

	@Override
	public Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft) {

		Point2D size =  new Point2D.Double(0,0);

		Point2D stubRight = new Point2D.Double(0, 0);
		Rectangle2D bounds = new Rectangle2D.Double(0, 0, 0, 0); 
		
		return new Dimensions(size,bounds,stubLeft,stubRight,0);
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}



}
