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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuleTree;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.stream.TokenStream;

/**
 * RuleParser is the meat behind the parsing engine. It is fairly simple in
 * its design but very powerful. We perform our LL(*) parsing and keep it
 * Efficient by sacrificing memory, something which in the old days wasn't
 * possible. Additionally optimizers are provided to further reduce some of the
 * complex overheads of large rule graphs.
 * 
 * For every character in our stream, we maintain a history of every rule
 * whether passed failed or still evaluating, that started evaluating on that
 * character. This gives several advantages, firstly it allows us to avoid
 * recursion, if a rule is still evaluating, then we can detect it and fail at
 * that point, without getting us into an infinite loop. Secondly if we know a
 * rule has passed or failed before, and either could of been the case, then we
 * don't need to evaluate that rule, we just reuse that decision. This involves
 * saving the tokens it evaluated as well. Thirdly and in my opinion most
 * usefully, this allows us to start debugging what is going on, including being
 * able to replay decisions.
 * 
 * @author jack
 * 
 */
@Beta
public class SimpleRuleParser implements RuleParser {

	private static final Logger logger = LoggerFactory
			.getLogger(SimpleRuleParser.class);
	private TokenStream<CharacterToken> sequence;
	private Grammar grammar;
	private int heirachy = 0;
	private RuleHistory history;

	/**
	 * @param grammar
	 * @param sequence
	 */
	public SimpleRuleParser(Grammar grammar, RuleHistory history, TokenStream<CharacterToken> sequence) {
		super();
		this.grammar = grammar;
		this.sequence = sequence;
		this.history = history;
	}

	@Override
	public RuleDecision parse(RuleTree rule) {
		return parse(rule.getRule());
	}

	@Override
	public RuleDecision parse(Rule rule) {
		return evaluate(rule, 0);
	}

	@Override
	public RuleDecision evaluate(Rule rule, int startIndex) {
		logger.debug("{} Visiting {}", heirachy++, rule);
		// Lets check to see if we have already considered this branch
		RuleDecision decision = history.getRuleDecision(rule, startIndex);

		if (decision.isConsidering()) {
			logger.debug("{} Cyclic Rule detected {}", --heirachy, rule);
			// We fail at this branch, but we don't want to fail the whole rule,
			// as they're maybe options where it does pass.
			return RuleDecision.cyclic();
		} else {
			if (decision.isUnconsidered()) {
				decision.considering();
				/*
				 * we expect the rule itself to decide whether we have passed or
				 * not. It is vitaly important that it does.
				 */
				try {
					rule.consider(this, decision);
				} catch (Throwable t) {
					/*
					 * We want people to write their own rules, but we want them
					 * to play well with others.
					 */
					logger.warn("Rule {} throw exception", rule, t);
					decision.failed("Rule {} throw exception", rule);
				}
			}
		}
		logger.debug(
				--heirachy + " Visited " + rule + ": " + decision.getFailure()
						+ ": " + decision, decision.getArguments());
		return decision;
	}

	@Override
	public Rule ruleForName(String symbol) {
		RuleTree tree = grammar.get(symbol);
		return tree == null ? null : tree.getRule();
	}

	@Override
	public TokenStream<CharacterToken> getSequence() {
		return sequence;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public RuleHistory getHistory() {
		return history;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleVisitor [history=").append(history)
				.append("\nsequence=").append(sequence).append("\nlinks=")
				.append(grammar).append("]");
		return builder.toString();
	}

}
