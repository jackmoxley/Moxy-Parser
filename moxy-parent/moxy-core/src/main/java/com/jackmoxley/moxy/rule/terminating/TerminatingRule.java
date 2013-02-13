package com.jackmoxley.moxy.rule.terminating;

import java.util.Set;

import com.jackmoxley.moxy.rule.Rule;

public abstract class TerminatingRule implements Rule {

	private static final long serialVersionUID = 2846424087976600923L;

	@Override
	public boolean isTerminating(Set<Rule> history) {
		return true;
	}

}
