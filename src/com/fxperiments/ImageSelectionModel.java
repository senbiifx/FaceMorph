package com.fxperiments;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Point2D;

public class ImageSelectionModel {
	ImageModel fromImage;
	ImageModel toImage;
	BooleanProperty isReady = new SimpleBooleanProperty();
	
	public ImageSelectionModel(ImageModel fromImage, ImageModel toImage) {
		this.fromImage = fromImage;
		this.toImage = toImage;
		isReady.set(isReady());
		fromImage.markers.addListener(new ListChangeListener<Point2D>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Point2D> c) {
				isReady.set(isReady());
			}
		});
		
		toImage.markers.addListener(new ListChangeListener<Point2D>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Point2D> c) {
				isReady.set(isReady());
			}
		});
	}
	
	private boolean isReady(){
		return fromImage.value.isNotNull().get() && toImage.value.isNotNull().get() && fromImage.markers.size() == toImage.markers.size() && !fromImage.markers.isEmpty();
	}
}
