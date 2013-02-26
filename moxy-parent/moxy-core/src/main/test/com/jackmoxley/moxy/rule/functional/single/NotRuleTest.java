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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.SimpleRuleParser;
import com.jackmoxley.moxy.parser.history.RuleHistoryTreeMap;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

/**
 * @author jack
 *
 */
public class NotRuleTest {


	@Test
	public void testEmpty(){
		NotRule testRule = new NotRule();

		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
	}

	@Test
	public void testTrue(){
		TrueRule trueRule = TrueRule.get();
		NotRule testRule = new NotRule(trueRule);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasFailed());
	}

	@Test
	public void testFalse(){
		FalseRule falseRule = FalseRule.get();
		NotRule testRule = new NotRule(falseRule);
		
		SimpleRuleParser visitor = new SimpleRuleParser(null,new RuleHistoryTreeMap(),null);
		RuleDecision decision = new RuleDecision(0);
		testRule.consider(visitor, decision);
		assertTrue(decision.hasPassed());
		
	}

}
