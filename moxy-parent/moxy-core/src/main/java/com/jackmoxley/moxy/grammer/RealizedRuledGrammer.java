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

import java.util.LinkedHashMap;
import java.util.Map;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;

@Beta
public class RealizedRuledGrammer extends RuledGrammer implements Grammer {

	private static final long serialVersionUID = 1L;

	private Map<String,String> textualSyntax;
	private String start;

	public RealizedRuledGrammer() {
		super();
		this.ruleMap = new LinkedHashMap<>();
		this.textualSyntax  = new LinkedHashMap<>(); 
	}

	public void setStart(String start) {
		this.start = start;
	}

	@Override
	protected Rule getStart() {
		return getRuleMap().get(start);
	}

	protected void put(String ruleName, Rule rule) {
		getRuleMap().put(ruleName, rule);
	}
	

	protected void putSyntax(String ruleName, String textualSyntax) {
		this.textualSyntax.put(ruleName, textualSyntax);
	}
	
	
	public Map<String, String> getSyntaxMap() {
		return textualSyntax;
	}

	public void setTextualSyntax(Map<String, String> textualSyntax) {
		this.textualSyntax = textualSyntax;
	}

	public String getTextualSyntax(String ruleName){
		return this.textualSyntax.get(ruleName);
	}
	
	public Rule getRule(String ruleName){
		return this.getRuleMap().get(ruleName);
	}
}
