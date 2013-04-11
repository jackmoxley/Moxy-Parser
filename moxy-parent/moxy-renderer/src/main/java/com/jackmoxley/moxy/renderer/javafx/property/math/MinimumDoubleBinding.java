package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.NumberExpression;

public class MinimumDoubleBinding extends RangedDoubleBinding {

	/**
	 * @param value1
	 * @param value2
	 */
	public MinimumDoubleBinding(NumberExpression... expressions) {
		super(expressions);
	}

	@Override
	protected double calculate() {
		double value = Double.MAX_VALUE;
		for (NumberExpression expression : expressions) {
			value = Math.min(value, expression.getValue().doubleValue());
		}
		return value;
	}

}
