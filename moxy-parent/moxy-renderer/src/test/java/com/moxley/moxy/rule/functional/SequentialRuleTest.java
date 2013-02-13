package com.moxley.moxy.rule.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

public class SequentialRuleTest {

	@Test
	public void testUnHappyPath_fail_pass() {
		TrueRule pass = TrueRule.get();
		FalseRule fail = FalseRule.get();
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
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
		RuleEvaluator visitor = new RuleEvaluator();
		
		SequenceRule rule = new SequenceRule();
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
		assertEquals(0, visitor.getRuleDecisions(0).size());
	}

}
