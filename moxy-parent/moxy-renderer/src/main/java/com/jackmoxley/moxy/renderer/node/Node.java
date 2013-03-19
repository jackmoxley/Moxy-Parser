package com.jackmoxley.moxy.renderer.node;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.renderable.Component;
import com.jackmoxley.moxy.renderer.common.renderable.Line;
import com.jackmoxley.moxy.renderer.common.renderable.Renderable;
import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.renderable.TextGroup;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.rule.Rule;

public abstract class Node<R extends Rule> extends Component {

	protected final R rule;
	private double yOffset = 0;
	private double xOffset = 0;
	private List<Component> children = new ArrayList<Component>();
	
	Line in = new Line(this, new Vertex(0, 0), new Vertex(0, 0));
	Line out = new Line(this, new Vertex(0, 0), new Vertex(0, 0));
	TextGroup inText = new TextGroup(this, "In");
	TextGroup outText = new TextGroup(this, "Out");


	/**
	 * @param spatials
	 * @param rule
	 */
	public Node(Spatial parent, R rule) {
		super(parent);
		this.rule = rule;
		
	}

	public R getRule() {
		return rule;
	}
	
	
	protected abstract void prepareNode(Graphics2D g);

	public final void prepare(Graphics2D g) {
		prepareNode(g);
		final Style style = getStyle();
		final double margin = style.margin;
		inText.setText(getInText());
		inText.prepare(g);
		outText.setText(getOutText());
		outText.prepare(g);
		xOffset = Math.max(margin, inText.size().x);
		double outMargin = Math.max(margin, outText.size().x);

		in.localTranslation(Vertex.ZERO);
		out.localTranslation(new Vertex(xOffset+size().x,0));
		in.prepare(g);
		out.prepare(g);
		inText.prepare(g);
		outText.prepare(g);
		
		yOffset = size().y/2;

		in.set(1, in.get(1).setX(xOffset));
		out.set(1, out.get(1).setX(outMargin));
		inText.localTranslation(in.localTranslation().subY(inText.size().y));
		outText.localTranslation(out.localTranslation().subY(outText.size().y));
		
		sizeFromSpatials(children);
//		System.out.println("Node size"+size());
		
		size(size().addX(xOffset+outMargin));
//		System.out.println("Node size"+size());
	}

	protected void draw(Graphics2D g) {
//		g.setColor(Color.magenta);
//		g.drawRect(0, (int)-yOffset, (int)size().x, (int)size().y);
		Vertex offset = new Vertex(xOffset,-yOffset);
		translateWorld(g, offset);
		for(Renderable renderable: children){
			renderable.render(g);
		}
		reverseTranslateWorld(g, offset);
		in.render(g);
		out.render(g);
		inText.render(g);
		outText.render(g);
	}

	protected Style getStyle() {
		return StyleManager.getInstance().getStyle(this, rule);
	}

	protected String getInText() {
		return inText.getText();
	}

	protected String getOutText() {
		return outText.getText();
	}

	protected void setInText(String inText) {
		this.inText.setText(inText);
	}

	protected void setOutText(String outText) {
		this.outText.setText(outText);
	}
	
	protected void add(Component component){
		children.add(component);
	}

	protected void remove(Component component){
		children.remove(component);
	}

	protected double yOffset() {
		return yOffset;
	}

	protected double xOffset() {
		return xOffset;
	}
	
	
}
