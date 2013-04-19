package com.jackmoxley.moxy.renderer.javafx.property.style;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;

public class RegisteredCssDoubleProperty<S extends Styleable> extends StyleableDoubleProperty implements RegisteredCssProperty<S>{
	
	private final S bean;
	private final String name;

	/**
	 * @param bean
	 * @param name
	 * @param metaData
	 */
	public RegisteredCssDoubleProperty(S bean, String name) {
		super();
		this.bean = bean;
		this.name = name;
		CssRegistry.registerProperty(this);
	}
	
	
	/**
	 * @param initialValue
	 */
	public RegisteredCssDoubleProperty(S bean, String name, double initialValue) {
		super(initialValue);
		this.bean = bean;
		this.name = name;
		CssRegistry.registerProperty(this);
	}


	@Override
	public CssMetaData<?, Number> getCssMetaData() {
		return CssRegistry.fetchCssMetaData(bean.getClass(), name);

	}

	@Override
	public S getBean() {
		return bean;
	}

	@Override
	public String getName() {
		return name;
	}
	
}
