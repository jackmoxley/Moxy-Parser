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
package com.jackmoxley.moxy.rule.functional.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.SimpleRuleParser;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

@Beta
public class SequentialRuleTest {

	@Test
	public void testUnHappyPath_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		
	}
	
	@Test
	public void testUnHappyPath_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testHappyPath_pass() {
		TrueRule pass = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	@Test
	public void testHappyPath_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}
	
	@Test
	public void testUnHappyPath_fail() {
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testUnHappyPath_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_greedy_empty() {
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		SequenceRule rule = new SequenceRule();
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(0, visitor.getHistory().getRuleDecisions(0).size());
	}

}
