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

import java.util.AbstractList;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;

@Beta
public abstract class FunctionalRule extends AbstractList<Rule> implements Rule {

	private static final long serialVersionUID = -3106346677327869406L;

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}


	/**
	 * determines if the subDecision has collected any tokens, this is to help
	 * us detect cycles in subrules that are repeatadly evaluated.
	 * 
	 * @param decision
	 * @param subDecision
	 * @return
	 */
	public static boolean isNotCollecting(RuleDecision decision,
			RuleDecision subDecision) {

		return subDecision.getNextIndex() == decision.getStartIndex();
	}

}
