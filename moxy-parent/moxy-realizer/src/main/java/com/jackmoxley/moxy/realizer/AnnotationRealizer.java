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

import javax.xml.bind.JAXBException;

/**
 * TODO 
 * @author jack
 *
 * @param <T>
 */
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
