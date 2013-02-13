package com.jackmoxley.moxy.grammer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.Token;
import com.jackmoxley.moxy.token.stream.TokenStream;

public abstract class RuledGrammer implements Grammer  {

	private static final long serialVersionUID = -3966010824935289514L;
	
	protected Map<String,Rule> ruleMap;

	public Map<String, Rule> getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(Map<String, Rule> ruleMap) {
		this.ruleMap = ruleMap;
	}
	

	@Override
	public Map<String, String> getSyntaxMap() {
		return Collections.emptyMap();
	}

	public List<Token> parse(TokenStream<CharacterToken> input){
		RuleEvaluator visitor = new RuleEvaluator();
		visitor.setLinks(ruleMap);
		visitor.setSequence(input);
		RuleDecision decision = visitor.evaluate(getStart(), 0);
		return decision.getTokens();
	}
	
	protected abstract Rule getStart();
	
}
