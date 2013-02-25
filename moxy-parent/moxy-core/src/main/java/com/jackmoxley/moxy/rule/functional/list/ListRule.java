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
package com.jackmoxley.moxy.rule.functional.list;

import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleVisitor;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

@Beta
public abstract class ListRule extends FunctionalRule {

	private static final long serialVersionUID = -2601327856351809245L;
	private final List<Rule> rules = new ArrayList<Rule>();

	@Override
	public void accept(RuleVisitor visitor) {
		visitor.visit(this);
		for (Rule rule : this) {
			rule.accept(visitor);
		}
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
