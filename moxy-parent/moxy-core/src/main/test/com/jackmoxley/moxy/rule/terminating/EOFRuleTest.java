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
package com.jackmoxley.moxy.rule.terminating;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.SimpleRuleParser;
import com.jackmoxley.moxy.rule.RuleHistoryTreeMap;
import com.jackmoxley.moxy.token.stream.CharSequenceTokenStream;

/**
 * @author jack
 * 
 */
public class EOFRuleTest {

	@Test
	public void testHappyPath_singleLine() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(7);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testHappyPath_doubleLine_middle() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		// \n is the end of the line
		RuleDecision decision = new RuleDecision(7);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testHappyPath_doubleLine_end() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(17);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testUnHappyPath_singleLine_begining() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_singleLine_penultimate() {
		String textToCheck = "abcdefg";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(5);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_doubleLine_begining() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(0);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_doubleLine_penultimate_middle() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		// \n is the end of the line
		RuleDecision decision = new RuleDecision(6);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_doubleLine_after_middle() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		// \n is the end of the line
		RuleDecision decision = new RuleDecision(8);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testUnHappyPath_doubleLine_penultimate_end() {
		String textToCheck = "abcdefg\nhijklmnop";

		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), new CharSequenceTokenStream(
						textToCheck));

		EOFRule rule = EOFRule.get();

		RuleDecision decision = new RuleDecision(15);
		assertTrue(decision.isUnconsidered());
		rule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}
}
