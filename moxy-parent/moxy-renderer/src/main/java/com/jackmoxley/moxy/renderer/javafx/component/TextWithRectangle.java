package com.jackmoxley.moxy.renderer.javafx.component;


import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TextWithRectangle extends StubPane{

	public final Label text;
	public final Rectangle rectangle;
	
	
	public TextWithRectangle() {
		this("");
	}
	
	public TextWithRectangle(String string) {
		super();
		text = new Label();
		rectangle = new Rectangle();
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.WHITE);
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
}
