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

import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.RuleParser;

/**
 * @author jack
 *
 */
public abstract class LogicalListRule extends ListRule {

	private static final long serialVersionUID = 1L;
	
	public enum Type {
		AcceptFirst, AcceptShortest, AcceptLongest
	}

	protected Type type = Type.AcceptFirst;

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {

		if (this.size() == 0) {
			decision.failed("{} failed no rules to consider", this);
			return;
		}

		RuleDecision finalDecision = null;
		switch (type) {
		case AcceptShortest:
			finalDecision = considerShortest(visitor, decision);
			break;
		case AcceptLongest:
			finalDecision = considerLongest(visitor, decision);
			break;
		case AcceptFirst:
		default:
			finalDecision = considerFirst(visitor, decision);
			break;
		}

		if (finalDecision != null) {
			decision.add(finalDecision);
			decision.passed();
		} else {
			decision.failed("{} failed", this);
		}
	}

	protected abstract RuleDecision considerFirst(RuleParser visitor,
			RuleDecision decision) ;

	protected abstract RuleDecision considerShortest(RuleParser visitor,
			RuleDecision decision) ;

	protected abstract RuleDecision considerLongest(RuleParser visitor,
			RuleDecision decision) ;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
