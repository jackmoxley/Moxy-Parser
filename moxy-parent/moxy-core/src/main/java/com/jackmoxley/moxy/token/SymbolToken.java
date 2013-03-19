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
package com.jackmoxley.moxy.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jackmoxley.meta.Beta;

@Beta
public class SymbolToken extends ArrayList<Token> implements Token {


	private static final long serialVersionUID = 1L;
	private int lastModCount = -1;
	private String storedValue = null;
	private int heirachy;
	private String symbol;
	
	private static final Logger logger = LoggerFactory.getLogger(SymbolToken.class);
	
	public SymbolToken() {
		super();
	}
	
	public SymbolToken(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String getValue() {
		if(this.modCount != lastModCount || storedValue == null){
			StringBuilder sb = new StringBuilder();
			for(Token token:this){
				sb.append(token.getValue());
			}
			storedValue = sb.toString();
		}
		return storedValue;
	}

	public int getHeirachy() {
		return heirachy;
	}

	public void setHeirachy(int heirachy) {
		this.heirachy = heirachy;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SymbolToken [symbol=").append(symbol)
				.append(", value=").append(getValue()).append("]");
		return builder.toString();
	}
	
	public List<SymbolToken> get(String name) {
		if(name == null){
			return Collections.emptyList();
		}
		String[] subnames = name.split("[.]");
		List<SymbolToken> tokens = new ArrayList<SymbolToken>();
		get(tokens, subnames, 0);
		return tokens;
	}
	
	/**
	 *  Do we use the regex symbols such as '+' '*' & '?' to represent multiples, I think this will solve some of the issues.
	 * @param name
	 * @return
	 */
	
	public boolean get(List<SymbolToken> tokens, String[] names, int count) {
		int nextCount = count + 1;
		String name = names[count];
		int length = name.length();
		name = name.replace("*", "");
		final boolean star = name.length() != length;

		length = name.length();
		name = name.replace("+", "");
		final boolean plus = name.length() != length;

		length = name.length();
		name = name.replace("?", "");
		final boolean question = name.length() != length;
		
		final boolean many = star || plus;
		final boolean optional = question || star; 
		// we use this to determine whether we should add null for non entities

		for (Token token : this) {
			if (token instanceof SymbolToken) {
				SymbolToken symbol = (SymbolToken) token;

				logger.trace("get considering {}, {}, {}",nextCount,symbol,name);
				if (name.equals(symbol.getSymbol())) {
					if (nextCount >= names.length) {
						logger.trace("get found {}, {}, {}",nextCount,symbol,name);
						tokens.add(symbol);
						if(!many){
							return true;
						}
					} else {

						logger.trace("get traversing {}, {}, {}",nextCount,names);
						if(symbol.get(tokens, names, nextCount) && !many){
							return true;
						}
					}
				} else if(optional) {
					tokens.add(null);
				}
			}
		}	
		return optional;
	}

}
