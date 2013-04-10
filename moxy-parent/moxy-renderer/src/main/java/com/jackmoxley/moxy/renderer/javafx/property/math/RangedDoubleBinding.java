package com.jackmoxley.moxy.renderer.javafx.property.math;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection;

public abstract class RangedDoubleBinding extends DoubleBinding {

	protected final ObservableList<NumberExpression> expressions;

	public RangedDoubleBinding(NumberExpression... expressions) {
		super();
		this.expressions = FXCollections.observableArrayList(expressions);
		
		this.bind(expressions);
	}

    @Override
    @ReturnsUnmodifiableCollection
    public ObservableList<?> getDependencies() {
        return FXCollections.unmodifiableObservableList(expressions);
    }

	public void addExpression(NumberExpression expression) {
		this.bind(expression);
		expressions.add(expression);
	}

	public void removeExpression(NumberExpression expression) {
		this.unbind(expression);
		expressions.remove(expression);
	}

}
