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

import com.jackmoxley.meta.Beta;

/**
 * Rule is the interface that defines the contract for a parsing rule. On top of
 * the methods that need to be implemented, we have various assumptions, see the
 * methods for these assumptions.
 * 
 * @author jack
 * 
 */
@Beta
public interface Rule extends Serializable {

	/**
	 * Storing the result in the provided decision object, determine if the rule
	 * should pass or not. Implementation of the method must follow the
	 * following rules.
	 * 
	 * 1. A rule that is run against the same set of tokens will always return
	 * the same result, irrespective of what has come before. That includes the
	 * next index to execute against, the tokens returned and the decision.
	 * 
	 * 2. A rule must determine whether it has passed or failed at the very end
	 * of its execution of its consider method. This is so that if we need to
	 * consider any sub rules, then we don't mess up cyclic detection.
	 * 
	 * 3. When considering rules we should hand off to the RuleParser via its
	 * evaluate method, rather than directly calling consider. This is so we can
	 * use the power of its ability to collect history, and detect cycles.
	 * 
	 * 4. If a rule could cause a cycle that is NOT due to its connections in
	 * the rule graph, i.e. an infinite while/for loop, see MinMaxRule for an
	 * example of this. Then the loop must be detected as early as possible and
	 * exited out of in a safe manner.
	 * 
	 * 5. Although exceptions are handled in a safe manner, it is inadvisable to
	 * throw an Exception to fail a rule if possible, instead just fail the
	 * rule, via the decision.
	 * 
	 * 6. Rules are failed by calling passed() or failed() on the passed in
	 * decision. It is up to the developer to decide whether to add tokens or
	 * not to it. If you wish to include a subdecision's tokens and index to
	 * this one, simple call add on the decision.
	 * 
	 * @param visitor
	 * @param decision
	 */
	public void consider(RuleParser visitor, RuleDecision decision);

	public void accept(RuleVisitor visitor);

}
