package com.jackmoxley.moxy.renderer.swing;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Dimensions {

	public final Point2D size;
	public final Rectangle2D bounds;

	public final Point2D stubLeft;
	public final Point2D stubRight;
	
	public final double localStubY;

	public Dimensions(Point2D size, Rectangle2D bounds, Point2D stubLeft,
			Point2D stubRight, double localStubY) {
		super();
		this.size = size;
		this.bounds = bounds;
		this.stubLeft = stubLeft;
		this.stubRight = stubRight;
		this.localStubY = localStubY;
	}

}
