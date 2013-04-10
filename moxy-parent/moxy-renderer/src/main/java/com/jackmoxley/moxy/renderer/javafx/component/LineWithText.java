package com.jackmoxley.moxy.renderer.javafx.component;



import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;


public class LineWithText extends StubPane{

	public Label textLabel;
	public Line line;
	public StringProperty text;
	
	
	public LineWithText() {
		this("");
	}
	
	public LineWithText(String string) {
		super();

		textLabel = new Label();
		line = new Line();
		this.getChildren().add(textLabel);
		this.getChildren().add(line);
		line.endXProperty().bind(textLabel.widthProperty());
		line.layoutYProperty().bind(textLabel.heightProperty());
		this.text = new SimpleStringProperty(string);
		textLabel.textProperty().bindBidirectional(text);
		this.getStyleClass().add("LineWithText");
		line.getStyleClass().add("path");
	}

	@Override
	protected DoubleExpression generateLayoutYToStubYExpression() {
		return textLabel.heightProperty();
	}

	public void setText(String string){
		text.set(string);
	}
	
	public String getText(){
		return text.get();
	}
}
