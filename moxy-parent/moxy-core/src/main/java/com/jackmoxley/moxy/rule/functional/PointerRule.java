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
