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
package com.jackmoxley.moxy.rule.functional.single;

import java.util.Set;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleVisitor;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;

/**
 * Single Rule is for functional rules that only have one rule to act against,
 * by providing list like methods.
 * 
 * @author jack
 * 
 */
@Beta
public abstract class SingleRule extends FunctionalRule {

	private static final long serialVersionUID = 1L;
	protected Rule rule;

	public SingleRule() {
		super();
	}

	public SingleRule(Rule rule) {
		this.rule = rule;
	}
	
	@Override
	public void accept(RuleVisitor visitor) {
		visitor.visit(this);
		rule.accept(visitor);
	}

	@Override
	protected boolean doSubRulesTerminate(Set<Rule> history) {
		if (rule == null) {
			return true;
		}
		return rule.isNotCircular(history);
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@Override
	public Rule get(int index) {
		if (index == 0) {
			if (rule == null) {
				throw new IndexOutOfBoundsException();
			}
			return rule;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public Rule set(int index, Rule element) {
		if (index == 0) {
			Rule toReturn = rule;
			rule = element;
			return toReturn;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void add(int index, Rule element) {
		if (index == 0) {
			rule = element;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public Rule remove(int index) {
		if (index == 0) {
			Rule toReturn = rule;
			rule = null;
			return toReturn;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int size() {
		return rule == null ? 0 : 1;
	}

}
