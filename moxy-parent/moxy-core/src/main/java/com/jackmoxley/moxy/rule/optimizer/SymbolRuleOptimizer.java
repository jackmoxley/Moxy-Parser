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
