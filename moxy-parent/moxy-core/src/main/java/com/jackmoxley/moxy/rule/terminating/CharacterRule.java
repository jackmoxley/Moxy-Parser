package com.jackmoxley.moxy.rule.terminating;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;

public class CharacterRule extends TerminatingRule {

	private static final long serialVersionUID = -1;

	protected final char character;

	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex);
		if(token != null && character == token.getCharacter()) {
			decision.passed();
			decision.getTokens().add(token);
			decision.setNextIndex(startIndex + 1);
		} else {			
			decision.failed("CharacterRule '{}' failed got '{}'",character, token);

		}
	}

	public CharacterRule(char character) {
		this.character = character;
	}

	public CharacterRule(String character) {
		super();
		this.character = character.charAt(0);
	}

	@Override
	public String toString() {
		return "CharacterRule [character=" + character + "]";
	}

	public char getCharacter() {
		return character;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterRule other = (CharacterRule) obj;
		if (character != other.character)
			return false;
		return true;
	}

}
