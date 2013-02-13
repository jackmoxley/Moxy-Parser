package com.jackmoxley.moxy.grammer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.Token;
import com.jackmoxley.moxy.token.stream.TokenStream;

public interface Grammer extends Serializable {

	public List<Token> parse(TokenStream<CharacterToken> input);

	public Map<String, Rule> getRuleMap();
	public Map<String, String> getSyntaxMap();
}
