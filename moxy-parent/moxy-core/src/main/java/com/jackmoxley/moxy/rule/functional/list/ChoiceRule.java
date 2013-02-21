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

	private boolean greedy = true;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		if(greedy){
			visitGreedy(visitor, decision);
		} else {
			visitLazy(visitor, decision);
		}
	}
	protected void visitLazy(RuleEvaluator visitor, RuleDecision decision) {
		if(this.size() == 0){
			decision.failed("{} failed no rules to consider",this);
			return;
		}
		RuleDecision finalDecision;
		for (Rule rule : this) {
			finalDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (finalDecision.hasPassed()) {
				decision.add(finalDecision);
				decision.passed();
				return;
			}
		}
		decision.failed("{} failed",this);
	}

	protected void visitGreedy(RuleEvaluator visitor, RuleDecision decision) {
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
		if (finalDecision != null) {
			decision.passed();
			decision.add(finalDecision);
		} else {
			decision.failed("ChoiceRule failed");
		}
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
		builder.append("ChoiceRule [size=").append(size()).append("]");
		return builder.toString();
	}
}
