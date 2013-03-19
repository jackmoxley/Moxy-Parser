package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.renderer.node.StyleManager;

public class TextGroup extends Component {

	private String text;
	private Text textComponents[];

	public TextGroup(Spatial spatial, String text) {
		super(spatial);
		setText(text);
	}

	@Override
	public void prepare(Graphics2D g) {

		Style style = StyleManager.currentStyle();
		final double margin = style.textStyle.margin;
		double w = 0;
		double h = margin;
		for (Text text : this.textComponents) {
			text.prepare(g);
			text.localTranslation(new Vertex(margin, h));
			w = Math.max(w, text.size().x);
			h += text.size().y;
		}
//		double w = textBounds.getWidth() + (style.textStyle.margin * 2);
//		double h = textBounds.getHeight() + (style.textStyle.margin * 2);
		size(new Vertex(w+(margin*2), h+margin));
	}

	@Override
	public void draw(Graphics2D g) {
		for (Text text : this.textComponents) {
			text.render(g);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		String[] textParts = text.split("\n");
		this.textComponents = new Text[textParts.length];
		for (int i = 0; i < textParts.length; i++) {
			this.textComponents[i] = new Text(this, textParts[i]);
		}
	}

}
