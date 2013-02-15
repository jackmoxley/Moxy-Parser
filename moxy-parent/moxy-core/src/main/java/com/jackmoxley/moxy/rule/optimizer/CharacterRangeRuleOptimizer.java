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
package com.jackmoxley.moxy.rule.optimizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;
import com.jackmoxley.moxy.rule.functional.OptionRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.StringRule;

/**
 * Optimizes rules to be as effecient as possible.
 * 
 * @author jack
 * 
 */
@Beta
public class CharacterRangeRuleOptimizer implements Optimizer {

	public int visitRule(Grammer grammer, FunctionalRule rule) {
		int rulesOptimized = 0;
		if (rule instanceof OptionRule) {
			List<RuleRange> ruleRanges = new ArrayList<RuleRange>();
			for (Rule innerRule : rule) {
				add(innerRule, ruleRanges);
			}
			for (RuleRange range : ruleRanges) {
				if (range.rulesUsed.size() >= 2) {
					rulesOptimized += range.rulesUsed.size() - 1;
					rule.removeAll(range.rulesUsed);
					CharacterRangeRule newRule = new CharacterRangeRule(
							range.start, range.end);
					rule.add(0, newRule);
				}
			}
		}
		return rulesOptimized;
	}

	public static void add(Rule rule, Collection<RuleRange> ranges) {
		char startChar;
		char endChar;
		if (rule instanceof CharacterRule) {
			CharacterRule charRule = (CharacterRule) rule;
			startChar = charRule.getCharacter();
			endChar = charRule.getCharacter();
		} else if (rule instanceof CharacterRangeRule) {
			CharacterRangeRule charRangeRule = (CharacterRangeRule) rule;
			startChar = charRangeRule.getStart();
			endChar = charRangeRule.getEnd();
		} else if (rule instanceof StringRule) {
			StringRule stringRule = (StringRule) rule;
			if (stringRule.getValue().length() == 1) {
				startChar = stringRule.getValue().charAt(0);
				endChar = startChar;
			} else {
				return;
			}
		} else {
			return;
		}

		for (RuleRange range : ranges) {
			if (range.add(rule, startChar, endChar)) {
				return;
			}
		}
		RuleRange range = new RuleRange(rule, startChar, endChar);
		ranges.add(range);
	}

	public static class RuleRange {

		final Set<Rule> rulesUsed = new LinkedHashSet<Rule>();
		char start;
		char end;

		public RuleRange(Rule rule, char start, char end) {
			super();
			this.rulesUsed.add(rule);
			this.start = start;
			this.end = end;
		}

		public boolean add(Rule rule, char start, char end) {
			if (inRange(start, end)) {
				setMaximum(start, end);
				rulesUsed.add(rule);
				return true;
			}
			return false;
		}

		private boolean inRange(char start, char end) {

			int allowedStart = this.start;
			if (allowedStart != Character.MIN_VALUE) {
				allowedStart--;
			}
			int allowedEnd = this.end;
			if (allowedEnd != Character.MAX_VALUE) {
				allowedEnd++;
			}

			if (start >= allowedStart) {
				if (start > allowedEnd) {
					return false;
				}
			} else if (end <= allowedEnd) {
				if (end < allowedStart) {
					return false;
				}
			}
			return true;
		}

		private void setMaximum(char start, char end) {
			if (this.start > start) {
				this.start = start;
			}
			if (this.end < end) {
				this.end = end;
			}
		}
	}
}
