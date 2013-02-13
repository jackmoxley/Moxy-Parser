package com.jackmoxley.moxy.rule.terminating;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;

public class FalseRule extends TerminatingRule {

	private static final long serialVersionUID = 2838775015098939760L;

	private static final FalseRule instance = new FalseRule();
	
	private FalseRule(){
		
	}
	public static FalseRule get(){
		return instance;
	}
	
	
	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		decision.failed("FailingRule failed");
	}
	
}
