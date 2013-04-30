package com.jackmoxley.moxy.renderer.javafx.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.scene.shape.Polygon;

/**
 * In an equilateral triangle if we know the vector translation between the
 * middle of the bottom and the top point, we can determine the left and right
 * points without the use of squares, cosines or root functions.
 * 
 * Don't you love math!
 * 
 * @author jack
 * 
 */
public class EquilateralTriangle extends Polygon {

	private DoubleProperty topX;
	private DoubleProperty topY;

	public EquilateralTriangle() {
		super(0, 0, 0, 0, 0, 0);
	}

	public final DoubleProperty topXProperty() {
		if (topX == null) {
			topX = new DoublePropertyBase(0.0) {

				@Override
				protected void invalidated() {
					double x = doubleValue();
					double hx = x / 2;
					EquilateralTriangle.this.getPoints().set(0, x);
					EquilateralTriangle.this.getPoints().set(3, -hx);
					EquilateralTriangle.this.getPoints().set(5, hx);
				}

				@Override
				public Object getBean() {
					return EquilateralTriangle.this;
				}

				@Override
				public String getName() {
					return "topX";
				}
			};
		}
		return topX;
	}

	public final DoubleProperty topYProperty() {
		if (topY == null) {
			topY = new DoublePropertyBase(0.0) {

				@Override
				protected void invalidated() {
					double y = doubleValue();
					double hy = y / 2;
					EquilateralTriangle.this.getPoints().set(1, y);
					EquilateralTriangle.this.getPoints().set(2, hy);
					EquilateralTriangle.this.getPoints().set(4, -hy);
				}

				@Override
				public Object getBean() {
					return EquilateralTriangle.this;
				}

				@Override
				public String getName() {
					return "topY";
				}
			};
		}
		return topY;
	}
}
