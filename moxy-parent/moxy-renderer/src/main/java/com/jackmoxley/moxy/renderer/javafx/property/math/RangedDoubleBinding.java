package com.jackmoxley.moxy.renderer.javafx.property.math;

import java.util.Collection;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection;

/**
 * The base class for any mathematical operation that returns a double based on
 * a set of number expressions.
 * 
 * @author jackmoxley
 * 
 */
public abstract class RangedDoubleBinding extends DoubleBinding {

	protected final ObservableList<NumberExpression> expressions;

	/**
	 * @param expressions
	 *            the expressions we wish to perform an operation against
	 */
	public RangedDoubleBinding(Collection<NumberExpression> expressions) {
		super();
		this.expressions = FXCollections.observableArrayList(expressions);

		this.bind(expressions.toArray(new NumberExpression[expressions.size()]));
	}
	
	/**
	 * @param expressions
	 *            the expressions we wish to perform an operation against
	 */
	public RangedDoubleBinding(NumberExpression... expressions) {
		super();
		this.expressions = FXCollections.observableArrayList(expressions);

		this.bind(expressions);
	}

	protected abstract double calculate();

	@Override
	protected double computeValue() {
		if (expressions.isEmpty()) {
			return 0;
		}
		if (expressions.size() == 1) {
			return expressions.get(0).doubleValue();
		}
		return calculate();
	}

	/**
	 * {inheritDoc}
	 */
	@Override
	@ReturnsUnmodifiableCollection
	public ObservableList<?> getDependencies() {
		return FXCollections.unmodifiableObservableList(expressions);
	}

	/**
	 * Add an expression to be considered for calculation
	 * @param expression
	 */
	public void addExpression(NumberExpression expression) {
		this.bind(expression);
		expressions.add(expression);
	}

	/**
	 * Remove an expression to be considered for calculation
	 * @param expression
	 */
	public void removeExpression(NumberExpression expression) {
		this.unbind(expression);
		expressions.remove(expression);
	}

}
