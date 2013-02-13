package com.moxley.moxy;

import java.awt.HeadlessException;

import com.jackmoxley.moxy.realizer.RealizedHolder;
import com.jackmoxley.moxy.renderer.swing.RuleViewerFrame;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.OptionRule;
import com.jackmoxley.moxy.rule.functional.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.StringRule;

public class RuleViewerFrameTest  {



	public static void main(String... args) throws HeadlessException, Exception {
		CharacterRule rule = new CharacterRule('a');
		CharacterRule rule1 = new CharacterRule('b');
		CharacterRule rule2 = new CharacterRule('c');
		CharacterRule rule3 = new CharacterRule('d');
		SequenceRule sequence = new SequenceRule();
		OptionRule option = new OptionRule();
		sequence.add(new StringRule("Hello World!"));
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
