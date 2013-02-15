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
package com.moxley.moxy.rule.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.PointerRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

@Beta
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
