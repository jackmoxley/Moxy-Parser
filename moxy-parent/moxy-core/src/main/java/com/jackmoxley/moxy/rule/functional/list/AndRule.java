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
package com.jackmoxley.moxy.rule.functional.list;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleParser;

/**
 * The and rule executes against every rule and if they all pass, it picks the
 * longest, shortest or first.
 * 
 * @author jack
 * 
 */
@Beta
public class AndRule extends LogicalListRule {

	private static final long serialVersionUID = 1L;

	protected RuleDecision considerFirst(RuleParser visitor,
			RuleDecision decision) {
		RuleDecision finalDecision = null;

		RuleDecision subDecision;
		for (Rule rule : this) {
			subDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (subDecision.hasPassed()) {
				if (finalDecision == null) {
					finalDecision = subDecision;
				}
			} else {
				return null;
			}
		}
		return finalDecision;
	}

	protected RuleDecision considerShortest(RuleParser visitor,
			RuleDecision decision) {
		RuleDecision finalDecision = null;

		RuleDecision subDecision;
		for (Rule rule : this) {
			subDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (subDecision.hasPassed()) {
				if (finalDecision == null
						|| finalDecision.getNextIndex() > subDecision
								.getNextIndex()) {
					finalDecision = subDecision;
				}
			} else {
				return null;
			}
		}
		return finalDecision;
	}

	protected RuleDecision considerLongest(RuleParser visitor,
			RuleDecision decision) {
		RuleDecision finalDecision = null;

		RuleDecision subDecision;
		for (Rule rule : this) {
			subDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (subDecision.hasPassed()) {
				if (finalDecision == null
						|| finalDecision.getNextIndex() < subDecision
								.getNextIndex()) {
					finalDecision = subDecision;
				}
			} else {
				return null;
			}
		}
		return finalDecision;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AndRule [type=").append(type).append(", size=")
				.append(size()).append("]");
		return builder.toString();
	}

}
