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
package com.jackmoxley.moxy.rule.terminating.collecting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.SimpleRuleParser;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.token.stream.CharSequenceTokenStream;

/**
 * @author jack
 * 
 */
public class SkipRuleTest {

	@Test
	public void testHappyPath_skip_0() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(0);
		rule.setCollecting(true);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		
		assertEquals(decision.getTokens().size(), 0);
	}

	@Test
	public void testHappyPath_skip_4() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(4);
		rule.setCollecting(true);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(decision.getTokens().size(), 4);
		assertEquals(decision.getTokens().get(0).getValue(),"a");
		assertEquals(decision.getTokens().get(1).getValue(),"b");
		assertEquals(decision.getTokens().get(2).getValue(),"c");
		assertEquals(decision.getTokens().get(3).getValue(),"d");
	}
	

	@Test
	public void testHappyPath_skip_7() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(7);
		rule.setCollecting(true);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
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
	public void testUnHappyPath_skip_8() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(8);
		rule.setCollecting(true);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testHappyPath_skip_0_notcollecting() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(0);
		rule.setCollecting(false);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		
		assertEquals(decision.getTokens().size(), 0);
	}

	@Test
	public void testHappyPath_skip_4_notcollecting() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(4);
		rule.setCollecting(false);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(decision.getTokens().size(), 0);
	}
	

	@Test
	public void testHappyPath_skip_7_notcollecting() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(7);
		rule.setCollecting(false);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		assertEquals(decision.getTokens().size(), 0);
	}
	

	@Test
	public void testUnHappyPath_skip_8_notcollecting() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		SkipRule rule = new SkipRule();
		rule.setTokensToSkip(8);
		rule.setCollecting(false);
		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
	

}
