package com.jackmoxley.moxy.rule.functional;

import java.util.Set;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.SymbolToken;

/**
 * LinkRules and SymbolRules are very similar in that they both delegate to
 * rules stored in the symbol map, the one difference being link rules adds its
 * tokens to its parents tokens. Whilst a symbol rule generates a new branch.
 * 
 * @author jack
 * 
 */
public abstract class SymbolRule extends FunctionalRule {

	private static final long serialVersionUID = 1L;

	protected String pointer;
	protected Boolean symbol = true;


	@Override
	protected boolean doSubRulesTerminate(Set<Rule> history) {
		// TODO Auto-generated method stub
		return false; // we don't actually know, so assume it isn't
	}

	public SymbolRule(String pointer) {
		super();
		this.pointer = pointer;
	}

	public SymbolRule(Boolean symbol, String pointer) {
		super();
		this.pointer = pointer;
		this.symbol = symbol;
	}

	public SymbolRule() {
		super();
	}

	protected abstract Rule getDelegate(RuleEvaluator evaluator);

	@Override
	public void consider(RuleEvaluator evaluator, RuleDecision decision) {
		Rule delegate = getDelegate(evaluator);
		if (delegate == null) {
			decision.failed("Rule {} does not exist", pointer);
			return;
		}
		RuleDecision subDecision = evaluator.evaluate(delegate,
				decision.getNextIndex());
		if(subDecision.hasPassed()){
			if (symbol) {
				SymbolToken token = new SymbolToken(pointer);
				token.addAll(subDecision.getTokens());
				decision.add(token, subDecision.getNextIndex());
				decision.passed();
			} else {
				decision.add(subDecision);
				decision.passed();
			}
		} else {
			decision.failed("PointerRule {} has failed", pointer);
		}
	}

	public String getPointer() {
		return pointer;
	}

	public void setPointer(String pointer) {
		this.pointer = pointer;
	}

	public boolean isSymbol() {
		return symbol;
	}

	public void setSymbol(boolean symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SymbolRule [pointer=").append(pointer)
				.append(", symbol=").append(symbol).append("]");
		return builder.toString();
	}

}
