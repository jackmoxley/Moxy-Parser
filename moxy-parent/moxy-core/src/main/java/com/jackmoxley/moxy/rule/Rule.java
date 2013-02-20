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
package com.jackmoxley.moxy.rule;

import java.io.Serializable;
import java.util.Set;

import com.jackmoxley.meta.Beta;

@Beta
public interface Rule extends Serializable{

	public boolean isNotCircular(Set<Rule> history);
	
	/**
	 * Storing the result in the provided decision object,
	 * determine if the rule should pass or not.
	 * @param visitor 
	 * @param decision
	 */
	public void consider(RuleEvaluator visitor, RuleDecision decision);
	
	public void accept(RuleVisitor visitor);
}
