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
package com.jackmoxley.moxy.grammer;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

import com.jackmoxley.moxy.rule.Rule;

/**
 * @author jack
 *
 */
public class RuleGraph extends AbstractList<Rule> implements Serializable, List<Rule>{

	private static final long serialVersionUID = -7247252355309117368L;
	
	private Rule rule;
	private final String name;
	private final String syntax;
	
	/**
	 * @param rule
	 */
	public RuleGraph(Rule rule) {
		this(rule,new StringBuilder("Rule:").append(rule.hashCode()).toString(),"");
	}
	
	/**
	 * @param rule
	 * @param name
	 */
	public RuleGraph(Rule rule, String name) {
		this(rule,name,"");
	}
	
	/**
	 * @param rule
	 * @param name
	 * @param syntax
	 */
	public RuleGraph(Rule rule, String name, String syntax) {
		super();
		this.rule = rule;
		this.name = name;
		this.syntax = syntax;
	}
	
	public Rule getRule() {
		return rule;
	}
	public String getName() {
		return name;
	}
	public String getSyntax() {
		return syntax;
	}

	@Override
	public String toString() {
		return "RuleGraph [name=" + name + ", rule=" + rule + ", syntax="
				+ syntax + "]";
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public Rule get(int index) {
		return rule;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return 1;
	}

	@Override
	public Rule set(int index, Rule element) {
		Rule old = this.rule;
		this.rule = element;
		return old;
	}

	@Override
	public Rule remove(int index) {
		Rule old = this.rule;
		this.rule = null;
		return old;
	}

}
