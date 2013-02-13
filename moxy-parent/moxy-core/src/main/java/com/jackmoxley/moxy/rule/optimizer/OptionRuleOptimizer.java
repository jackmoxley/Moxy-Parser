package com.jackmoxley.moxy.rule.optimizer;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.OptionRule;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
public class OptionRuleOptimizer extends AbstractRuleListOptimizer<OptionRule> implements Optimizer{

	@Override
	protected OptionRule asInstance(Rule rule) {
		return rule instanceof OptionRule ? (OptionRule)rule : null;
	}



}
