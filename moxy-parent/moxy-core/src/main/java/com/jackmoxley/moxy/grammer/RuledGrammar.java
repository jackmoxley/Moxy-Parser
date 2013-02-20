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
package com.jackmoxley.moxy.grammer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.Token;
import com.jackmoxley.moxy.token.stream.TokenStream;


@Beta
public class RuledGrammar implements Grammar  {

	private static final long serialVersionUID = -1L;
	
	protected Map<String,RuleTree> ruleTrees  = new LinkedHashMap<>();
	

	public Map<String, RuleTree> getRuleTrees() {
		return ruleTrees;
	}

	public void setRuleTrees(Map<String, RuleTree> ruleTrees) {
		this.ruleTrees = ruleTrees;
	}


	public List<Token> parse(TokenStream<CharacterToken> input, String start){
		RuleEvaluator visitor = new RuleEvaluator(this,input);
		RuleDecision decision = visitor.evaluate(get(start).getRule(), 0);
		return decision.getTokens();
	}
	
	public RuleTree get(String ruleName){
		return this.getRuleTrees().get(ruleName);
	}
	


	public void put(String ruleName, Rule rule) {
		getRuleTrees().put(ruleName, new RuleTree(rule,ruleName));
	}

	public void put(String ruleName, Rule rule, String textualSyntax) {
		getRuleTrees().put(ruleName, new RuleTree(rule,ruleName,textualSyntax));
	}

	@Override
	public String toString() {
		return "RuledGrammar [ruleTrees=" + ruleTrees + "]";
	}
	
}