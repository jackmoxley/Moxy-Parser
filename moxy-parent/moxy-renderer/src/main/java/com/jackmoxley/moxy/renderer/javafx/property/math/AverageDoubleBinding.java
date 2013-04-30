package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.NumberExpression;

/**
 * Given a set of NumberExpressions the binding returns the average of those expressions.
 * Where the result = SUM(expressions)/expressions.size
 * @author jackmoxley
 *
 */
public class AverageDoubleBinding extends RangedDoubleBinding {
	
	
	/**
	 * @param expressions the expressions we want the average of
	 */
	public AverageDoubleBinding(Collection<NumberExpression> expressions) {
		super(expressions);
	}

	/**
	 * @param expressions the expressions we want the average of.
	 */
	public AverageDoubleBinding(NumberExpression... expressions) {
		super(expressions);
	}

	@Override
	protected double calculate() {
		double value = 0;
		for(NumberExpression expression: expressions){
			value += expression.getValue().doubleValue();
		}
		return value / expressions.size();
	}

}
