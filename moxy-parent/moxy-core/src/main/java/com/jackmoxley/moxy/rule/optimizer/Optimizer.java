package com.jackmoxley.moxy.rule.optimizer;

import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

public interface Optimizer {
	int visitRule(Grammer grammer, FunctionalRule parent); // we want to drive off this one.
}
