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
