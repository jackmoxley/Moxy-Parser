package com.jackmoxley.moxy.token.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.jackmoxley.moxy.token.CharacterToken;


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
