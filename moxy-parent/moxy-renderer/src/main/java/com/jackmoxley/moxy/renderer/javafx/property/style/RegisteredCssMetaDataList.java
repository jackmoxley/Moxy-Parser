package com.jackmoxley.moxy.renderer.javafx.property.style;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javafx.css.CssMetaData;
import javafx.css.Styleable;

public class RegisteredCssMetaDataList{
	
	private final List<CssMetaData<? extends Styleable, ?>>  metaData;
	
	/**
	 * @param beanClass
	 */
	public RegisteredCssMetaDataList(Collection<CssMetaData<? extends Styleable, ?>> inheritedMetaData, CssMetaData<? extends Styleable, ?> classMetaData) {
		super();
		List<CssMetaData<? extends Styleable, ?>> metaData = new ArrayList<>(inheritedMetaData);
		Collections.addAll(metaData, classMetaData);
		this.metaData = Collections.unmodifiableList(metaData);
	}

	

	public List<CssMetaData<? extends Styleable, ?>> getMetaData() {
		return metaData;
	}

}
