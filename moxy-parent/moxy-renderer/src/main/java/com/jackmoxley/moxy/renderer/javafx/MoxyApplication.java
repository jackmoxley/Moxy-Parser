package com.jackmoxley.moxy.renderer.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.renderer.javafx.node.TerminatingNode;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;


public class MoxyApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Group group = new Group();

        Scene scene = new Scene(group, 1000, 1000, Color.WHITE);
        
		RuleNode<?> ruleNode = new TerminatingNode(new TextRule("Hello World!"));
		group.getChildren().add(ruleNode);

		RuleNode<?> last = ruleNode;
		ruleNode.setLayoutY(0);
		
		SequenceRule sequenceRule = new SequenceRule();
		sequenceRule.add(new TextRule("I"));
		sequenceRule.add(new TextRule("Am"));
		sequenceRule.add(new TextRule("Sequential"));
		ruleNode = NodeFactory.getInstance().getNodeFor(scene, sequenceRule);

		ruleNode.layoutYProperty().bind(last.endYProperty());
		last = ruleNode;
		group.getChildren().add(ruleNode);
		
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
		ruleNode = NodeFactory.getInstance().getNodeFor(scene, orRule);
		ruleNode.layoutYProperty().bind(last.endYProperty());
		last = ruleNode;
		group.getChildren().add(ruleNode);
		
		OptionalRule optionalRule2 = new OptionalRule();
		optionalRule2.add(new TextRule("Optional"));
		ruleNode = NodeFactory.getInstance().getNodeFor(scene, optionalRule2);
		ruleNode.setLayoutY(600);
		ruleNode.layoutYProperty().bind(last.endYProperty());
		last = ruleNode;
		group.getChildren().add(ruleNode);
		
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
	}

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
