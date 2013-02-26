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

@Beta
public class OptionalRule extends SingleRule {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public OptionalRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rule
	 */
	public OptionalRule(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {
		if (this.size() == 0) {
			// By its nature we always pass even if we have nothing to evaluate.
			decision.passed(); 
			return;
		}
		RuleDecision finalDecision = visitor.evaluate(rule,
				decision.getStartIndex());
		if (finalDecision.hasPassed()) {
			decision.add(finalDecision);
		}
		decision.passed();
	}

	@Override
	public String toString() {
		return "OptionalRule";
	}
}
