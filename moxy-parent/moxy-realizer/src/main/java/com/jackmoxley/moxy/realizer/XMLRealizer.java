/**
 * Copyright (C) 2013  John Orlando Keleshian Moxley
 * 
 * Unless otherwise stated by the license provided by the copyright holder.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
