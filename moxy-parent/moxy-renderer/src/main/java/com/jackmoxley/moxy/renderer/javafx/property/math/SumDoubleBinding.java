package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.NumberExpression;

/**
 * Given a set of NumberExpressions the binding returns the sum of those expressions.
 * Where the result = SUM(expressions)
 * @author jackmoxley
 *
 */
public class SumDoubleBinding extends RangedDoubleBinding {

	/**
	 * @param expressions the expressions we want the total of
	 */
	public SumDoubleBinding(Collection<NumberExpression> expressions) {
		super(expressions);
	}

	/**
	 * @param expressions the expressions we want the total of
	 */
	public SumDoubleBinding(NumberExpression... expressions) {
		super(expressions);
	}

	@Override
	protected double calculate() {
		double value = 0;
		for(NumberExpression expression: expressions){
			value += expression.getValue().doubleValue();
		}
		return value;
	}

}
