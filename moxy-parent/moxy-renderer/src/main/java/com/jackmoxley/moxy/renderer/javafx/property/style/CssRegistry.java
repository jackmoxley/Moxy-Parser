package com.jackmoxley.moxy.renderer.javafx.property.style;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;

public class CssRegistry {
	
	
	private static Map<Object, Map<String, StyleableProperty<?>>> propertyMap = new WeakHashMap<>();
	private static Map<Class<?>, Map<String, CssMetaData<?,?>>> metaDataMap = new HashMap<>();

	public static <S extends Styleable> void registerProperty(RegisteredCssDoubleProperty<S> property){
		S bean = property.getBean();
		String name = property.getName();
		Map<String, StyleableProperty<?>> namedProperties = propertyMap.get(bean);
		if(namedProperties == null){
			namedProperties = new WeakHashMap<>();
			propertyMap.put(bean, namedProperties);
		}
		namedProperties.put(name, property);
	}
	
	public static <S extends Styleable> void registerProperty(S bean, StyleableProperty<?> property, String name){
		Map<String, StyleableProperty<?>> namedProperties = propertyMap.get(bean);
		if(namedProperties == null){
			namedProperties = new WeakHashMap<>();
			propertyMap.put(bean, namedProperties);
		}
		namedProperties.put(name, property);
	}

	public static <S extends Styleable, V> void registerMetaData(Class<?> beanClass, CssMetaData<S, V> metaData, String name){
		Map<String, CssMetaData<?,?>> nameMap = metaDataMap.get(beanClass);
		if(nameMap == null){
			nameMap = new HashMap<>();
			metaDataMap.put(beanClass, nameMap);
		}
		if(nameMap.get(name) == null){
			nameMap.put(name, metaData);
		}
	}
	
	public static <S extends Styleable, V> void registerMetaData(Class<S> beanClass, RegisteredCssMetaData<S, V> metaData){
		String name = metaData.getName();
		Map<String, CssMetaData<?,?>> nameMap = metaDataMap.get(beanClass);
		if(nameMap == null){
			nameMap = new HashMap<>();
			metaDataMap.put(beanClass, nameMap);
		}
		if(nameMap.get(name) == null){
			nameMap.put(name, metaData);
		}
	}

	
	public static Collection<CssMetaData<?,?>> metaDataForClass(Class<?> beanClass){
		return metaDataMap.get(beanClass).values();
	}
	
	public static <S extends Styleable, V> RegisteredCssMetaData<S, V> fetchCssMetaData(Class<S> beanClass, String name){
		Map<String, CssMetaData<?,?>> namedMetaData = metaDataMap.get(beanClass);
		if(namedMetaData == null){
			return null;
		}
		return (RegisteredCssMetaData<S, V>)namedMetaData.get(name);
	}

	public static <V> StyleableProperty<V> fetchProperty(Styleable bean, String name){
		Map<String, StyleableProperty<?>> namedProperties = propertyMap.get(bean);
		if(namedProperties == null){
			return null;
		}
		return (StyleableProperty<V>)namedProperties.get(name);
	}
}
