package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.NumberExpression;

/**
 * A helper class to enable us to easily generate bindings for mathematical
 * property bindings.
 * 
 * @author jackmoxley
 * 
 */
public class PropertyMath {

	public static AbsoluteDoubleBinding abs(NumberExpression expression) {
		return new AbsoluteDoubleBinding(expression);
	}

	public static AverageDoubleBinding avg(NumberExpression... expressions) {
		return new AverageDoubleBinding(expressions);
	}

	public static AverageDoubleBinding avg(
			Collection<NumberExpression> expressions) {
		return new AverageDoubleBinding(expressions);
	}

	public static MaximumDoubleBinding max(NumberExpression... expressions) {
		return new MaximumDoubleBinding(expressions);
	}

	public static MaximumDoubleBinding max(
			Collection<NumberExpression> expressions) {
		return new MaximumDoubleBinding(expressions);
	}

	public static MinimumDoubleBinding min(NumberExpression... expressions) {
		return new MinimumDoubleBinding(expressions);
	}

	public static MinimumDoubleBinding min(
			Collection<NumberExpression> expressions) {
		return new MinimumDoubleBinding(expressions);
	}

	public static SumDoubleBinding sum(NumberExpression... expressions) {
		return new SumDoubleBinding(expressions);
	}

	public static SumDoubleBinding sum(Collection<NumberExpression> expressions) {
		return new SumDoubleBinding(expressions);
	}
}
