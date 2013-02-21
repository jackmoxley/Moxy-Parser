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
import com.jackmoxley.moxy.rule.RuleEvaluator;

@Beta
public class ChoiceRule extends ListRule {

	private static final long serialVersionUID = 1L;

	public enum Type {
		Lazy, Shortest, Longest
	}

	private Type type = Type.Lazy;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {

		if (this.size() == 0) {
			decision.failed("{} failed no rules to consider", this);
			return;
		}

		RuleDecision finalDecision = null;
		switch (type) {
		case Shortest:
			finalDecision = considerShortest(visitor, decision);
			break;
		case Longest:
			finalDecision = considerLongest(visitor, decision);
			break;
		case Lazy:
		default:
			finalDecision = considerLazy(visitor, decision);
			break;
		}

		if (finalDecision != null) {
			decision.add(finalDecision);
			decision.passed();
		} else {
			decision.failed("{} failed", this);
		}
	}

	protected RuleDecision considerLazy(RuleEvaluator visitor,
			RuleDecision decision) {
		RuleDecision finalDecision;
		for (Rule rule : this) {
			finalDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (finalDecision.hasPassed()) {
				return finalDecision;
			}
		}
		return null;
	}

	protected RuleDecision considerShortest(RuleEvaluator visitor,
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
			}
		}
		return finalDecision;
	}

	protected RuleDecision considerLongest(RuleEvaluator visitor,
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
			}
		}
		return finalDecision;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChoiceRule [type=").append(type).append(", size=")
				.append(size()).append("]");
		return builder.toString();
	}

}
