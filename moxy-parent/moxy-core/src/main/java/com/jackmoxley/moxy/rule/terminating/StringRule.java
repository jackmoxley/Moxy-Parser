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

import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;

@Beta
public class StringRule extends TerminatingRule {

	private static final long serialVersionUID = -1;

	protected final String value;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		List<CharacterToken> tokens = visitor.getSequence().tokens(startIndex,
				value.length());
		int size = tokens.size();
		if(size < value.length()){
			decision.failed("StringRule \"{}\" failed, {} tokens returned, expected {}",value, size,value.length());
			return;
		}
		CharacterToken token;
		for (int i = 0; i < size; i++) {
			token = tokens.get(i);
			if (value.charAt(i) != token.getCharacter()) {
				decision.failed("StringRule \"{}\" failed at {} expected '{}' not '{}'",value, i,value.charAt(i),token.getCharacter());
				return;
			}
			decision.getTokens().add(token);
		}
		decision.passed();
		decision.setNextIndex(startIndex + value.length());
	}

	public StringRule(String value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "StringRule [value=" + value + "]";
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		StringRule other = (StringRule) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
