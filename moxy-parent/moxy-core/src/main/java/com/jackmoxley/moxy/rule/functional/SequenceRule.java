package com.jackmoxley.moxy.rule.functional;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;

public class SequenceRule extends RuleList  {

	private static final long serialVersionUID = 1L;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {

		if(this.size() == 0){
			decision.failed("SequentialRule failed no rules to consider");
			return;
		}
		RuleDecision subDecision;
		for (int  i = 0; i < this.size();i++) {
			subDecision = visitor.evaluate(get(i), decision.getNextIndex());
			if (subDecision.hasPassed()) {
				decision.add(subDecision);
			} else {
				decision.failed("SequentialRule failed rule due to SubRule: {}",i);
				return;
			}
		}
		decision.passed();
	}

	@Override
	public String toString() {
		return "SequenceRule";
	}

}
