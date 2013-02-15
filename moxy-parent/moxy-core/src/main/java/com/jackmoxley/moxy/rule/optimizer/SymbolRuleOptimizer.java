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

import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.DelegateRule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;
import com.jackmoxley.moxy.rule.functional.PointerRule;
import com.jackmoxley.moxy.rule.functional.RuleList;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
public class SymbolRuleOptimizer implements Optimizer{

	public int visitRule(Grammer grammer, FunctionalRule rule) {
		int rulesOptimized = 0;
		if (rule instanceof RuleList) {
			RuleList ruleList = (RuleList) rule;
			for (int i = 0; i < ruleList.size(); i++) {
				Rule innerRule = ruleList.get(i);
				if (innerRule instanceof PointerRule) {
					PointerRule pointer = (PointerRule)innerRule;
					if(pointer.isSymbol()){
						ruleList.set(i, new DelegateRule(true, pointer.getPointer(), grammer.getRuleMap().get(pointer.getPointer())));
						rulesOptimized++;
					} else {
						ruleList.set(i, grammer.getRuleMap().get(pointer.getPointer()));
						rulesOptimized++;
					}
					i--;
				} 
				if (innerRule instanceof DelegateRule) {
					DelegateRule delegate = (DelegateRule)innerRule;
					if(!delegate.isSymbol()){
						Rule childRule = delegate.getRule();
						if(childRule == null){
							childRule = grammer.getRuleMap().get(delegate.getPointer());
						}
						ruleList.set(i, delegate.getRule());
						rulesOptimized++;
						i--;
					}
				}
			}
		}
		return rulesOptimized;
	}

}
