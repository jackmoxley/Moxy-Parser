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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.OptionRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

public class OrRuleTest {

	@Test
	public void testHappyPath_lazy_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
		
	}
	
	@Test
	public void testHappyPath_lazy_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_lazy_pass() {
		TrueRule pass = TrueRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	@Test
	public void testHappyPath_lazy_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_lazy_fail() {
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_lazy_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(false);
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
	}
	@Test
	public void testHappyPath_greedy_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
		
	}
	
	@Test
	public void testHappyPath_greedy_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_greedy_pass() {
		TrueRule pass = TrueRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	@Test
	public void testHappyPath_greedy_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_greedy_fail() {
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_greedy_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(2, visitor.getRuleDecisions(0).size());
	}

	@Test
	public void testUnHappyPath_greedy_empty() {
		RuleEvaluator visitor = new RuleEvaluator();
		
		OptionRule rule = new OptionRule();
		rule.setGreedy(true);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(0, visitor.getRuleDecisions(0).size());
	}


}
