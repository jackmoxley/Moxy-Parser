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
package com.jackmoxley.moxy.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.stream.TokenStream;

public class RuleEvaluator {


	private static final Logger logger = LoggerFactory.getLogger(RuleEvaluator.class);
	private TreeMap<Integer, Map<Rule, RuleDecision>> rulesHistory = new TreeMap<Integer, Map<Rule, RuleDecision>>();
	private TokenStream<CharacterToken> sequence;
	private Map<String,Rule> links;
	private int heirachy = 0;
	
	

	public Entry<Rule,RuleDecision> getLastPassed(){
		for(Entry<Integer, Map<Rule, RuleDecision>> entry : rulesHistory.descendingMap().entrySet()){
			for(Entry<Rule,RuleDecision> ruleEntry : entry.getValue().entrySet()){
				if(ruleEntry.getKey() instanceof CharacterRule && ruleEntry.getValue().hasPassed()){
					return ruleEntry;
				}
			}
		}
		return null;
	}
	
	public Entry<Rule,RuleDecision> getLast(){
		for(Entry<Integer, Map<Rule, RuleDecision>> entry : rulesHistory.descendingMap().entrySet()){
			for(Entry<Rule,RuleDecision> ruleEntry : entry.getValue().entrySet()){
					return ruleEntry;
			}
		}
		return null;
	}


	public Map<Rule, RuleDecision> getRuleDecisions(int startIndex) {
		Map<Rule, RuleDecision> history = rulesHistory.get(startIndex);
		if (history == null) {
			history = new HashMap<Rule, RuleDecision>();
			rulesHistory.put(startIndex, history);
		}
		return history;
	}

	public RuleDecision getRuleDecision(Rule rule, int startIndex) {
		Map<Rule, RuleDecision> history = getRuleDecisions(startIndex);
		RuleDecision decision = history.get(rule);
		if (decision == null) {
			decision = new RuleDecision(startIndex);
			history.put(rule, decision);
		}
		return decision;
	}

	public RuleDecision evaluate(Rule rule, int startIndex) {
		logger.debug("{} Visiting {}",heirachy++, rule);
		// Lets check to see if we have already considered this branch
		RuleDecision decision = getRuleDecision(rule, startIndex);

		if (decision.isConsidering()) {
			// The decision branch has failed because it is cyclic.
			decision.failed("{} is cyclic",rule.getClass().getSimpleName());
		} else {
			if (decision.isUnconsidered()) {
				decision.considering();
				// we expect the rule itself to decide whether we have passed or not.
				// It is vitaly important that it does.
				rule.consider(this, decision);
			}
		}
		logger.debug(--heirachy+ " Visited "+rule+": "+decision.getFailure()+": "+decision ,decision.getArguments());
		return decision;
	}

	public Rule ruleForName(String symbol) {
		return links.get(symbol);
	}

	public TokenStream<CharacterToken> getSequence() {
		return sequence;
	}

	public void setSequence(TokenStream<CharacterToken> sequence) {
		this.sequence = sequence;
	}

	public Map<String, Rule> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Rule> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleVisitor [rulesHistory=").append(rulesHistory)
				.append("\nsequence=").append(sequence).append("\nlinks=")
				.append(links).append("]");
		return builder.toString();
	}
	
	
}
