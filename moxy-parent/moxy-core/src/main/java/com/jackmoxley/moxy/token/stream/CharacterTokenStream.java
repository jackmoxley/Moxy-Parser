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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.CharacterToken;


@Beta
public class CharacterTokenStream extends TokenStreamImpl<CharacterToken> {
	
	public CharacterTokenStream(Reader charReader) throws IOException {
		super(new ArrayList<CharacterToken>());
		final Reader buffered = new BufferedReader(charReader);
		int read = 0;
		int lineNo = 1;
		int linePos = 1;
		while(( read =buffered.read()) != -1){
			tokens.add(new CharacterToken((char)read, lineNo, linePos++));
			if(read == '\n'){
				lineNo++;
				linePos = 1;
			}
		}
	}
}
