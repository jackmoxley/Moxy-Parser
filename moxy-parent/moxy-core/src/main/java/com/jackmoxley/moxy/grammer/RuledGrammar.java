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
import java.util.Map;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;


@Beta
public class RuledGrammar implements Grammar  {

	private static final long serialVersionUID = -1L;
	
	protected Map<String,RuleGraph> ruleGraphs  = new LinkedHashMap<>();
	

	public Map<String, RuleGraph> getRuleTrees() {
		return ruleGraphs;
	}

	public void setRuleTrees(Map<String, RuleGraph> ruleGraphs) {
		this.ruleGraphs = ruleGraphs;
	}
	
	public RuleGraph get(String ruleName){
		return this.getRuleTrees().get(ruleName);
	}


	public void put(String ruleName, Rule rule) {
		getRuleTrees().put(ruleName, new RuleGraph(rule,ruleName));
	}

	public void put(String ruleName, Rule rule, String textualSyntax) {
		getRuleTrees().put(ruleName, new RuleGraph(rule,ruleName,textualSyntax));
	}

	@Override
	public String toString() {
		return "RuledGrammar [ruleGraphs=" + ruleGraphs + "]";
	}
	
}
