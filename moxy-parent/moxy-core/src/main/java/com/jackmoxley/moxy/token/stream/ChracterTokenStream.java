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
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.CharacterToken;

@Beta
public class ChracterTokenStream extends TokenStreamImpl<CharacterToken>
		implements Closeable {

	private final Reader charReader;

	private int totalRead = 0;
	private int lineNo = 1;
	private int linePos = 1;

	private boolean finished = false;

	public ChracterTokenStream(Reader charReader) {
		super(new ArrayList<CharacterToken>());
		this.charReader = new BufferedReader(charReader);
	}

	public ChracterTokenStream(File file) throws FileNotFoundException {
		this(new FileReader(file));
	}
	
	public ChracterTokenStream(InputStream in) throws FileNotFoundException {
		this(new InputStreamReader(in));
	}

	private synchronized void readNext(int requiredIndex){
		if (!finished && requiredIndex >= totalRead) {
			int read = 0;
			try {
				while (requiredIndex >= totalRead
						&& (read = charReader.read()) != -1) {
					this.totalRead++;
					tokens.add(new CharacterToken((char) read, lineNo, linePos++));
					if (read == '\n') {
						lineNo++;
						linePos = 1;
					}
				}
			} catch (IOException e) {
				finished = true;
				e.printStackTrace();
			}
			if (read == -1) {
				finished = true;
			}
		}
	}

	@Override
	public void close() throws IOException {
		charReader.close();
		finished = true;
	}

	@Override
	public CharacterToken tokenAt(int index) {
		readNext(index);
		return super.tokenAt(index);
	}

	@Override
	public List<CharacterToken> tokens(final int start, final int length) {
		readNext(start+length);
		return super.tokens(start, length);
	}

}
