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

import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.SimpleRuleParser;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.collecting.SkipRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.token.stream.CharSequenceTokenStream;

/**
 * @author jack
 *
 */
public class UntilRuleTest {


	@Test
	public void testEmpty(){
		UntilRule testRule = new UntilRule();

		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testTrue(){
		TrueRule trueRule = TrueRule.get();
		TrueRule until  = TrueRule.get();
		UntilRule testRule = new UntilRule(trueRule,until);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testFalse(){
		FalseRule falseRule = FalseRule.get();
		TrueRule until  = TrueRule.get();
		UntilRule testRule = new UntilRule(falseRule,until);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		
	}
	@Test
	public void testTrue_1(){
		TrueRule trueRule = TrueRule.get();
		TrueRule until  = TrueRule.get();
		UntilRule testRule = new UntilRule(trueRule, 1, UntilRule.UNLIMITED,until,false,false);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testFalse_1(){
		FalseRule falseRule = FalseRule.get();
		TrueRule until  = TrueRule.get();
		UntilRule testRule = new UntilRule(falseRule, 1, UntilRule.UNLIMITED,until, false,false);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testHappyPath_collecting_unlimited() {
		SkipRule skip = new SkipRule(true, 1);
		TrueRule until  = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		UntilRule rule = new UntilRule(skip,until);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(decision.getTokens().size(), 0);
		
	}
	
	@Test
	public void testHappyPath_collecting_unlimited_greedy() {
		SkipRule skip = new SkipRule(true, 1);
		TrueRule until  = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdefg"));
		
		UntilRule rule = new UntilRule(skip,until);
		rule.setGreedy(true);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(2, visitor.getHistory().getRuleDecisions(0).size());
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

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
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
	public void testHappyPath_collecting_min3_max5_with4() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcd"));

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
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
	public void testHappyPath_collecting_min3_max5_with5() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcde"));

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
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
	public void testHappyPath_collecting_min3_max5_with3() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abc"));

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
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

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	

	@Test
	public void testUnHappyPath_collecting_min3_max5_with0() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream(""));

		TrueRule until  = TrueRule.get();
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testHappyPath_collecting_min3_max5_with5_untilD() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcde"));

		CharacterRule until  = new CharacterRule('d');
		UntilRule rule = new UntilRule(skip,3,5,until,false,false);
		
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
	public void testHappyPath_collecting_min3_max5_with5_untilD_include() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcde"));

		CharacterRule until  = new CharacterRule('d');
		UntilRule rule = new UntilRule(skip,3,5,until,true,false);
		
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
	public void testHappyPath_collecting_min3_max5_with6_untilD_greedy() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdde"));

		CharacterRule until  = new CharacterRule('d');
		UntilRule rule = new UntilRule(skip,3,5,until,false,true);
		
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(1, visitor.getHistory().getRuleDecisions(0).size());
		assertEquals(4, decision.getTokens().size());
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
	}
	@Test
	public void testHappyPath_collecting_min3_max5_with6_untilD_include_greedy() {
		SkipRule skip = new SkipRule(true, 1);
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),new CharSequenceTokenStream("abcdde"));

		CharacterRule until  = new CharacterRule('d');
		UntilRule rule = new UntilRule(skip,3,5,until,true,true);
		
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
		assertEquals(decision.getTokens().get(4).getValue(),"d");
	}

}
