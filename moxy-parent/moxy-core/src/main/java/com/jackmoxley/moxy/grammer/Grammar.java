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
package com.jackmoxley.moxy.grammer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.CharacterToken;
import com.jackmoxley.moxy.token.Token;
import com.jackmoxley.moxy.token.stream.TokenStream;

/**
 * The base implementation representing a grammar, this is where we store our
 * rule trees, keyed by a string.
 * 
 * @author jack
 * 
 */
@Beta
public interface Grammar extends Serializable {

	/**
	 * 
	 * @param input
	 * @return
	 */
	public List<Token> parse(TokenStream<CharacterToken> input, String start);

	public Map<String, RuleTree> getRuleTrees();
	
	public RuleTree get(String name);
}