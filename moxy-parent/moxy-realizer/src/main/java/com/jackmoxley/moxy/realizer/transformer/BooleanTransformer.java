package com.jackmoxley.moxy.realizer.transformer;

public class BooleanTransformer implements Transformer {

	@Override
	public Object transform(Object input) {
		if(input == null){
			return Boolean.FALSE;
		}
		return Boolean.parseBoolean(input.toString());
	}

}
