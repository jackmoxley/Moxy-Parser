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
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.CharacterToken;


@Beta
public class CharSequenceTokenStream extends TokenStreamImpl<CharacterToken> implements CharSequence{
	
	public CharSequenceTokenStream(Reader charReader) throws IOException {
		super(new ArrayList<CharacterToken>());
		readAll(charReader);
	}
	
	public CharSequenceTokenStream(CharSequence value) {
		super(new ArrayList<CharacterToken>());
		readSafely(new StringReader(value.toString()));
	}
	

	public CharSequenceTokenStream(char[] value){
		super(new ArrayList<CharacterToken>());
		readSafely(new CharArrayReader(value));
	}
	
	public CharSequenceTokenStream(List<CharacterToken> tokens) {
		super(tokens);
	}
	
	private void readSafely(Reader charReader){

		try {
			readAll(charReader);
			charReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			// Will never happen.
		}

	}
	
	private void readAll(Reader charReader) throws IOException{
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



	/* (non-Javadoc)
	 * @see java.lang.CharSequence#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return this.tokenAt(index).getCharacter();
	}

	/* (non-Javadoc)
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return new CharSequenceTokenStream(this.tokens(start, end-start));
	}

	@Override
	public String toString() {
		return new StringBuilder(this).toString();
	}
	
	
}
