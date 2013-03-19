package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;

public interface Renderable {

	void prepare(Graphics2D g);
	
	void render(Graphics2D g);
}
