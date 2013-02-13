package com.jackmoxley.moxy.token.stream;

import java.util.List;

import com.jackmoxley.moxy.token.Token;


public interface TokenStream<T extends Token> {

	public T tokenAt(int index);
	

	public List<T> tokens(int start, int length);
}
