package com.jackmoxley.moxy;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.grammer.RuledGrammar;
import com.jackmoxley.moxy.renderer.javafx.RuleRenderer;
import com.jackmoxley.moxy.renderer.javafx.component.StubPane;
import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.functional.symbol.PointerRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;


public class NodeRenderingTest extends RuleRenderer {

    @Override
	protected Grammar getGrammar() throws Exception {
    	RuledGrammar grammer = new RuledGrammar();
    	
    	grammer.put("Text Rule", new TextRule("Hello World!"));
    	
		SequenceRule sequenceRule = new SequenceRule();
		sequenceRule.add(new TextRule("I"));
		sequenceRule.add(new TextRule("Am"));
		sequenceRule.add(new TextRule("Sequential"));
    	grammer.put("Sequence Rule",sequenceRule);

		OrRule orRule2 = new OrRule();
		orRule2.add(new TextRule("I"));
		orRule2.add(new TextRule("Am An"));
		orRule2.add(new TextRule("Other Or"));
	
		OrRule orRule = new OrRule();
		orRule.add(new TextRule("I"));
		orRule.add(new TextRule("Am An"));
		orRule.add(orRule2);
		orRule.add(new TextRule("Or"));
		OptionalRule optionalRule = new OptionalRule();
		optionalRule.add(new TextRule("Optional"));
		orRule.add(optionalRule);
		orRule.add(optionalRule);
		orRule.add(new TextRule("Rule"));
    	grammer.put("Or Rule",orRule);
		
		OptionalRule optionalRule2 = new OptionalRule();
		optionalRule2.add(new TextRule("Optional"));
    	grammer.put("Optional Rule",optionalRule2);
		
		PointerRule pointer = new PointerRule(true, "I am a Pointer");
    	grammer.put("Pointer Rule",pointer);

    	
		return grammer;
	}





	public static void main(String[] args) throws Exception {
        launch(args);
    }
}
