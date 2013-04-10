package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.NumberExpression;

public class AverageDoubleBinding extends RangedDoubleBinding {
	
	
	/**
	 * @param value1
	 * @param value2
	 */
	public AverageDoubleBinding(NumberExpression... expressions) {
		super(expressions);
	}

	@Override
	protected double computeValue() {
		double value = 0;
		for(NumberExpression expression: expressions){
			value += expression.getValue().doubleValue();
		}
		return value / expressions.size();
	}

}
