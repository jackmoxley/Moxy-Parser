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

import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.parser.RuleDecision;
import com.jackmoxley.moxy.parser.RuleParser;
import com.jackmoxley.moxy.token.CharacterToken;

/**
 * Skip rule allows us to determine the number of tokens to skip. As long as
 * that many characters exist we skip past them.
 * 
 * @author jack
 * 
 */
@Beta
public class SkipRule extends CollectingRule {

	private static final long serialVersionUID = 163168561429254410L;

	private int tokensToSkip;

	public SkipRule() {
		super();
	}

	public SkipRule(boolean collecting) {
		super(collecting);
	}

	public SkipRule(int tokensToSkip) {
		super();
		this.tokensToSkip = tokensToSkip;
	}

	public SkipRule(boolean collecting, int tokensToSkip) {
		super(collecting);
		this.tokensToSkip = tokensToSkip;
	}

	@Override
	public void consider(RuleParser visitor, RuleDecision decision) {
		final int start = decision.getStartIndex(); // ?
		final int end = start + tokensToSkip;
		List<CharacterToken> tokens = visitor.getSequence().tokens(start,
				tokensToSkip);
		int tokensReturned = tokens.size();
		if (tokensToSkip == tokensReturned) {
			passed(decision, end, tokens);
		} else {
			decision.failed("{} has failed as only {} tokens left", this,
					tokensReturned);
		}
	}

	public int getTokensToSkip() {
		return tokensToSkip;
	}

	public void setTokensToSkip(int tokensToSkip) {
		this.tokensToSkip = tokensToSkip;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkipRule [tokensToSkip=").append(tokensToSkip)
				.append(", collecting=").append(collecting).append("]");
		return builder.toString();
	}
}
