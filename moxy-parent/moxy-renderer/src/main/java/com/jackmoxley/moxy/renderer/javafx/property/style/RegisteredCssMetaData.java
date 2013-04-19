package com.jackmoxley.moxy.renderer.javafx.property.style;

import javafx.beans.property.Property;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;

public class RegisteredCssMetaData<S extends Styleable,V> extends CssMetaData<S,V> {

	private final String name;
	
	public RegisteredCssMetaData(String property, StyleConverter<?, V> converter, Class<S> beanClass, String name) {
		super(property, converter);
		this.name = name;
		CssRegistry.registerMetaData(beanClass, this, name);
	}
	
	@Override
	public boolean isSettable(S bean) {
		StyleableProperty<?> property = CssRegistry.fetchProperty(bean, name);
		if(property instanceof Property){
			if(((Property<?>)property).isBound()){
				return property == null;
			}
		}
		return true;
	}

	@Override
	public StyleableProperty<V> getStyleableProperty(S bean) {
		StyleableProperty<V> property = CssRegistry.fetchProperty(bean, name);
		return property;
	}


	public String getName() {
		return name;
	}

}
