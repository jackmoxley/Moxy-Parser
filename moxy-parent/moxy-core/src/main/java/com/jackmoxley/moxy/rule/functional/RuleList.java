package com.jackmoxley.moxy.rule.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jackmoxley.moxy.rule.Rule;

public abstract class RuleList extends FunctionalRule{

	private static final long serialVersionUID = -2601327856351809245L;
	private final List<Rule> rules = new ArrayList<Rule>();

	@Override
	protected boolean doSubRulesTerminate(Set<Rule> history) {
		for(Rule rule: rules) {
			if(!rule.isTerminating(history)){
				return false;
			}
		}
		return true;
	}

	public int size() {
		return rules.size();
	}

	public Rule get(int index) {
		return rules.get(index);
	}

	public Rule set(int index, Rule element) {
		return rules.set(index, element);
	}

	public void add(int index, Rule element) {
		rules.add(index, element);
	}

	public Rule remove(int index) {
		return rules.remove(index);
	}
}
