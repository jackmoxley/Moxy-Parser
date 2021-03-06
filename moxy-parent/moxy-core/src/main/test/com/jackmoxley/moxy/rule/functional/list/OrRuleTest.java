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
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.collecting.SkipRule;
import com.jackmoxley.moxy.token.stream.CharSequenceTokenStream;

@Beta
public class OrRuleTest {

	@Test
	public void testHappyPath_first_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		
	}
	
	@Test
	public void testHappyPath_first_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_first_pass() {
		TrueRule pass = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	@Test
	public void testHappyPath_first_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_first_fail() {
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_first_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptFirst);
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_longest_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		
	}
	
	@Test
	public void testHappyPath_longest_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_longest_pass() {
		TrueRule pass = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}

	
	@Test
	public void testHappyPath_longest_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_longest_fail() {
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_longest_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}

	@Test
	public void testUnHappyPath_longest_empty() {
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(0, visitor.getHistory().getRuleDecisions(0).size());
	}

	@Test
	public void testHappyPath_shortest_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(fail);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		
	}
	
	@Test
	public void testHappyPath_shortest_pass_fail() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(pass);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_shortest_pass() {
		TrueRule pass = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(pass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testHappyPath_shortest_pass_pass() {
		TrueRule pass = TrueRule.get();
		TrueRule pass2 = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(pass);
		rule.add(pass2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_shortest_fail() {
		FalseRule fail = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(fail);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}
	
	@Test
	public void testUnHappyPath_shortest_fail_fail() {
		FalseRule fail = FalseRule.get();
		FalseRule fail2 = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(fail);
		rule.add(fail2);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
	}

	@Test
	public void testUnHappyPath_shortest_empty() {
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(0, visitor.getHistory().getRuleDecisions(0).size());
	}
	@Test
	public void testHappyPath_longest_passLong_passShort() {
		SkipRule shortPass = new SkipRule(true, 1);
		SkipRule longPass = new SkipRule(true, 2);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(longPass);
		rule.add(shortPass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 2);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		
	}
	

	@Test
	public void testHappyPath_longest_passShort_passLong() {
		SkipRule shortPass = new SkipRule(true, 1);
		SkipRule longPass = new SkipRule(true, 2);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptLongest);
		rule.add(shortPass);
		rule.add(longPass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 2);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		
	}
	@Test
	public void testHappyPath_shortest_passLong_passShort() {
		SkipRule shortPass = new SkipRule(true, 1);
		SkipRule longPass = new SkipRule(true, 2);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(longPass);
		rule.add(shortPass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 1);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		
	}
	

	@Test
	public void testHappyPath_shortest_passShort_passLong() {
		SkipRule shortPass = new SkipRule(true, 1);
		SkipRule longPass = new SkipRule(true, 2);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		OrRule rule = new OrRule();
		rule.setType(OrRule.Type.AcceptShortest);
		rule.add(shortPass);
		rule.add(longPass);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 1);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		
	}
}
