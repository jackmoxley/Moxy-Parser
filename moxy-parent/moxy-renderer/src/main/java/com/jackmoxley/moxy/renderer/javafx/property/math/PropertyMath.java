package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.NumberExpression;

public class PropertyMath {

	public static AbsoluteDoubleBinding abs(NumberExpression expression){
		return new AbsoluteDoubleBinding(expression);
	}
	

	public static AverageDoubleBinding avg(NumberExpression... expressions){
		return new AverageDoubleBinding(expressions);
	}

	public static MaximumDoubleBinding max(NumberExpression... expressions){
		return new MaximumDoubleBinding(expressions);
	}

	public static MinimumDoubleBinding min(NumberExpression... expressions){
		return new MinimumDoubleBinding(expressions);
	}


	public static SumDoubleBinding sum(NumberExpression... expressions){
		return new SumDoubleBinding(expressions);
	}
}
