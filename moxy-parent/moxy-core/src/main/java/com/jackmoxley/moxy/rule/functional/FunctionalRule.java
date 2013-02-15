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
import java.util.HashSet;
import java.util.Set;

import com.jackmoxley.moxy.rule.Rule;

public abstract class FunctionalRule extends AbstractList<Rule> implements Rule {

	private static final long serialVersionUID = -3106346677327869406L;

	protected abstract boolean doSubRulesTerminate(Set<Rule> history);

	@Override
	public boolean isTerminating(Set<Rule> history) {
		if (history == null) {
			history = new HashSet<Rule>();
		}
		if (history.contains(this)) {
			return false;
		}
		history.add(this);
		boolean terminating = doSubRulesTerminate(history);
		history.remove(this);
		return terminating;
	}

	@Override
	public boolean equals(Object obj) {
        return (this == obj);
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

}
