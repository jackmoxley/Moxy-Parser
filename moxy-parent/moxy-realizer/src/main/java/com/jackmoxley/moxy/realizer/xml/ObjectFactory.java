//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.12 at 08:19:08 AM GMT 
//


package com.jackmoxley.moxy.realizer.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jackmoxley.moxy.realizer.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Beans_QNAME = new QName("http://www.jackmoxley.com/Moxy", "beans");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jackmoxley.moxy.realizer.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Beans }
     * 
     */
    public Beans createBeans() {
        return new Beans();
    }

    /**
     * Create an instance of {@link BeanMethod }
     * 
     */
    public BeanMethod createBeanMethod() {
        return new BeanMethod();
    }

    /**
     * Create an instance of {@link BeanConstructor }
     * 
     */
    public BeanConstructor createBeanConstructor() {
        return new BeanConstructor();
    }

    /**
     * Create an instance of {@link BeanField }
     * 
     */
    public BeanField createBeanField() {
        return new BeanField();
    }

    /**
     * Create an instance of {@link BeanArgument }
     * 
     */
    public BeanArgument createBeanArgument() {
        return new BeanArgument();
    }

    /**
     * Create an instance of {@link Bean }
     * 
     */
    public Bean createBean() {
        return new Bean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Beans }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.jackmoxley.com/Moxy", name = "beans")
    public JAXBElement<Beans> createBeans(Beans value) {
        return new JAXBElement<Beans>(_Beans_QNAME, Beans.class, null, value);
    }

}
