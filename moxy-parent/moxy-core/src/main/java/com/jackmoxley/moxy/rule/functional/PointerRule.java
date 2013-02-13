package com.jackmoxley.moxy.rule.functional;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleEvaluator;

/**
 * LinkRules and SymbolRules are very similar in that they both delegate to
 * rules stored in the symbol map, the one difference being link rules adds its
 * tokens to its parents tokens. Whilst a symbol rule generates a new branch.
 * 
 * @author jack
 * 
 */
public class PointerRule extends SymbolRule {

	private static final long serialVersionUID = 1L;

	public PointerRule(Boolean symbol, String pointer) {
		super(symbol, pointer);
	}

	public PointerRule() {
		super();
	}

	protected Rule getDelegate(RuleEvaluator evaluator) {
		return evaluator.ruleForName(pointer);
	}

	public int size() {
		return 0;
	}

	public Rule get(int index) {
		throw new UnsupportedOperationException();
	}

}
