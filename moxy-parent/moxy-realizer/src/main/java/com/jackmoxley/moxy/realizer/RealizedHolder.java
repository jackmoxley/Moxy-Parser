package com.jackmoxley.moxy.realizer;

public class RealizedHolder<T> {

	String name;
	T value;
	
	public RealizedHolder() {
		
	}
	public RealizedHolder(String name, T value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RealizedHolder [name=").append(name).append(", value=")
				.append(value).append("]");
		return builder.toString();
	}
	
	
	
}
