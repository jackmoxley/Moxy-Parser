package com.jackmoxley.moxy.rule.optimizer;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.SequenceRule;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
public class SequenceRuleOptimizer extends AbstractRuleListOptimizer<SequenceRule> implements Optimizer {

	@Override
	protected SequenceRule asInstance(Rule rule) {
		return rule instanceof SequenceRule ? (SequenceRule)rule : null;
	}


}
