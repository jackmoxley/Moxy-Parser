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
package com.jackmoxley.moxy.rule.functional.single;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.SimpleRuleParser;
import com.jackmoxley.moxy.rule.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.collecting.SkipRule;
import com.jackmoxley.moxy.token.stream.CharSequenceTokenStream;

/**
 * @author jack
 *
 */
public class MinMaxRuleTest {


	@Test
	public void testEmpty(){
		MinMaxRule testRule = new MinMaxRule();

		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testTrue(){
		TrueRule trueRule = TrueRule.get();
		MinMaxRule testRule = new MinMaxRule(trueRule);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testFalse(){
		FalseRule falseRule = FalseRule.get();
		MinMaxRule testRule = new MinMaxRule(falseRule);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		
	}
	@Test
	public void testTrue_1(){
		TrueRule trueRule = TrueRule.get();
		MinMaxRule testRule = new MinMaxRule(trueRule, 1, MinMaxRule.UNLIMITED);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testFalse_1(){
		FalseRule falseRule = FalseRule.get();
		MinMaxRule testRule = new MinMaxRule(falseRule, 1, MinMaxRule.UNLIMITED);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testHappyPath_collecting_unlimited() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		MinMaxRule rule = new MinMaxRule(skip);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 7);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
		assertEquals(decision.getTokens().get(4).getValue(),"e");
		assertEquals(decision.getTokens().get(5).getValue(),"f");
		assertEquals(decision.getTokens().get(6).getValue(),"g");
		
	}
	
	
	@Test
	public void testHappyPath_collecting_min3_max5_with6() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdef"));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 5);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
		assertEquals(decision.getTokens().get(4).getValue(),"e");
		
	}
	
	@Test
	public void testHappyPath_collecting_min3_max5_with4() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcd"));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 4);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
		
	}
	
	@Test
	public void testHappyPath_collecting_min3_max5_with5() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcde"));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 5);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
		assertEquals(decision.getTokens().get(4).getValue(),"e");
	}
	
	@Test
	public void testHappyPath_collecting_min3_max5_with3() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abc"));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 3);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
	}
	
	@Test
	public void testUnHappyPath_collecting_min3_max5_with2() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("ab"));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	

	@Test
	public void testUnHappyPath_collecting_min3_max5_with0() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream(""));
		
		MinMaxRule rule = new MinMaxRule(skip,3,5);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

}
