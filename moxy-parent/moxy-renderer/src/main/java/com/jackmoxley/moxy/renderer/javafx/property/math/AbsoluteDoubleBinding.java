package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;

/**
 * Provides the absoloute value of the associated number expression. For
 * instance if the captured expression is -3.4 this binding returns 3.4, If the
 * captured expression is 5.6 it returns 5.6
 * 
 * @author jack
 * 
 */
public class AbsoluteDoubleBinding extends DoubleBinding {

	private final NumberExpression expression;


	/**
	 * @param expression the expression we wish to get the absoloute value of
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
