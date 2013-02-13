package com.jackmoxley.moxy.rule;

import java.io.Serializable;
import java.util.Set;

public interface Rule extends Serializable{

	public boolean isTerminating(Set<Rule> history);
	public void consider(RuleEvaluator visitor, RuleDecision decision);
}
