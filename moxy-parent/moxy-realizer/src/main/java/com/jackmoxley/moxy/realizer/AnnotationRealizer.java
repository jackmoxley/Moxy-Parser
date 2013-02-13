package com.jackmoxley.moxy.realizer;

import javax.xml.bind.JAXBException;

public class AnnotationRealizer<T> extends BeanRealizer<T> implements Realizer<T> {


	public AnnotationRealizer(Class<?>... classes)  {
		super();

	}

	public AnnotationRealizer() throws JAXBException {
		super();
	}

	protected void registerClass(Class<?> clazz){
		
		
	}

}
