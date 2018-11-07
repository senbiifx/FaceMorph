package com.fxperiments;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class ImageModel {
	ObjectProperty<Image> value = new SimpleObjectProperty<>();
	ObservableList<Point2D> markers = FXCollections.observableArrayList();
	
	public ImageModel() {
		value.addListener(new ChangeListener<Image>(){
			@Override
			public void changed(ObservableValue<? extends Image> observable, Image oldValue, Image newValue) {
				if(newValue != null){
					markers.clear();
				}
			}
		});
	}
	
	public void addMarker(Point2D point){
		markers.add(point);
	}
	
	public String nextMarker(){
		return String.valueOf(markers.size() + 1);
	}
}
