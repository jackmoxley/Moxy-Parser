package com.jackmoxley.moxy.realizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.jackmoxley.moxy.realizer.xml.Beans;

public class XMLRealizer<T> extends BeanRealizer<T> implements Realizer<T> {

	public XMLRealizer(String file) throws JAXBException, FileNotFoundException {
		this(new FileReader(file));
	}

	public XMLRealizer(Reader reader) throws JAXBException {
		super();
		Unmarshaller unmarshaller = getUnmarshaller();
		setJAXBBeans(unmarshaller.unmarshal(reader));

	}

	public XMLRealizer(InputStream stream) throws JAXBException {
		super();
		Unmarshaller unmarshaller = getUnmarshaller();
		setJAXBBeans(unmarshaller.unmarshal(stream));
	}

	@SuppressWarnings("unchecked")
	protected void setJAXBBeans(Object beans) {
		if (beans instanceof JAXBElement) {
			createBeanMap(((JAXBElement<Beans>) beans).getValue().getBean());
		} else {
			createBeanMap(((Beans) beans).getBean());
		}
	}

	protected static Unmarshaller getUnmarshaller() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Beans.class.getPackage()
				.getName());
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return unmarshaller;
	}

}
