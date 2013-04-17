package com.jackmoxley.moxy.renderer.javafx.component;


import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class TextWithRectangle extends StubPane{

	public final Label text;
	public final Rectangle rectangle;
	
	
	public TextWithRectangle() {
		this("");
	}
	
	public TextWithRectangle(String string) {
		super();
		this.getStyleClass().add("textbox");
		this.getStyleClass().add("TextWithRectangle");
		text = new Label();
		text.getStyleClass().add("text");
		rectangle = new Rectangle();
		rectangle.getStyleClass().add("box");
		this.getChildren().add(rectangle);
		this.getChildren().add(text);
		setString(string);
		rectangle.widthProperty().bind(text.widthProperty());
		rectangle.heightProperty().bind(text.heightProperty());
		rectangle.layoutXProperty().bindBidirectional(text.layoutXProperty());
		rectangle.layoutYProperty().bindBidirectional(text.layoutYProperty());
	}


	public void setString(String string){
		text.setText(string);
	}
	
	public String getString(){
		return text.getText();
	}
	
	public StringProperty textProperty(){
		return text.textProperty();
	}
}
