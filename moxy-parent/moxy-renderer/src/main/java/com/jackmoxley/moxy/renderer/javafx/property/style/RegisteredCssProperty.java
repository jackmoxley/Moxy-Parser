package com.jackmoxley.moxy.renderer.javafx.property.style;

import javafx.css.Styleable;

public interface RegisteredCssProperty<S extends Styleable> {

	 S getBean();

	 String getName();
}
