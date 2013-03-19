package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class Component extends Spatial implements Renderable {
	
	
	public Component(Spatial parent) {
		super(parent);
	}

	@Override
	public void prepare(Graphics2D g) {
		// Do Nothing
	}

	protected void draw(Graphics2D g) {	
	}
	
	@Override
	public final void render(Graphics2D g) {
		translateWorld(g,localTranslation());
		draw(g);
		reverseTranslateWorld(g,localTranslation());
	}
	

	public RoundRectangle2D roundRect( Vertex cornerRadius) {
		return new RoundRectangle2D.Double(0, 0,
				size().x, size().y, cornerRadius.x, cornerRadius.y);
	}

	public Rectangle2D rect() {
		return new Rectangle2D.Double(0, 0, size().x,
				size().y);
	}
	
	protected static void translateWorld(Graphics2D g, Vertex v){
		g.translate(v.x, v.y);
		
	}

	protected static void reverseTranslateWorld(Graphics2D g, Vertex v){
		g.translate(-v.x, -v.y);
	}
	
	public void sizeFromVertices(Iterable<Vertex> vertices) {

		double x = Double.MIN_VALUE;
		double y = Double.MIN_VALUE;

		for (Vertex vertex : vertices) {
			x = Math.max(vertex.x, x);
			y = Math.max(vertex.y, y);
		}
		size(new Vertex(x,y));
	}

//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append(getClass().getSimpleName()).append(" [size=").append(size())
//				.append(", localTranslation=").append(localTranslation())
//				.append(", worldTranslation=").append(worldTranslation())
//				.append("]");
//		return builder.toString();
//	}
	
}
