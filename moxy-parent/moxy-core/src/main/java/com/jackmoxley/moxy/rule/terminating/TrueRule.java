package com.jackmoxley.moxy.rule.terminating;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;

public class TrueRule extends TerminatingRule {

	private static final long serialVersionUID = 163168561429254410L;

	private static final TrueRule instance = new TrueRule();
	
	private TrueRule(){
		
	}
	
	public static TrueRule get(){
		return instance;
	}
	
	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		decision.passed();
	}

}
