/**
 * Copyright (C) 2013  John Orlando Keleshian Moxley
 * 
 * Unless otherwise stated by the license provided by the copyright holder.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jackmoxley.moxy.rule.functional.symbol;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.single.SingleRule;
import com.jackmoxley.moxy.token.SymbolToken;

/**
 * Symbol rules allow us to delegate rule behaviour to an already definined
 * RuleTree within our grammer, or via rule passed into the constructor. As the
 * name implies they are merely a symbol and don't make any decisions and simply
 * point to another rule and are effectively transparent in the parsing
 * algorithim, bar the fact that the history of its decisions is retained.
 * 
 * However if the instance variable symbol is set to true, rather than tokens
 * being copied to the end of the parents tokens, we effectively create a new
 * branch called a symbol token and add all child rules tokens to that, and then
 * add the symbol token to the parent. Thus creating a tree of tokens.
 * 
 * @author jack
 * 
 */
@Beta
public abstract class SymbolRule extends SingleRule {

	private static final long serialVersionUID = 1L;

	protected String pointer;
	protected Boolean symbol = false;

	public SymbolRule() {
		super();
	}
	
	public SymbolRule(Rule rule) {
		super(rule);
	}

	public SymbolRule(String pointer) {
		super();
		this.pointer = pointer;
	}
	
	public SymbolRule(String pointer, Rule rule) {
		super(rule);
		this.pointer = pointer;
	}

	public SymbolRule(Boolean symbol, String pointer) {
		super();
		this.pointer = pointer;
		this.symbol = symbol;
	}
	

	public SymbolRule(Boolean symbol, String pointer, Rule rule) {
		super(rule);
		this.pointer = pointer;
		this.symbol = symbol;
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
