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
import com.jackmoxley.moxy.rule.RuleEvaluator;

@Beta
public class MinMaxRule extends SingleRule {

	private static final long serialVersionUID = 1L;

	protected int min;
	protected int max;

	public MinMaxRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param min
	 * @param max
	 */
	public MinMaxRule(int min, int max, Rule rule) {
		super(rule);
		this.min = min;
		this.max = max;
	}

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		if (this.size() == 0) {
			if (min == 0) {
				// By its nature we always pass even if we have nothing to
				// evaluate.
				decision.passed();
			} else {
				decision.failed(
						"{} failed no rules to consider",this, min,
						max);
			}
			return;
		}

		for (int i = 0; i < min; i++) {
			RuleDecision subDecision = visitor.evaluate(rule,
					decision.getNextIndex());
			if (subDecision.hasPassed()) {
				decision.add(subDecision);
			} else {
				decision.failed(
						"{} failed rule due to iteration: {}",this, i);
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
	protected void considerMax(RuleEvaluator visitor, RuleDecision decision) {
		boolean unlimited = max < 0 ;
		for (int i = min; unlimited || (i < max); i++) {
			RuleDecision subDecision = visitor.evaluate(rule, decision.getNextIndex());
			if (subDecision.hasPassed()) {
				decision.add(subDecision);
			} else {
				decision.passed();
				return;
			}
		}
		decision.passed();
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
