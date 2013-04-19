package com.jackmoxley.moxy.renderer.javafx.component;

import java.util.List;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.jackmoxley.moxy.renderer.javafx.property.BidirectionalDifferenceBinding;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssMetaData;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssMetaDataList;
import com.jackmoxley.moxy.renderer.javafx.property.style.RegisteredCssDoubleProperty;

public class StubPane extends Pane implements ChangeListener<Bounds> {

	private DoubleProperty stubY;
	private DoubleProperty endX;
	private DoubleProperty endY;
	private DoubleExpression layoutToStubY;

	
	private final DoubleProperty gapProperty = new RegisteredCssDoubleProperty<StubPane>(
			this, "gap");

	private static final RegisteredCssMetaDataList cssMetaDataList = new RegisteredCssMetaDataList(
			Pane.getClassCssMetaData(), new RegisteredCssMetaData<>(
					"-moxy-gap", StyleConverter.getSizeConverter(),
					StubPane.class, "gap"));

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return cssMetaDataList.getMetaData();
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return getClassCssMetaData();
	}

	public StubPane() {
		super();

		getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> c) {

				while (c.next()) {
					for (Node node : c.getAddedSubList()) {
						node.boundsInLocalProperty().addListener(StubPane.this);
					}
				}
				requestLayout();
			}
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onMouseClicked(event);
			}
		});
	}

	protected void onMouseClicked(MouseEvent event) {
		System.out.println("lY:" + layoutYProperty().intValue() + " sY:"
				+ stubYProperty().intValue() + " diff:"
				+ layoutToStubYProperty().intValue() + " height:"
				+ heightProperty().intValue() + " classes:"
				+ StubPane.this.getStyleClass());
	}

	@Override
	public void changed(ObservableValue<? extends Bounds> observable,
			Bounds oldValue, Bounds newValue) {
		this.requestLayout();
	}

	protected DoubleExpression generateLayoutYToStubYExpression() {
		return this.heightProperty().divide(2);
	}

	public DoubleProperty stubYProperty() {
		if (stubY == null) {
			stubY = new SimpleDoubleProperty(0);
			BidirectionalDifferenceBinding.bind(layoutYProperty(), stubY,
					layoutToStubYProperty());
		}
		return stubY;
	}

	public DoubleProperty endXProperty() {
		if (endX == null) {
			endX = new SimpleDoubleProperty(0);
			BidirectionalDifferenceBinding.bind(layoutXProperty(), endX,
					widthProperty());
		}
		return endX;
	}

	public DoubleProperty endYProperty() {
		if (endY == null) {
			endY = new SimpleDoubleProperty(0);
			BidirectionalDifferenceBinding.bind(layoutYProperty(), endY,
					heightProperty());
		}
		return endY;
	}

	public DoubleProperty gapProperty() {
		return gapProperty;
	}
	public double getGap() {
		return gapProperty.get();
	}
	public void setGap(double value) {
		gapProperty.set(value);
	}
	
	public DoubleExpression layoutToStubYProperty() {
		if (layoutToStubY == null) {
			layoutToStubY = generateLayoutYToStubYExpression();
		}
		return layoutToStubY;
	}

}