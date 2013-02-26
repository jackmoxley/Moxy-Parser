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

import java.util.Collections;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;

/**
 * EOLRule checks to see if we have reached the end of our line or not. We do
 * this by checking the line position of the next token. EOLRule will return
 * true if this character is the last character on the line, in the middle of a
 * character stream this might be the '\n' character, whilst at the end it might
 * be the last char. This differs from the EOFRule which will always look for
 * the non-existence of a token at the end of the stream
 * 
 * @author jack
 * 
 */
@Beta
public class EOLRule extends CollectingRule {

	private static final long serialVersionUID = 163168561429254410L;

	public EOLRule() {
		super();
	}

	public EOLRule(boolean collecting) {
		super(collecting);
	}

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex + 1);
		if (token == null || token.getLinePos() == 1) {
			passed(decision, startIndex + 1, Collections.singletonList(token));
		} else {
			decision.failed("{} failed got", this);

		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EOLRule [collecting=").append(collecting).append("]");
		return builder.toString();
	}

}
