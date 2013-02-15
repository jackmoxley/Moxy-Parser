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
package com.jackmoxley.moxy.rule.functional;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;

@Beta
public class SequenceRule extends RuleList  {

	private static final long serialVersionUID = 1L;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {

		if(this.size() == 0){
			decision.failed("SequentialRule failed no rules to consider");
			return;
		}
		RuleDecision subDecision;
		for (int  i = 0; i < this.size();i++) {
			subDecision = visitor.evaluate(get(i), decision.getNextIndex());
			if (subDecision.hasPassed()) {
				decision.add(subDecision);
			} else {
				decision.failed("SequentialRule failed rule due to SubRule: {}",i);
				return;
			}
		}
		decision.passed();
	}

	@Override
	public String toString() {
		return "SequenceRule";
	}

}
