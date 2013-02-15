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
package com.jackmoxley.moxy.rule.optimizer;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;
import com.jackmoxley.moxy.rule.functional.RuleList;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
@Beta
public abstract class AbstractRuleListOptimizer <RL extends RuleList> implements Optimizer{


	/**
	 * Returns the correct instance or null
	 * Couples as a converter and a check to see if it is the right instance.
	 * 
	 * @param rule
	 * @return the converted rule or null
	 */
	protected abstract RL asInstance(Rule rule);

	@Override
	public int visitRule(Grammer grammer, FunctionalRule parent) {
		int rulesOptimized = 0;
		boolean parentIsInstance = asInstance(parent) != null;
		for(int i =0;i < parent.size();i++){
			RL rule = asInstance(parent.get(i));
			if (rule != null) {
				if(rule.size() == 0){
					parent.remove(i--);
					rulesOptimized++;
				} else if(rule.size() == 1){
					parent.set(i, rule.get(0));
					rulesOptimized++;
				} else if(parentIsInstance){
					parent.remove(i);
					parent.addAll(i--, rule);
					rulesOptimized++;
				} 
			}
		}
		return rulesOptimized;
	}

}
