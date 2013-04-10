package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;

public class AbsoluteDoubleBinding extends DoubleBinding {

	private final NumberExpression expression;
	
	/**
	 * @param expression
	 */
	public AbsoluteDoubleBinding(NumberExpression expression) {
		super();
		this.expression = expression;
		this.bind(expression);
	}

	@Override
	protected double computeValue() {
		return Math.abs(expression.doubleValue());
	}
}
