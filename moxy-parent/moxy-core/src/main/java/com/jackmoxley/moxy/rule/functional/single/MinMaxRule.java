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
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.RuleParser;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

@Beta
public class MinMaxRule extends SingleRule {

	private static final long serialVersionUID = 1L;

	public static final int UNLIMITED = -1;

	protected int min = UNLIMITED;
	protected int max = UNLIMITED;

	/**
	 * Defaults to an unlimited number of rule checks, this will automatically
	 * pass, even if the subrule fails the first time, but will collect every
	 * time the subrule passes until the first fail. This is prone to
	 * repetitiveness.
	 */
	public MinMaxRule() {
		super();
	}


	public MinMaxRule(Rule rule) {
		super(rule);
	}
	
	/**
	 * @param exact
	 *            the exact number of times the sub rule needs to be applied -1
	 *            means any number of times.
	 * 
	 */
	public MinMaxRule(Rule rule, int exact) {
		super(rule);
		this.setExact(exact);
	}

	/**
	 * @param min
	 *            the minimum times the sub rule needs to be applied -1 means
	 *            any number of times.
	 * @param max
	 *            the maximum times the sub rule needs to be applied -1 means
	 *            any number of times. if max is lower than min, then it will be
	 *            treated as though it is the same size as min.
	 * 
	 */
	public MinMaxRule(Rule rule, int min, int max) {
		super(rule);
		this.min = min;
		this.max = max;
	}

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {
		if (this.size() == 0) {
			if (min <= 0) {
				// By its nature we always pass even if we have nothing to
				// evaluate.
				decision.passed();
			} else {
				decision.failed("{} failed no rules to consider", this, min,
						max);
			}
			return;
		}

		for (int i = 0; i < min; i++) {
			RuleDecision subDecision = visitor.evaluate(rule,
					decision.getNextIndex());
			if (subDecision.hasPassed()) {
				/*
				 * If our subrule is non-collecting, if it passes once it will
				 * continue to pass and in the case of unlimited maximums we
				 * want to avoid the case of an infinite loop. Because it will
				 * always pass, we know that the minmaxrule will always pass. It
				 * is important to know that rules are presumed to be unchanging
				 * between evaluations, i.e. a rule that is ran against the same
				 * set of tokens will always return the same result,
				 * irrespective of what has come before.
				 */
				if (FunctionalRule.isNotCollecting(decision, subDecision)) {
					decision.passed();
					return;
				}
				decision.add(subDecision);
			} else {
				decision.failed("{} failed rule due to iteration: {}", this, i);
				return;
			}
		}
		considerMax(visitor, decision);
	}

	/**
	 * @param visitor
	 * @param decision
	 * @param unlimited
	 */
	protected void considerMax(RuleParser visitor, RuleDecision decision) {
		boolean unlimited = max < 0;
		for (int i = min; unlimited || (i < max); i++) {
			RuleDecision subDecision = visitor.evaluate(rule,
					decision.getNextIndex());
			if (subDecision.hasPassed()) {
				/*
				 * If our subrule is non-collecting, if it passes once it will
				 * continue to pass and in the case of unlimited maximums we
				 * want to avoid the case of an infinite loop. Because it will
				 * always pass, we know that the minmaxrule will always pass. It
				 * is important to know that rules are presumed to be unchanging
				 * between evaluations, i.e. a rule that is ran against the same
				 * set of tokens will always return the same result,
				 * irrespective of what has come before.
				 */
				if (FunctionalRule.isNotCollecting(decision, subDecision)) {
					decision.passed();
					return;
				}
				decision.add(subDecision);
			} else {
				decision.passed();
				return;
			}
		}
		decision.passed();
	}

	public void setExact(int exact) {
		this.min = exact;
		this.max = exact;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MinMaxRule [min=").append(min).append(", max=")
				.append(max).append("]");
		return builder.toString();
	}

}
