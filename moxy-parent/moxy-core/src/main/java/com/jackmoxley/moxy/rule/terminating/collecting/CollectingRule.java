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

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;
import com.jackmoxley.moxy.token.Token;

/**
 * @author jack
 *
 */
public abstract class CollectingRule extends TerminatingRule {

	private static final long serialVersionUID = -1l;
	protected boolean collecting;
	
	public CollectingRule() {
		super();
	}
	
	public CollectingRule(boolean collecting) {
		super();
		this.collecting = collecting;
	}
	
	protected void passed(RuleDecision decision, int nextIndex, List<? extends Token> tokens){
		if(collecting){
			decision.getTokens().addAll(tokens);
		} 
		decision.setNextIndex(nextIndex);
		decision.passed();
	}

	public boolean isCollecting() {
		return collecting;
	}

	public void setCollecting(boolean collecting) {
		this.collecting = collecting;
	}
	
}
