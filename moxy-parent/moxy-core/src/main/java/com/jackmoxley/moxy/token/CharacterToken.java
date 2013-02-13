package com.jackmoxley.moxy.token;




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
