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

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;

/**
 * EOFRule checks to see if we have reached the end of our token stream or not.
 * @author jack
 *
 */
@Beta
public class EOFRule extends TerminatingRule {

	private static final long serialVersionUID = 163168561429254410L;

	private static final EOFRule instance = new EOFRule();
	
	private EOFRule(){
		
	}
	
	public static EOFRule get(){
		return instance;
	}
	
	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex);
		if(token == null) {
			decision.passed();
			decision.setNextIndex(startIndex);
		} else {			
			decision.failed("EOFRule failed got '{}'", token == null ? null :token.getCharacter());

		}
	}

}
