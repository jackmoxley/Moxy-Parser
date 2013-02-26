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
package com.jackmoxley.moxy.rule;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.RuleTree;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.stream.TokenStream;

/**
 * RuleParser is the interface for our rule parsers, each rule may call one of
 * the evaluate methods, or may try and query more specific information like the
 * sequence of characters or fetch a rule for a particular name.
 * 
 */
@Beta
public interface RuleParser {

	public RuleDecision parse(RuleTree rule);

	public RuleDecision parse(Rule rule);

	public RuleDecision evaluate(Rule rule, int startIndex);

	public Rule ruleForName(String symbol);

	public TokenStream<CharacterToken> getSequence();

}
