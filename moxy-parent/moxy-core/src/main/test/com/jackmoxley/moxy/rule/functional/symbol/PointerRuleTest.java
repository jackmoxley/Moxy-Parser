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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuledGrammar;
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.SimpleRuleParser;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.functional.symbol.PointerRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

@Beta
public class PointerRuleTest {

	
	@Test
	public void testHappyPath_symbol_pass() {
		RuledGrammar grammar = new RuledGrammar();
		grammar.put("passed", TrueRule.get());
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
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
		RuledGrammar grammar = new RuledGrammar();
		grammar.put("failed", FalseRule.get());
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
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
		RuledGrammar grammar = new RuledGrammar();
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
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
		RuledGrammar grammar = new RuledGrammar();
		grammar.put("passed", TrueRule.get());
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
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
		RuledGrammar grammar = new RuledGrammar();
		grammar.put("failed", FalseRule.get());
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
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
		Grammar grammar = new RuledGrammar();
		SimpleRuleParser visitor = new SimpleRuleParser(grammar,new RuleHistoryTreeMap(),null);
		
		
		PointerRule rule = new PointerRule();
		rule.setSymbol(false);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
}
