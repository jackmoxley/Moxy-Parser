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
package com.jackmoxley.moxy.rule.functional.single;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleParser;
import com.jackmoxley.moxy.rule.RuleVisitor;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

@Beta
public class UntilRule extends MinMaxRule {

	private static final long serialVersionUID = 1L;

	private Rule until;
	private boolean include;
	private boolean greedy;

	public UntilRule() {
		super();
	}

	public UntilRule(Rule rule, Rule until) {
		super(rule);
		this.until = until;
	}

	public UntilRule(Rule rule, Rule until, boolean include) {
		this(rule, until);
		this.include = include;
	}

	public UntilRule(Rule rule, Rule until, boolean include, boolean greedy) {
		this(rule, until, include);
		this.greedy = greedy;
	}

	public UntilRule(Rule rule, int exact, Rule until, boolean include,
			boolean greedy) {
		super(rule, exact);
		this.until = until;
		this.include = include;
		this.greedy = greedy;
	}

	public UntilRule(Rule rule, int min, int max, Rule until, boolean include,
			boolean greedy) {
		super(rule, min, max);
		this.until = until;
		this.include = include;
		this.greedy = greedy;
	}

	@Override
	public void accept(RuleVisitor visitor) {
		super.accept(visitor);
		until.accept(visitor);
	}

	protected void considerMax(RuleParser visitor, RuleDecision decision) {
		boolean unlimited = max < 0;
		RuleDecision current = new RuleDecision(decision.getStartIndex());
		current.setNextIndex(decision.getNextIndex());
		RuleDecision greedyCurrent = null;
		RuleDecision subDecision;
		for (int i = min; (unlimited || (i < max)); i++) {
			subDecision = visitor.evaluate(until, current.getNextIndex());
			if (subDecision.hasPassed()) {
				if (greedy) {
					greedyCurrent = new RuleDecision(current);
					if (include) {
						greedyCurrent.add(subDecision);
					}
				} else {
					if (include) {
						current.add(subDecision);
					}
					decision.add(current);

					decision.passed();
					return;
				}
			}
			subDecision = visitor.evaluate(rule, current.getNextIndex());
			if (subDecision.hasPassed()) {
				/*
				 * If our subrule is non-collecting, if it passes once it will
				 * continue to pass and in the case of unlimited maximums we
				 * want to avoid the case of an infinite loop. Because it will
				 * always pass, we know that the untilrule will always pass. It
				 * is important to know that rules are presumed to be unchanging
				 * between evaluations, i.e. a rule that is ran against the same
				 * set of tokens will always return the same result,
				 * irrespective of what has come before.
				 */
				if (FunctionalRule.isNotCollecting(current, subDecision)) {
					break;
				}
				current.add(subDecision);
			} else {
				break;
			}
		}
		subDecision = visitor.evaluate(until, current.getNextIndex());
		if (subDecision.hasPassed()) {
			decision.add(current);
			if(include){
				decision.add(subDecision);
			}
			decision.passed();
		} else if (greedy && greedyCurrent != null) {
			decision.add(greedyCurrent);
			decision.passed();
		} else {
			decision.failed("{} has failed as until rule was not achieved.",
					this);
		}
	}

	public Rule getUntil() {
		return until;
	}

	public void setUntil(Rule until) {
		this.until = until;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public boolean isGreedy() {
		return greedy;
	}

	public void setGreedy(boolean greedy) {
		this.greedy = greedy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UntilRule [until=").append(until).append(", include=")
				.append(include).append(", greedy=").append(greedy).append("]");
		return builder.toString();
	}

}
