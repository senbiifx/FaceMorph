package com.fxperiments;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dialog {
	public static void showErrorMessage(String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
