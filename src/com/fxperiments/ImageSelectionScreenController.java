package com.fxperiments;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fxperiments.router.FXRouter;
import com.fxperiments.router.Param;
import com.fxperiments.router.RoutingSetup;
import com.fxperiments.router.Var;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ImageSelectionScreenController {
	private ImageSelectionModel model;
	private Logger logger = Logger.getGlobal();
	@FXML
	private HBox imagesPane;
	@FXML
	private Button btnNext;
	
	@RoutingSetup
	private void setup(@Var("model") ImageSelectionModel model) {
		try {
			SelectImageController controller1 = addImageSelector();
			SelectImageController controller2  = addImageSelector();
			if(model!=null){
				controller1.setModel(model.fromImage);
				controller2.setModel(model.toImage);
			}
			this.model = new ImageSelectionModel(controller1.getModel(), controller2.getModel());
			btnNext.disableProperty().bind(this.model.isReady.not());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Exception in loading fxmls", e);
			Dialog.showErrorMessage("Error loading fxmls");
		}
	}
	
	@FXML
	private void next(){
		FXRouter.goTo("result", Param.name("model")
									 .inject(model));
	}
	
	
	private SelectImageController addImageSelector() throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane root = fxmlLoader.load(getClass().getResource("/selectImage.fxml").openStream());
		SelectImageController controller = fxmlLoader.getController();
		imagesPane.getChildren().add(root);
		return controller;
	}

}
