package com.jackmoxley.moxy.rule.terminating;

import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.token.CharacterToken;

public class EOFRule extends TerminatingRule {

	private static final long serialVersionUID = 163168561429254410L;

	private static final EOFRule instance = new EOFRule();
	
	private EOFRule(){
		
	}
	
	public static EOFRule get(){
		return instance;
	}
	
	@Override
	public void consider(RuleEvaluator visitor, RuleDecision decision) {
		int startIndex = decision.getStartIndex();
		CharacterToken token = visitor.getSequence().tokenAt(startIndex);
		if(token == null) {
			decision.passed();
			decision.setNextIndex(startIndex);
		} else {			
			decision.failed("EOFRule failed got '{}'", token == null ? null :token.getCharacter());

		}
	}

}
