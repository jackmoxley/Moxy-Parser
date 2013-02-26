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

import com.jackmoxley.moxy.realizer.RealizedHolder;
import com.jackmoxley.moxy.renderer.swing.RuleViewerFrame;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class RuleViewerFrameTest  {



	public static void main(String... args) throws HeadlessException, Exception {
		CharacterRule rule = new CharacterRule('a');
		CharacterRule rule1 = new CharacterRule('b');
		CharacterRule rule2 = new CharacterRule('c');
		CharacterRule rule3 = new CharacterRule('d');
		SequenceRule sequence = new SequenceRule();
		OrRule option = new OrRule();
		sequence.add(new TextRule("Hello World!"));
		sequence.add(option);
		sequence.add(option);
		sequence.add(rule2);
		sequence.add(new CharacterRangeRule('0', '9'));

		option.add(rule);
		option.add(sequence);
		option.add(new CharacterRangeRule('A', 'Z'));
		option.add(rule1);
		option.add(rule2);
		option.add(rule2);
		option.add(rule3);
		RealizedHolder<? extends Rule> ruleHolder1 = new RealizedHolder<>("My Sequence",sequence);
		RealizedHolder<? extends Rule> ruleHolder2 = new RealizedHolder<>("My Option",option);
		
		RuleViewerFrame viewer = new RuleViewerFrame(RuleVisitorTest.basicTest());
		viewer.setVisible(true);
		

	}
}
