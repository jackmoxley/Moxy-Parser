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
package com.jackmoxley.moxy.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;

/**
 * @author jack
 *
 */
public class RuleHistoryTreeMap implements RuleHistory{

	private TreeMap<Integer, Map<Rule, RuleDecision>> rulesHistory = new TreeMap<Integer, Map<Rule, RuleDecision>>();

	public Map<Rule, RuleDecision> getRuleDecisions(int startIndex) {
		Map<Rule, RuleDecision> history = rulesHistory.get(startIndex);
		if (history == null) {
			history = new HashMap<Rule, RuleDecision>();
			rulesHistory.put(startIndex, history);
		}
		return history;
	}

	public RuleDecision getRuleDecision(Rule rule, int startIndex) {
		Map<Rule, RuleDecision> history = getRuleDecisions(startIndex);
		RuleDecision decision = history.get(rule);
		if (decision == null) {
			decision = new RuleDecision(startIndex);
			history.put(rule, decision);
		}
		return decision;
	}
}
