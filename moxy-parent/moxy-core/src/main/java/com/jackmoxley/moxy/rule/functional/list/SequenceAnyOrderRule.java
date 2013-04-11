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

import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.RuleParser;
import com.jackmoxley.moxy.rule.Rule;

@Beta
public class SequenceAnyOrderRule extends ListRule {

	private static final long serialVersionUID = 1L;

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {

		if (this.size() == 0) {
			decision.passed();
			return;
		}
		List<RuleDecision> decisions = considerSubList(visitor, decision.getNextIndex(), this);
		if(decisions != null){
			for(int i = decisions.size()-1;i >= 0;i--){
				decision.add(decisions.get(i));
			}
			decision.passed();
		} else {
			decision.failed("{} failed", this);
		}
	}

	public List<RuleDecision> considerSubList(RuleParser visitor,
			int nextIndex, List<Rule> subList) {
		RuleDecision subDecision;
		for (int i = 0; i < subList.size(); i++) {
			subDecision = visitor.evaluate(get(i), nextIndex);
			if (subDecision.hasPassed()) {
				List<RuleDecision> decisions;
				if (subList.size() <= 1) {
					decisions = new ArrayList<RuleDecision>(subList.size());
				} else {
					List<Rule> newSubList = new ArrayList<Rule>(subList);
					newSubList.remove(i);
					decisions = considerSubList(visitor,
							subDecision.getNextIndex(), newSubList);
				}
				if (decisions != null) {
					decisions.add(subDecision);
					return decisions;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SequenceRule [size=").append(size()).append("]");
		return builder.toString();
	}

}
