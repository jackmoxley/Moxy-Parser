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

import com.jackmoxley.meta.Beta;

@Beta
public class CharacterToken implements Token{

	private final char character;
	private final int lineNo;
	private final int linePos;
	
	public CharacterToken(char character, int lineNo, int linePos) {
		this.character = character;
		this.lineNo = lineNo;
		this.linePos = linePos;
	}
	
	public int getLineNo() {
		return lineNo;
	}

	public int getLinePos() {
		return linePos;
	}

	@Override
	public String getValue() {
		return String.valueOf(character);
	}
	
	public char getCharacter() {
		return character;
	}
	

	@Override
	public String toString() {
		return "CharacterToken [lineNo=" + lineNo + ", linePos=" + linePos
				+ ", value=" + getValue() + "]";
	}
}
