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
import com.jackmoxley.moxy.rule.RuleParser;

/**
 * Delegate rules allow us to delegate to an existing rule, that we have defined
 * elsewhere, their main use is envisaged for labeling parts of the parsed
 * syntax to help us create a tree of tokens. As such by default symbol is set
 * to true.
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
public class DelegateRule extends SymbolRule {

	private static final long serialVersionUID = 1L;

	public DelegateRule(String pointer, Rule rule) {
		super(true, pointer, rule);
	}

	public DelegateRule() {
		super();
		symbol = true;
	}

	protected Rule getDelegate(RuleParser evaluator) {
		return rule;
	}
}
