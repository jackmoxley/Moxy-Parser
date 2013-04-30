package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.NumberExpression;

/**
 * Given a set of NumberExpressions the binding returns the smallest of those expressions.
 * Where the result is the minimum value of any one of those expressions
 * @author jackmoxley
 *
 */
public class MinimumDoubleBinding extends RangedDoubleBinding {

	/**
	 * @param expressions the expressions we want the smallest of
	 */
	public MinimumDoubleBinding(Collection<NumberExpression> expressions) {
		super(expressions);
	}

	/**
	 * @param expressions the expressions we want to find the smallest of
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
