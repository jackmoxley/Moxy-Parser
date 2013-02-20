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
package com.jackmoxley.moxy.optimizer;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;
import com.jackmoxley.moxy.rule.functional.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.StringRule;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
@Beta
public class StringRuleOptimizer implements Optimizer{

	public int visitRule(Grammar grammer, FunctionalRule rule) {
		int rulesOptimized = 0;
		if (rule instanceof SequenceRule) {
			SequenceRule ruleList = (SequenceRule) rule;
			int combinedCount = 0;
			int start = 0;
			boolean finished = false;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ruleList.size(); i++) {
				Rule innerRule = ruleList.get(i);
				if(innerRule instanceof CharacterRule){
					CharacterRule charRule = (CharacterRule)innerRule;
					if(0 == combinedCount++) {
						start = i;
					}
					sb.append(charRule.getCharacter());
					 finished = ((i+1) == ruleList.size());
				} else if(innerRule instanceof StringRule){
					StringRule stringRule = (StringRule)innerRule;
					if(0 == combinedCount++) {
						start = i;
					}
					 finished = ((i+1) == ruleList.size());
					sb.append(stringRule.getValue());
				} else {
					finished = true;
				}
				if(finished) {
					if(combinedCount > 1){
						for(int c = 0;c < combinedCount;c++){
							ruleList.remove(start);
						}
						ruleList.add(start,new StringRule(sb.toString()));
						i = start;
						rulesOptimized += (combinedCount-1); // we merge combinedCount into one rule.
					}
					if(combinedCount > 0){
						combinedCount = 0;
						sb = new StringBuilder();
					}
				} 
				
			}
			
		}
		return rulesOptimized;
	}

}
