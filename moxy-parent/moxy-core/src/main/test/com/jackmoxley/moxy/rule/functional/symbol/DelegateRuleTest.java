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
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

@Beta
public class DelegateRuleTest {

	
	@Test
	public void testHappyPath_symbol_pass() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(true);
		rule.setPointer("passed");
		rule.setRule(TrueRule.get());
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	
	
	@Test
	public void testUnHappyPath_symbol_fail() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(true);
		rule.setPointer("failed");
		rule.setRule(FalseRule.get());
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_symbol_null() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(true);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	
	@Test
	public void testHappyPath_link_pass() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(false);
		rule.setPointer("passed");
		rule.setRule(TrueRule.get());
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	
	
	@Test
	public void testUnHappyPath_link_fail() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(false);
		rule.setPointer("failed");
		rule.setRule(FalseRule.get());
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_link_null() {
		RuleEvaluator visitor = new RuleEvaluator(null,new RuleHistoryTreeMap(),null);
		
		
		DelegateRule rule = new DelegateRule();
		rule.setSymbol(false);
		rule.setPointer("passed");
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
}
