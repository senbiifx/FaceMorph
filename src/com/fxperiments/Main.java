package com.fxperiments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane root = fxmlLoader.load(getClass().getResource("/main.fxml").openStream());
    	Scene scene = new Scene(root, 554, 400);
        stage.setTitle("Face Morphing");
        stage.setScene(scene);	
        stage.setResizable(false);
        stage.show();
	}
    
}