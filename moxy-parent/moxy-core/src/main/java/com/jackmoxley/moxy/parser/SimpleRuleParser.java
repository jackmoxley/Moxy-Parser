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
package com.jackmoxley.moxy.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.parser.history.RuleHistory;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.stream.TokenStream;

/**
 * SimpleRuleParser is the meat behind the parsing engine. It is fairly simple
 * in its design but very powerful. We keep our parsing efficient by sacrificing
 * memory, something which in the old days wasn't possible. Additionally
 * optimizers are provided to further reduce some of the complex overheads of
 * large rule graphs.
 * 
 * When parsing we choose the root of our rule-graph and ask it to consider our
 * sequence of tokens, typically with a given index of that sequence to start
 * from, most often the beginning. If terminal, the rule will assess the
 * relevant tokens from the sequence, it will then make a decision as to whether
 * it has passed or failed. It it has failed we make a not of why, and if it has
 * passed we typically add those tokens to its decision. If functional, the rule
 * will ask the parser to consider its child rules and thus in a depth first
 * traversal we visit all the nodes, the parser will return the decision made by
 * the child rule, and typically the functional rule will add those that
 * decision's tokens to its own, and mark the next index.
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
	private RuleHistory history;

	/**
	 * @param grammar
	 * @param sequence
	 */
	public SimpleRuleParser(Grammar grammar, RuleHistory history,
			TokenStream<CharacterToken> sequence) {
		super();
		this.grammar = grammar;
		this.sequence = sequence;
		this.history = history;
	}

	@Override
	public RuleDecision parse(RuleGraph rule) {
		return parse(rule.getRule());
	}

	@Override
	public RuleDecision parse(Rule rule) {
		return evaluate(rule, 0);
	}

	/**
	 * The evaluate method does 2 things, firstly it checks to see if a decision
	 * has already been made for the index requested, if a decision has been
	 * made and it has passed or failed we immediately return that decision. If
	 * the rule is currently under consideration for that index we fail it as
	 * cyclic, otherwise we then ask the rule for the correct decision. The
	 * actual owner of the RuleDecision is the history and the evaluate method
	 * and the rule it calls merely modify that object rather than create one.
	 * 
	 * As you may note, apart from some rare rules with their own cyclic
	 * detection all history based evaluation is done here, this means other
	 * implementations with other strategies could quitehappily use the same
	 * rules.
	 * 
	 * @param rule
	 * @param startIndex
	 * @return
	 */
	@Override
	public RuleDecision evaluate(Rule rule, int startIndex) {
		logger.debug("Visiting {}", rule);
		// Lets check to see if we have already considered this branch
		RuleDecision decision = history.getRuleDecision(rule, startIndex);

		if (decision.isConsidering()) {
			/*
			 * We fail at this branch, but we don't want to fail the whole rule,
			 * as they're maybe options where it does pass.Any decent RuleTree
			 * is going to have some form of cycling going on so its not worth
			 * logging this higher than debug.
			 */
			decision.cyclic();
		} else {
			if (decision.isUnconsidered()) {
				decision.considering();
				/*
				 * we expect the rule itself to decide whether we have passed or
				 * not. It is vitally important that it does.
				 */
				try {
					rule.consider(this, decision);
					/*
					 * Well if your not going to have your cake, I will have to
					 * eat it. And log it, I hope your embarrassed.
					 */
					if (decision.isIncomplete()) {
						logger.warn(
								"Rule {} could not make up its mind, we fail by default",
								rule);
						decision.failed(
								"Rule {} could not make up its mind, we fail by default",
								rule);
					}
				} catch (Throwable t) {
					/*
					 * We want people to write their own rules, but we want them
					 * to play well with others.
					 */
					logger.warn("Rule {} threw an exception", rule, t);
					decision.failed("Rule {} threw exception: {}", rule,
							t.getMessage());
				}
			}
		}
		if (decision.hasPassed()) {
			logger.debug("Passed {}: ", rule);
		} else if (logger.isDebugEnabled()) {
			Object[] arguments = decision.getArguments();
			Object[] newArguments = new Object[arguments.length+1];
			newArguments[0] = rule;
			System.arraycopy(arguments, 0, newArguments, 1, arguments.length);
			logger.debug("Failed {}: " + decision.getFailure()
					+ ": ", newArguments);
		}
		return decision;
	}

	@Override
	public Rule ruleForName(String symbol) {
		RuleGraph tree = grammar.get(symbol);
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
