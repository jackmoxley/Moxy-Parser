package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class TextBox extends Component {

	private Box box;
	private TextGroup text;


	public TextBox(Spatial parent, String text) {
		super(parent);
		box = new Box(this);
		this.text = new TextGroup(this, text);
	}

	@Override
	public void prepare(Graphics2D g) {
		text.localTranslation(Vertex.ZERO);
		text.prepare(g);
		box.copyBounds(text);
//		box.prepare(g); // unneeded
		this.copyBounds(text);
	}

	@Override
	public void draw(Graphics2D g) {
		box.render(g);
		text.render(g);
	}

	public String getText() {
		return text.getText();
	}

	public void setText(String text) {
		this.text.setText(text);
	}

//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append(super.toString()).append("[box=").append(box).append(", text=")
//				.append(text).append("]");
//		return builder.toString();
//	}
	
	
}
