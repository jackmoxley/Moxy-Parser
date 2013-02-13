package com.jackmoxley.moxy.token.stream;

import java.util.Collections;
import java.util.List;

import com.jackmoxley.moxy.token.Token;

public class TokenStreamImpl<T extends Token> implements TokenStream<T>{


	protected final List<T> tokens;
	
	public TokenStreamImpl(List<T> tokens) {
		super();
		this.tokens = tokens;
	}

	@Override
	public T tokenAt(int index) {
		if(index >= tokens.size()){
			return null;
		}
		return tokens.get(index);
	}
	
	

	@Override
	public List<T> tokens(int start, int length) {
		if(start < 0 || length <= 0){
			return Collections.emptyList();
		}
		int end = start+length;
		if(end >= tokens.size()){
			return Collections.emptyList();
		}
		return tokens.subList(start, end);
	}
	
}
