package com.jackmoxley.moxy.renderer.node;

import java.awt.Graphics2D;

import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.renderable.TextBox;
import com.jackmoxley.moxy.rule.terminating.EOFRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.collecting.EOLRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class TerminatingNode extends Node<TerminatingRule> {

	private final TextBox box;

	public TerminatingNode(Spatial parent, TerminatingRule rule) {
		super(parent, rule);
		box = new TextBox(this, generateText());
		add(box);
	}

	@Override
	protected void prepareNode(Graphics2D g) {
		box.prepare(g);
		this.copyBounds(box);
	}
	
	protected String generateText() {
		StringBuilder sb = new StringBuilder();
		if (rule == null) {
			return "null";
		} else if (rule instanceof CharacterRule) {
			sb.append(((CharacterRule) rule).getCharacter());
		} else if (rule instanceof TextRule) {
			sb.append(((TextRule) rule).getValue());
		} else if (rule instanceof CharacterRangeRule) {
			sb.append(((CharacterRangeRule) rule).getStart());
			sb.append(" ... ");
			sb.append(((CharacterRangeRule) rule).getEnd());
		} else if (rule instanceof TrueRule) {
			sb.append("True");
		} else if (rule instanceof FalseRule) {
			sb.append("False");
		} else if (rule instanceof EOFRule) {
			sb.append("EOF");
		} else if (rule instanceof EOLRule) {
			sb.append("EOL");
		} else {
			sb.append(rule.toString());
		}
		return sb.toString();
	}

}
