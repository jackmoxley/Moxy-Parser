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
package com.jackmoxley.moxy.rule.terminating.text;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;
import com.jackmoxley.moxy.token.CharacterToken;

/**
 * CharacterRangeRule allows us to check whether the character at this point in
 * the stream is between inclusive of the start and end characters. This is
 * envisaged mostly for being able to select any upper case character or any
 * lower case character without using ChoiceRule.
 * 
 * @author jack
 * 
 */
@Beta
public class CharacterRangeRule extends TerminatingRule {

	private static final long serialVersionUID = -1;

	protected char start;

	protected char end;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex);

		if (token != null
				&& (token.getCharacter() >= start && token.getCharacter() <= end)) {
			decision.passed();
			decision.getTokens().add(token);
			decision.setNextIndex(startIndex + 1);
		} else {
			decision.failed("CharacterRangeRule '{}..{}' failed got '{}'",
					start, end, token == null ? null : token.getCharacter());
		}

	}

	public CharacterRangeRule(char start, char end) {
		this.start = start;
		this.end = end;
	}

	public CharacterRangeRule() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CharacterRangeRule [start=").append(start)
				.append(", end=").append(end).append("]");
		return builder.toString();
	}

	public char getStart() {
		return start;
	}

	public void setStart(char start) {
		this.start = start;
	}

	public char getEnd() {
		return end;
	}

	public void setEnd(char end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterRangeRule other = (CharacterRangeRule) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}

}
