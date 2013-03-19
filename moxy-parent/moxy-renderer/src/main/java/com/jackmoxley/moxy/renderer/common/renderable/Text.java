package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.renderer.node.StyleManager;

public class Text extends Component {

	private String text = "";

	public Text(Spatial spatial, String text) {
		super(spatial);
		this.text = text;
	}

	@Override
	public void prepare(Graphics2D g) {
		Style style = StyleManager.currentStyle();
		g.setFont(style.textStyle.font);
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(text, g);
//		double w = textBounds.getWidth() + (style.textStyle.margin * 2);
//		double h = textBounds.getHeight() + (style.textStyle.margin * 2);
		size(new Vertex(textBounds.getWidth(), textBounds.getHeight()));
	}

	@Override
	public void draw(Graphics2D g) {
		Style style = StyleManager.currentStyle();
		g.setFont(style.textStyle.font);
		g.setColor(style.textStyle.color);
//		g.drawString(text, (int)style.textStyle.margin, (int)(size().y-style.textStyle.margin));
		g.drawString(text,0, (int)size().y);
//		g.drawRect((int)0, (int)0, (int)size().x, (int)size().y);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
