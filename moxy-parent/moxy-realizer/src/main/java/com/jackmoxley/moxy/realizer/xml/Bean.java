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

package com.jackmoxley.moxy.realizer.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Bean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="constructor" type="{http://www.jackmoxley.com/Moxy}BeanConstructor" minOccurs="0"/>
 *         &lt;element name="field" type="{http://www.jackmoxley.com/Moxy}BeanField" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="method" type="{http://www.jackmoxley.com/Moxy}BeanMethod" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="extends" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="includes" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="singleton" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bean", propOrder = {
    "constructor",
    "field",
    "method"
})
public class Bean {

    protected BeanConstructor constructor;
    protected List<BeanField> field;
    protected List<BeanMethod> method;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "class")
    protected String clazz;
    @XmlAttribute(name = "extends")
    protected String _extends;
    @XmlAttribute(name = "includes")
    protected String includes;
    @XmlAttribute(name = "singleton")
    protected Boolean singleton;

    /**
     * Gets the value of the constructor property.
     * 
     * @return
     *     possible object is
     *     {@link BeanConstructor }
     *     
     */
    public BeanConstructor getConstructor() {
        return constructor;
    }

    /**
     * Sets the value of the constructor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeanConstructor }
     *     
     */
    public void setConstructor(BeanConstructor value) {
        this.constructor = value;
    }

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeanField }
     * 
     * 
     */
    public List<BeanField> getField() {
        if (field == null) {
            field = new ArrayList<BeanField>();
        }
        return this.field;
    }

    /**
     * Gets the value of the method property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the method property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeanMethod }
     * 
     * 
     */
    public List<BeanMethod> getMethod() {
        if (method == null) {
            method = new ArrayList<BeanMethod>();
        }
        return this.method;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the extends property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtends() {
        return _extends;
    }

    /**
     * Sets the value of the extends property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtends(String value) {
        this._extends = value;
    }

    /**
     * Gets the value of the includes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncludes() {
        return includes;
    }

    /**
     * Sets the value of the includes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludes(String value) {
        this.includes = value;
    }

    /**
     * Gets the value of the singleton property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSingleton() {
        if (singleton == null) {
            return false;
        } else {
            return singleton;
        }
    }

    /**
     * Sets the value of the singleton property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSingleton(Boolean value) {
        this.singleton = value;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean [constructor=").append(constructor)
				.append(", field=").append(field).append(", method=")
				.append(method).append(", name=").append(name)
				.append(", clazz=").append(clazz).append(", _extends=")
				.append(_extends).append(", includes=").append(includes)
				.append(", singleton=").append(singleton).append("]");
		return builder.toString();
	}

}
