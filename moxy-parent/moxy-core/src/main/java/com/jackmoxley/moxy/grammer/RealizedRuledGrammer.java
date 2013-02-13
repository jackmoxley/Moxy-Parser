package com.jackmoxley.moxy.grammer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jackmoxley.moxy.rule.Rule;

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
