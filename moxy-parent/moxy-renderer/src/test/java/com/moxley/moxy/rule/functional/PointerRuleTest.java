package com.moxley.moxy.rule.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.PointerRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

public class PointerRuleTest {

	
	@Test
	public void testHappyPath_symbol_pass() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new LinkedHashMap<String, Rule>();
		links.put("passed", TrueRule.get());
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(true);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	
	
	@Test
	public void testUnHappyPath_symbol_fail() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new HashMap<String, Rule>();
		links.put("failed", FalseRule.get());
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(true);
		rule.setPointer("failed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_symbol_null() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new HashMap<String, Rule>();
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(true);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	
	@Test
	public void testHappyPath_link_pass() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new HashMap<String, Rule>();
		links.put("passed", TrueRule.get());
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(false);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	
	
	@Test
	public void testUnHappyPath_link_fail() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new HashMap<String, Rule>();
		links.put("failed", FalseRule.get());
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(false);
		rule.setPointer("failed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_link_null() {
		RuleEvaluator visitor = new RuleEvaluator();
		Map<String,Rule> links  = new HashMap<String, Rule>();
		visitor.setLinks(links);
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(false);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
}
