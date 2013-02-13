package com.jackmoxley.moxy.rule.functional;

import java.util.AbstractList;
import java.util.HashSet;
import java.util.Set;

import com.jackmoxley.moxy.rule.Rule;

public abstract class FunctionalRule extends AbstractList<Rule> implements Rule {

	private static final long serialVersionUID = -3106346677327869406L;

	protected abstract boolean doSubRulesTerminate(Set<Rule> history);

	@Override
	public boolean isTerminating(Set<Rule> history) {
		if (history == null) {
			history = new HashSet<Rule>();
		}
		if (history.contains(this)) {
			return false;
		}
		history.add(this);
		boolean terminating = doSubRulesTerminate(history);
		history.remove(this);
		return terminating;
	}

	@Override
	public boolean equals(Object obj) {
        return (this == obj);
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

}
