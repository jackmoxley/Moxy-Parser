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

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuleTree;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.stream.TokenStream;

/**
 * RuleEvaluator is the meat behind the parsing engine. It is fairly simple in
 * its design but very powerful. We perform our LL(*) parsing and keep it
 * Efficient by sacrificing memory, something which in the old days wasn't
 * possible. Additionally optimizers are provided to further reduce some of the
 * complex overheads of large rule graphs.
 * 
 * For every character in our stream, we maintain a history of every rule
 * whether passed failed or still evaluating, that started evaluating on that
 * character. This gives several advantages, firstly it allows us to avoid
 * recursion, if a rule is still evaluating, then if the same rule hits it
 * again, we fail that rule. Secondly if we know a rule has passed or failed
 * before, and either could of been the case, then we don't need to evaluate
 * that rule, we just reuse that decision. This involves saving the tokens it
 * evaluated as well. Thirdly and in my opinion most usefully, this allows us to
 * start debugging what is going on.
 * 
 * 
 * 
 * @author jack
 * 
 */
@Beta
public class RuleEvaluator {

	private static final Logger logger = LoggerFactory
			.getLogger(RuleEvaluator.class);
	private TreeMap<Integer, Map<Rule, RuleDecision>> rulesHistory = new TreeMap<Integer, Map<Rule, RuleDecision>>();
	private TokenStream<CharacterToken> sequence;
	private Grammar grammar;
	private int heirachy = 0;

	/**
	 * @param grammar
	 * @param sequence
	 */
	public RuleEvaluator(Grammar grammar, TokenStream<CharacterToken> sequence) {
		super();
		this.grammar = grammar;
		this.sequence = sequence;
	}

	public Entry<Rule, RuleDecision> getLastPassed() {
		for (Entry<Integer, Map<Rule, RuleDecision>> entry : rulesHistory
				.descendingMap().entrySet()) {
			for (Entry<Rule, RuleDecision> ruleEntry : entry.getValue()
					.entrySet()) {
				if (ruleEntry.getKey() instanceof CharacterRule
						&& ruleEntry.getValue().hasPassed()) {
					return ruleEntry;
				}
			}
		}
		return null;
	}

	public Entry<Rule, RuleDecision> getLast() {
		for (Entry<Integer, Map<Rule, RuleDecision>> entry : rulesHistory
				.descendingMap().entrySet()) {
			for (Entry<Rule, RuleDecision> ruleEntry : entry.getValue()
					.entrySet()) {
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

	public RuleDecision evaluate(RuleTree rule) {
		return evaluate(rule.getRule());
	}

	public RuleDecision evaluate(Rule rule) {
		return evaluate(rule, 0);
	}

	public RuleDecision evaluate(Rule rule, int startIndex) {
		logger.debug("{} Visiting {}", heirachy++, rule);
		// Lets check to see if we have already considered this branch
		RuleDecision decision = getRuleDecision(rule, startIndex);

		if (decision.isConsidering()) {
			// The decision branch has failed because it is cyclic.
			decision.failed("{} is cyclic", rule.getClass().getSimpleName());
		} else {
			if (decision.isUnconsidered()) {
				decision.considering();
				// we expect the rule itself to decide whether we have passed or
				// not.
				// It is vitaly important that it does.
				rule.consider(this, decision);
			}
		}
		logger.debug(
				--heirachy + " Visited " + rule + ": " + decision.getFailure()
						+ ": " + decision, decision.getArguments());
		return decision;
	}

	public Rule ruleForName(String symbol) {
		RuleTree tree = grammar.get(symbol);
		return tree == null ? null : tree.getRule();
	}

	public TokenStream<CharacterToken> getSequence() {
		return sequence;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleVisitor [rulesHistory=").append(rulesHistory)
				.append("\nsequence=").append(sequence).append("\nlinks=")
				.append(grammar).append("]");
		return builder.toString();
	}

}
