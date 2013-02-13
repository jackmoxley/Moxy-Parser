package com.jackmoxley.moxy.realizer;

import java.util.List;

import com.jackmoxley.moxy.token.Token;

public interface Realizer<T> {
	public List<T> realize(List<Token> tokens) ;
}
