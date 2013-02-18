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

import com.jackmoxley.moxy.rule.Rule;

/**
 * @author jack
 *
 */
public class RuleTree implements Serializable {

	private static final long serialVersionUID = -7247252355309117368L;
	
	private final Rule rule;
	private final String name;
	private final String syntax;
	
	/**
	 * @param rule
	 */
	public RuleTree(Rule rule) {
		this(rule,new StringBuilder("Rule:").append(rule.hashCode()).toString(),"");
	}
	
	/**
	 * @param rule
	 * @param name
	 */
	public RuleTree(Rule rule, String name) {
		this(rule,name,"");
	}
	
	/**
	 * @param rule
	 * @param name
	 * @param syntax
	 */
	public RuleTree(Rule rule, String name, String syntax) {
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
		return "RuleTree [name=" + name + ", rule=" + rule + ", syntax="
				+ syntax + "]";
	}

}
