package com.fxperiments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SelectImageController implements Initializable{
	
	private static final int MARKER_OFFSET = 5;
	private ImageModel model = new ImageModel();
	
	@FXML
	private Button btnImage;
	@FXML
	private Button btnClear;
	@FXML
	private ImageView imageView;
	@FXML
	private Pane imageMarkerPane;
	private Logger logger = Logger.getGlobal();

	public ImageModel getModel() {
		return model;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.imageProperty().bindBidirectional(model.value);
	}
	
	@FXML
	private void selectImage(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) imageView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null) {
            try {
            	imageView.setImage(new Image(new FileInputStream(selectedFile), 250, 250, false, false));
            	model.markers.clear();
            } catch (FileNotFoundException e) {
            	logger.log(Level.SEVERE, "Error loading the selected file.", e);
            	Dialog.showErrorMessage("Error loading the selected file.");
            }
        }
	}
	
	@FXML
	private void imageAddMarker(MouseEvent mouseEvent) {
		if(imageView.getImage()!=null){
			Label label = createMarkerLabel(model.nextMarker(), mouseEvent.getX() -  MARKER_OFFSET, mouseEvent.getY() - 5);
			imageMarkerPane.getChildren().add(label);
			model.addMarker(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
		}
	}
	@FXML
	private void clearMarkers(){
		model.markers.clear();
		imageMarkerPane.getChildren().clear();
	}
	
	public void setModel(ImageModel model){
		imageView.imageProperty().unbind();
		this.model = model;
		imageView.imageProperty().bindBidirectional(model.value);
		
		for (int i = 0; i < model.markers.size(); i++) {
			Point2D p = model.markers.get(i);
			Label label = createMarkerLabel(String.valueOf(i + 1), p.getX() - MARKER_OFFSET, p.getY() - MARKER_OFFSET);
			imageMarkerPane.getChildren().add(label);
		}
	}
	
	private Label createMarkerLabel(String text, double x, double y){
		Label label = new Label(text);
		label.getStyleClass().add("marker");
		label.setTranslateX(x);
		label.setTranslateY(y);
		return label;
	}
	
}
