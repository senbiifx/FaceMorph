package com.fxperiments;

import com.fxperiments.router.FXRouter;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {
	@FXML 
	private Pane mainPane;
	
	@FXML
    private void initialize() {
        FXRouter.appRoot(mainPane);
        FXRouter.when("selectImages", "/selectImageScreen.fxml");
        FXRouter.when("result", "/result.fxml");
        
        FXRouter.goTo("selectImages");
	}
}
