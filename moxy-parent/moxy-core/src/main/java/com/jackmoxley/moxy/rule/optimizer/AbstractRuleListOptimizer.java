package com.jackmoxley.moxy.rule.optimizer;

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
