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
package com.jackmoxley.moxy.rule.functional;

import java.util.Set;

import com.jackmoxley.meta.Beta;
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
@Beta
public class DelegateRule extends SymbolRule {

	private static final long serialVersionUID = 1L;
	protected Rule rule;
	
	public DelegateRule(Boolean symbol, String pointer, Rule rule) {
		super(symbol, pointer);
		this.rule = rule;
	}

	
	public DelegateRule() {
		super();
	}
	@Override
	protected boolean doSubRulesTerminate(Set<Rule> history) {
		if(rule == null){
			return true;
		}
		return rule.isNotCircular(history);
	}
	

	protected Rule getDelegate(RuleEvaluator evaluator) {
		if(rule == null){
			rule = evaluator.ruleForName(pointer);
		}
		return rule;
	}


	public Rule getRule() {
		return rule;
	}


	public void setRule(Rule rule) {
		this.rule = rule;
	}


	@Override
	public Rule get(int index) {
		if(index == 0){
			if(rule == null){
				throw new IndexOutOfBoundsException();
			}
			return rule;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}


	@Override
	public Rule set(int index, Rule element) {
		if(index == 0){
			Rule toReturn = rule;
			rule = element;
			return toReturn;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}


	@Override
	public void add(int index, Rule element) {
		if(index == 0){
			rule = element;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}


	@Override
	public Rule remove(int index) {
		if(index == 0){
			Rule toReturn = rule;
			rule = null;
			return toReturn;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}


	@Override
	public int size() {
		// TODO Auto-generated method stub
		return rule == null ? 0 : 1;
	}

}
