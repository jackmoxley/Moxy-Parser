package com.jackmoxley.moxy.renderer.javafx.property;

import java.lang.ref.WeakReference;

import javafx.beans.WeakListener;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TransparentBinding implements ChangeListener<Number>, WeakListener {

	private final WeakReference<DoubleProperty> propertyRef1;
	private final WeakReference<DoubleExpression> propertyRef2;

	private boolean updating = false;
	private final int cachedHashCode;

	private TransparentBinding(DoubleProperty property1,
			DoubleExpression property2) {
		cachedHashCode = property1.hashCode() * property2.hashCode();
		propertyRef1 = new WeakReference<DoubleProperty>(property1);
		propertyRef2 = new WeakReference<DoubleExpression>(property2);
	}

	private static void checkParameters(Object property1, Object property2) {
		if ((property1 == null) || (property2 == null)) {
			throw new NullPointerException("All properties must be specified.");
		}
		if (property1.equals(property2)) {
			throw new IllegalArgumentException("Cannot bind property to itself");
		}
	}

	public static TransparentBinding bind(DoubleProperty property1,
			DoubleExpression property2) {
		checkParameters(property1, property2);
		final TransparentBinding binding = new TransparentBinding(property1,
				property2);
		property1.setValue(property2.getValue());
		property2.addListener(binding);
		return binding;
	}

	protected Property<Number> getProperty1() {
		return propertyRef1.get();
	}

	protected DoubleExpression getProperty2() {
		return propertyRef2.get();
	}

	@Override
	public void changed(ObservableValue<? extends Number> sourceProperty,
			Number oldValue, Number newValue) {
		if (!updating) {
			final DoubleProperty property1 = propertyRef1.get();
			final DoubleExpression property2 = propertyRef2.get();
			if ((property1 == null) || (property2 == null)) {
				if (property2 != null) {
					property2.removeListener(this);
				}
			} else {
				try {
					updating = true;
					property1.setValue(property2.getValue());
				} catch (RuntimeException e) {
					property1.setValue(oldValue.doubleValue());
					throw new RuntimeException(
							"TransparentBinding binding failed, setting to the previous value",
							e);
				} finally {
					updating = false;
				}
			}
		}
	}

	@Override
	public int hashCode() {
		return cachedHashCode;
	}

	@Override
	public boolean wasGarbageCollected() {
		return (getProperty1() == null) || (getProperty2() == null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		final Object propertyA1 = getProperty1();
		final Object propertyA2 = getProperty2();
		if ((propertyA1 == null) || (propertyA2 == null)) {
			return false;
		}

		if (obj instanceof TransparentBinding) {
			final TransparentBinding otherBinding = (TransparentBinding) obj;
			final Object propertyB1 = otherBinding.getProperty1();
			final Object propertyB2 = otherBinding.getProperty2();
			if ((propertyB1 == null) || (propertyB2 == null)) {
				return false;
			}
			if ((propertyA1.equals(propertyB1) && (propertyA2
					.equals(propertyB2)))) {
				return true;
			}
			if ((propertyA1.equals(propertyB2) && (propertyA2
					.equals(propertyB1)))) {
				return true;
			}
		}
		return false;
	}

}
