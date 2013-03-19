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
package com.jackmoxley.moxy;

import java.awt.HeadlessException;

import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuledGrammar;
import com.jackmoxley.moxy.renderer.swing.RuleViewerFrame;
import com.jackmoxley.moxy.rule.functional.list.AndRule;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.list.XOrRule;
import com.jackmoxley.moxy.rule.functional.single.MinMaxRule;
import com.jackmoxley.moxy.rule.functional.single.NotRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.functional.single.UntilRule;
import com.jackmoxley.moxy.rule.functional.symbol.DelegateRule;
import com.jackmoxley.moxy.rule.functional.symbol.PointerRule;
import com.jackmoxley.moxy.rule.terminating.EOFRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.collecting.EOLRule;
import com.jackmoxley.moxy.rule.terminating.collecting.SkipRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class RuleRendererTest {

	public static void main(String... args) throws HeadlessException, Exception {

		RuleViewerFrame viewer = new RuleViewerFrame(
				createGrammarForEveryRule());
		viewer.setVisible(true);

	}

	private static Grammar createGrammarForEveryRule() {
		RuledGrammar grammar = new RuledGrammar();
		// Terminating
		EOLRule eol = new EOLRule();
		grammar.put("eol", eol);
		SkipRule skip = new SkipRule(5);
		grammar.put("skip", skip);
		CharacterRangeRule characterRange = new CharacterRangeRule('A', 'Z');
		grammar.put("characterRange", characterRange);
		CharacterRule character = new CharacterRule('Q');
		grammar.put("character", character);
		TextRule text = new TextRule("Hello World!");
		grammar.put("textStyle", text);
		EOFRule eof = EOFRule.get();
		grammar.put("eof", eof);
		TrueRule tru = TrueRule.get();
		grammar.put("true", tru);
		FalseRule fals = FalseRule.get();
		grammar.put("false", fals);

		// Functional List
		AndRule and = new AndRule();
		and.add(eol);
		and.add(skip);
		grammar.put("and", and);
		OrRule or = new OrRule();
		or.add(characterRange);
		or.add(character);
		grammar.put("or", or);
		XOrRule xor = new XOrRule();
		xor.add(text);
		xor.add(eof);
		grammar.put("xor", xor);
		SequenceRule sequenece = new SequenceRule();
		sequenece.add(tru);
		sequenece.add(fals);
		grammar.put("sequenece", sequenece);
		// Functional Single
		MinMaxRule minMax = new MinMaxRule(character, -1, 6);
		grammar.put("minMax", minMax);
		NotRule not = new NotRule(text);
		grammar.put("not", not);
		OptionalRule optional = new OptionalRule(characterRange);
		grammar.put("optional", optional);
		UntilRule until = new UntilRule(tru, 5, -1, fals, true, true);
		grammar.put("until", until);
		// Symbol Rule
		PointerRule pointer = new PointerRule(true, "skip");
		grammar.put("pointer", pointer);
		DelegateRule delegate = new DelegateRule("eol", eol);
		grammar.put("delegate", delegate);
		return grammar;
	}
}
