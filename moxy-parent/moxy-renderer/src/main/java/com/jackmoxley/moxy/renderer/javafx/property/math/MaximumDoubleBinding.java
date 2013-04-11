package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.NumberExpression;

public class MaximumDoubleBinding extends RangedDoubleBinding {
	
	/**
	 * @param value1
	 * @param value2
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
//		System.out.println("MaximumDoubleBinding:"+value);
		return value;
	}

}
