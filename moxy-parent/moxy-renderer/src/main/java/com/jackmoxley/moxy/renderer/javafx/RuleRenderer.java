package com.jackmoxley.moxy.renderer.javafx;

import java.util.Map;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;


public abstract class RuleRenderer extends Application {

	protected abstract Grammar getGrammar() throws Exception;

	public void start(Stage stage) throws Exception {
		Grammar grammar = getGrammar();
		Group group = new Group();

		ScrollPane pane = new ScrollPane(group);

        Scene scene = new Scene(pane, 1000, 1000, Color.WHITE);
        

		Map<String, RuleGraph> graphs =grammar.getRuleTrees();
		RuleGraphNode graphNode;
		Property<Number> start = new SimpleIntegerProperty(0);
		for(RuleGraph graph : graphs.values()){
			graphNode = NodeFactory.getInstance().getGraphFor(graph);
			group.getChildren().add(graphNode);
			graphNode.setup();
			graphNode.layoutYProperty().bind(start);
			start =graphNode.endYProperty();
		}
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("RuleRenderer");
        stage.setScene(scene);
        stage.show();
	}

}
