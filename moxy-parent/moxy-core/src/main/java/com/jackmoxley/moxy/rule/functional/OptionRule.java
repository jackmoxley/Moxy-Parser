package com.jackmoxley.moxy.rule.functional;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;

public class OptionRule extends RuleList {

	private static final long serialVersionUID = 1L;

	private boolean greedy = true;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		if(greedy){
			visitGreedy(visitor, decision);
		} else {
			visitLazy(visitor, decision);
		}
	}
	protected void visitLazy(RuleEvaluator visitor, RuleDecision decision) {
		if(this.size() == 0){
			decision.failed("OptionRule failed no rules to consider");
			return;
		}
		RuleDecision finalDecision;
		for (Rule rule : this) {
			finalDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (finalDecision.hasPassed()) {
				decision.add(finalDecision);
				decision.passed();
				return;
			}
		}
		decision.failed("OptionRule failed");
	}

	protected void visitGreedy(RuleEvaluator visitor, RuleDecision decision) {
		RuleDecision finalDecision = null;
		
		RuleDecision subDecision;
		for (Rule rule : this) {
			subDecision = visitor.evaluate(rule, decision.getStartIndex());
			if (subDecision.hasPassed()) {
				if (finalDecision == null
						|| finalDecision.getNextIndex() < subDecision
								.getNextIndex()) {
					finalDecision = subDecision;
				}
			}
		}
		if (finalDecision != null) {
			decision.passed();
			decision.add(finalDecision);
		} else {
			decision.failed("OptionRule failed");
		}
	}

	public boolean isGreedy() {
		return greedy;
	}

	public void setGreedy(boolean greedy) {
		this.greedy = greedy;
	}
	@Override
	public String toString() {
		return "OptionRule "+this.size();
	}
}
