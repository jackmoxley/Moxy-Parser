package com.jackmoxley.moxy.renderer.javafx.property;

import java.lang.ref.WeakReference;

import javafx.beans.WeakListener;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BidirectionalDifferenceBinding implements ChangeListener<Number>,
		WeakListener {

	private final WeakReference<DoubleProperty> propertyRef1;
	private final WeakReference<DoubleProperty> propertyRef2;
	private final WeakReference<DoubleExpression> differenceRef;
	private boolean updating = false;
	private final int cachedHashCode;

	private BidirectionalDifferenceBinding(DoubleProperty property1,
			DoubleProperty property2, DoubleExpression difference) {
		cachedHashCode = property1.hashCode() * property2.hashCode()
				* difference.hashCode();
		propertyRef1 = new WeakReference<DoubleProperty>(property1);
		propertyRef2 = new WeakReference<DoubleProperty>(property2);
		differenceRef = new WeakReference<DoubleExpression>(difference);
	}

	private static void checkParameters(Object property1, Object property2,
			Object difference) {
		if ((property1 == null) || (property2 == null) || (difference == null)) {
			throw new NullPointerException("All properties must be specified.");
		}
		if (property1.equals(property2) || property1.equals(difference)
				|| property2.equals(difference)) {
			throw new IllegalArgumentException("Cannot bind property to itself");
		}
	}

	public static BidirectionalDifferenceBinding bind(DoubleProperty property1,
			DoubleProperty property2, DoubleExpression difference) {
		checkParameters(property1, property2, difference);
		final BidirectionalDifferenceBinding binding = new BidirectionalDifferenceBinding(
				property1, property2, difference);
		property2.setValue(property1.getValue() - difference.getValue());
		property1.addListener(binding);
		property2.addListener(binding);
		difference.addListener(binding);
		return binding;
	}

	protected Property<Number> getProperty1() {
		return propertyRef1.get();
	}

	protected Property<Number> getProperty2() {
		return propertyRef2.get();
	}

	protected DoubleExpression getDifference() {
		return differenceRef.get();
	}

	@Override
	public void changed(ObservableValue<? extends Number> sourceProperty,
			Number oldValue, Number newValue) {
		if (!updating) {
			final DoubleProperty property1 = propertyRef1.get();
			final DoubleProperty property2 = propertyRef2.get();
			final DoubleExpression difference = differenceRef.get();
			if ((property1 == null) || (property2 == null)
					|| (difference == null)) {
				if (property1 != null) {
					property1.removeListener(this);
				}
				if (property2 != null) {
					property2.removeListener(this);
				}
				if (difference != null) {
					difference.removeListener(this);
				}
			} else {
				try {
					updating = true;
					if (property2 == sourceProperty) {
						if (!property1.isBound()) {
							property1.set(newValue.doubleValue()
									- difference.doubleValue());
						} else {
							System.out
									.println("Property 2 changed, Property 1 bound");
						}
					} else if (property1 == sourceProperty) {
						if (!property2.isBound()) {
							property2.set(newValue.doubleValue()
									+ difference.doubleValue());
						} else {
							System.out
									.println("Property 1 changed, Property 2 bound");
						}
					} else {
						if (!property2.isBound()) {
							property2.set(property1.doubleValue()
									+ newValue.doubleValue());
						} else if (!property1.isBound()) {
							property1.set(property2.doubleValue()
									- newValue.doubleValue());
						} else {
							System.out
									.println("Difference Changed, Properties 1 & 2 bound");
						}
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
					if (property2 == sourceProperty) {
						property2.set(oldValue.doubleValue());
						System.out
								.println("Exception thrown, Property 2 reset");
					} else if (property1 == sourceProperty) {
						property1.set(oldValue.doubleValue());
						System.out
								.println("Exception thrown, Property 1 reset");
					}
					throw new RuntimeException(
							"BidirectionalDifferenceBinding binding failed, setting to the previous value",
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
		return (getProperty1() == null) || (getProperty2() == null)
				|| (getDifference() == null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		final Object propertyA1 = getProperty1();
		final Object propertyA2 = getProperty2();
		final Object differenceA3 = getDifference();
		if ((propertyA1 == null) || (propertyA2 == null)
				|| (differenceA3 == null)) {
			return false;
		}

		if (obj instanceof BidirectionalDifferenceBinding) {
			final BidirectionalDifferenceBinding otherBinding = (BidirectionalDifferenceBinding) obj;
			final Object propertyB1 = otherBinding.getProperty1();
			final Object propertyB2 = otherBinding.getProperty2();
			final Object differenceB3 = otherBinding.getDifference();
			if ((propertyB1 == null) || (propertyB2 == null)
					|| (differenceB3 == null)) {
				return false;
			}
			if (differenceA3.equals(differenceB3)) {
				if ((propertyA1.equals(propertyB1) && (propertyA2
						.equals(propertyB2)))) {
					return true;
				}
				if ((propertyA1.equals(propertyB2) && (propertyA2
						.equals(propertyB1)))) {
					return true;
				}
			}
		}
		return false;
	}

}
