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
 * CharacterRule provides functionality to determine whether the next charchter
 * in the token stream is equal to the one specified in this instance.
 * 
 * @author jack
 * 
 */
@Beta
public class CharacterRule extends TerminatingRule {

	private static final long serialVersionUID = -1;

	protected final char character;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex);
		if (token != null && character == token.getCharacter()) {
			decision.passed();
			decision.add(token, startIndex + 1);
		} else {
			decision.failed("CharacterRule '{}' failed got '{}'", character,
					token);

		}
	}

	public CharacterRule(char character) {
		this.character = character;
	}

	public CharacterRule(String character) {
		super();
		this.character = character.charAt(0);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CharacterRule [character=").append(character)
				.append("]");
		return builder.toString();
	}

	public char getCharacter() {
		return character;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
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
		CharacterRule other = (CharacterRule) obj;
		if (character != other.character)
			return false;
		return true;
	}

}
