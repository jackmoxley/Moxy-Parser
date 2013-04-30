package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.NumberExpression;

/**
 * Given a set of NumberExpressions the binding returns the largest of those expressions.
 * Where the result is the maximum value of any one of those expressions
 * @author jackmoxley
 *
 */
public class MaximumDoubleBinding extends RangedDoubleBinding {
	

	/**
	 * @param expressions the expressions we want the largest of
	 */
	public MaximumDoubleBinding(Collection<NumberExpression> expressions) {
		super(expressions);
	}

	/**
	 * @param expressions the expressions we want to find the largest of
	 */
	public MaximumDoubleBinding(NumberExpression... expressions) {
		super(expressions);
	}

	@Override
	protected double calculate() {
		double value = Double.MIN_VALUE;
		for(NumberExpression expression: expressions){
			double expressionValue = expression.doubleValue();
			value = Math.max(value, expressionValue);
		}
		return value;
	}

}
