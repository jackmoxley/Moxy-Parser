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
import com.jackmoxley.moxy.rule.RuleVisitor;

@Beta
public class UntilRule extends MinMaxRule {

	private static final long serialVersionUID = 1L;

	private Rule until;
	private boolean include;
	private boolean greedy;

	public UntilRule() {
		super();
	}

	/**
	 * @param min
	 * @param max
	 */
	public UntilRule(int min, int max, Rule rule, Rule until, boolean include, boolean greedy) {
		super(min, max, rule);
		this.until = until;
		this.include = include;
		this.greedy = greedy;
	}
	
	@Override
	public void accept(RuleVisitor visitor) {
		super.accept(visitor);
		//TODO do we only do this if the rule is included?
		until.accept(visitor);
	}
	
	protected void considerMax(RuleEvaluator visitor, RuleDecision decision) {
		boolean unlimited = max < 0 ;
		for (int i = min; (unlimited || (i < max)) ; i++) {
			if((!greedy) && considerUntil(visitor,decision)){
				decision.passed();
				return;
			}
			RuleDecision subDecision = visitor.evaluate(rule, decision.getNextIndex());
			if (subDecision.hasPassed()) {
				decision.add(subDecision);
			} else {
				decision.passed();
				break;
			}
		}
		if(considerUntil(visitor,decision)){
			decision.passed();
		} else {
			decision.failed("{} has failed as until rule was not achieved.",this);
		}
	}
	
	protected boolean considerUntil(RuleEvaluator visitor, RuleDecision decision){

		RuleDecision subDecision = visitor.evaluate(until,
				decision.getNextIndex());
		if (subDecision.hasPassed()) {
			if(include){
				decision.add(subDecision);
			}
			return true;
		} 
		return false;
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
