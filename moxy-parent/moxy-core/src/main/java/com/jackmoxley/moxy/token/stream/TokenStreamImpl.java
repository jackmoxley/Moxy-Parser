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
package com.jackmoxley.moxy.token.stream;

import java.util.Collections;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.Token;

@Beta
public class TokenStreamImpl<T extends Token> implements TokenStream<T>{


	protected final List<T> tokens;
	
	public TokenStreamImpl(List<T> tokens) {
		super();
		this.tokens = tokens;
	}

	@Override
	public T tokenAt(int index) {
		if(index >= tokens.size()){
			return null;
		}
		return tokens.get(index);
	}
	
	public int length() {
		return tokens.size();
	}
	

	@Override
	public List<T> tokens(int start, int length) {
		if(start < 0 || length <= 0){
			return Collections.emptyList();
		}
		int end = start+length;
		if(end > tokens.size()){
			return Collections.emptyList();
		}
		return tokens.subList(start, end);
	}
	
}
