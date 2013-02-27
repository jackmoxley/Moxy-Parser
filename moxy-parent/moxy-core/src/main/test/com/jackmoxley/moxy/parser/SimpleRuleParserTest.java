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
package com.jackmoxley.moxy.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.parser.RuleDecision.State;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleVisitor;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

/**
 * @author jack
 * 
 */
public class SimpleRuleParserTest {

	@Test
	public void testHappyPath_passed() {
		TrueRule trueRule = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), null);
		RuleDecision decision = visitor.evaluate(trueRule, 0);
		assertTrue(decision.hasPassed());

	}

	@Test
	public void testUnHappyPath_failed() {
		FalseRule falseRule = FalseRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,
				new RuleHistoryTreeMap(), null);
		RuleDecision decision = visitor.evaluate(falseRule, 0);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testUnHappyPath_cyclic() {
		RuleHistoryTreeMap history = new RuleHistoryTreeMap();
		TrueRule trueRule = TrueRule.get();
		SimpleRuleParser visitor = new SimpleRuleParser(null,
				history, null);
		RuleDecision decision = history.getRuleDecision(trueRule, 0);
		decision.setState(State.Considering);
		decision = visitor.evaluate(trueRule, 0);
		assertTrue(decision.hasFailed());
		assertTrue(State.Cyclic.equals(decision.getState()));
	}
	

	@Test
	public void testUnHappyPath_exception() {
		RuleHistoryTreeMap history = new RuleHistoryTreeMap();
		Rule exceptionRule = new Rule(){

			private static final long serialVersionUID = 1L;

			@Override
			public void consider(RuleParser visitor, RuleDecision decision) {
				RuntimeException rex =  new RuntimeException("Test Exception, Ignore at will");
				rex.setStackTrace(new StackTraceElement[0]);
				throw rex;
			}

			@Override
			public void accept(RuleVisitor visitor) { }
			
		};
		SimpleRuleParser visitor = new SimpleRuleParser(null,
				history, null);
		RuleDecision decision = visitor.evaluate(exceptionRule, 0);
		assertTrue(decision.hasFailed());
	}
	
	@Test
	public void testUnHappyPath_dumb() {
		RuleHistoryTreeMap history = new RuleHistoryTreeMap();
		Rule dumbRule = new Rule(){

			private static final long serialVersionUID = 1L;

			@Override
			public void consider(RuleParser visitor, RuleDecision decision) {
			}

			@Override
			public void accept(RuleVisitor visitor) { }
			
		};
		SimpleRuleParser visitor = new SimpleRuleParser(null,
				history, null);
		RuleDecision decision = visitor.evaluate(dumbRule, 0);
		assertTrue(decision.hasFailed());
	}

}
