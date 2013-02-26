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
import com.jackmoxley.moxy.parser.RuleParser;
import com.jackmoxley.moxy.rule.Rule;

/**
 * Pointer rules allow us to point to directly to an already defined ruletree in
 * our grammar. Because our grammars can be a collection from multiple places,
 * this allows us to point to rules that may not of been created yet. The main
 * use of this rule is to allow us to reuse already defined rules, rather than
 * creating very length and often complicated rule graphs.
 * 
 * It should be pointed out that the fetched rule is not cached.
 * 
 * DelegateRules and PointerRules are very similar in that they both delegate to
 * rules however DelegateRules have a rule already defined, and pointer rules
 * fetch their's from the grammar each time.
 * 
 * 
 * @author jack
 * 
 */
@Beta
public class PointerRule extends SymbolRule {

	private static final long serialVersionUID = 1L;

	public PointerRule(Boolean symbol, String pointer) {
		super(symbol, pointer);
	}

	public PointerRule() {
		super();
	}

	protected Rule getDelegate(RuleParser evaluator) {
		return evaluator.ruleForName(pointer);
	}

	public int size() {
		return 0;
	}

	public Rule get(int index) {
		throw new UnsupportedOperationException();
	}

}
