package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.renderer.node.StyleManager;


public class Box extends Component {

	public Box(Spatial parent) {
		super(parent);
		size(new Vertex(10,10));
	}

	@Override
	public void draw(Graphics2D g) {
		Style style = StyleManager.currentStyle();
		g.setColor(style.shadowStyle.color);
		RoundRectangle2D rect = roundRect(style.lineStyle.cornerRadius);
		translateWorld(g, style.shadowStyle.shadowOffset);
		g.fill(rect);
		reverseTranslateWorld(g, style.shadowStyle.shadowOffset);
		
		g.setColor(style.polygonStyle.color);
		g.fill(rect);
		g.setColor(style.polygonStyle.lineStyle.color);
		g.draw(rect);
//		System.out.println(this +" "+ rect.getBounds());
	}

	
}
