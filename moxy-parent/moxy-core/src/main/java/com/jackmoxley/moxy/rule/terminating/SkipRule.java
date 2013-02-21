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
public class SkipRule extends TerminatingRule {

	private static final long serialVersionUID = 163168561429254410L;

	private int tokensToSkip;
	
	public SkipRule(){
		
	}
	
	public SkipRule(int tokensToSkip) {
		super();
		this.tokensToSkip = tokensToSkip;
	}

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		final int start = decision.getStartIndex(); //?
		final int end = start+tokensToSkip;
		List<CharacterToken> tokens = visitor.getSequence().tokens(start, tokensToSkip);
		int tokensReturned = tokens.size();
		if(tokensToSkip == tokensReturned) {
			decision.getTokens().addAll(tokens);
			decision.setNextIndex(end);
			decision.passed();
		} else {
			decision.failed("{} has failed as only {} tokens left", this, tokensReturned);
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
				.append("]");
		return builder.toString();
	}

}
