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
	public EquilateralTriangle arrow = new EquilateralTriangle();
	
	
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
		textLabel.getStyleClass().add("text");
		
		arrow.layoutXProperty().bind(line.layoutXProperty().add(line.endXProperty().subtract(line.startXProperty())).subtract(arrow.topXProperty()).divide(2));
		arrow.layoutYProperty().bind(line.layoutYProperty());
		arrow.topXProperty().set(7);
		arrow.topYProperty().set(0);
		this.getChildren().add(arrow);
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

	public StringProperty textProperty(){
		return text;
	}
}
