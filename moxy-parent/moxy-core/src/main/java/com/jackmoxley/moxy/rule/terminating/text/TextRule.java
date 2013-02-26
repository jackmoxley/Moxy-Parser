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

import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.RuleParser;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;
import com.jackmoxley.moxy.token.CharacterToken;

@Beta
public class TextRule extends TerminatingRule {

	private static final long serialVersionUID = -1;

	protected final CharSequence text;

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		List<CharacterToken> tokens = visitor.getSequence().tokens(startIndex,
				text.length());
		int size = tokens.size();
		if(size < text.length()){
			decision.failed("{} failed, {} tokens returned, expected {}",this, size,text.length());
			return;
		}
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			char characterToCheck = tokens.get(i).getCharacter();
			if (character != characterToCheck) {
				decision.failed("{} failed at {} expected '{}' not '{}'",this, i,character,characterToCheck);
				return;
			}
		}
		decision.addAll(tokens, startIndex + text.length());
		decision.passed();
	}

	public TextRule(CharSequence value) {
		super();
		this.text = value;
	}



	public CharSequence getValue() {
		return text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TextRule [text=").append(text).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		TextRule other = (TextRule) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
