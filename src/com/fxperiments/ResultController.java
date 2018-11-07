package com.fxperiments;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.fxperiments.router.FXRouter;
import com.fxperiments.router.Param;
import com.fxperiments.router.RoutingSetup;
import com.fxperiments.router.Var;

import Facemorph.Template;
import Facemorph.Transformer;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class ResultController implements Initializable{
	private static final int STEP_COUNT = 20;
	@FXML
	private Slider slider;
	@FXML
	private ImageView imageView;
	private ImageSelectionModel model;
	private Logger logger = Logger.getGlobal();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		slider.setMax(STEP_COUNT - 1);
	}
	
	@RoutingSetup
	private void setup(@Var("model") ImageSelectionModel model){
		this.model = model;
		
		Task<List<Image>> task = new MorphingTask();
		
		task.setOnSucceeded(e -> {
			List<Image> images = task.getValue();
			setImage(images.get(0));
			slider.valueProperty().addListener((observable, oldValue, newValue) -> 
			setImage(images.get(newValue.intValue())));
		});
		
		task.setOnFailed( e -> Dialog.showErrorMessage("Error in processing the images."));
		
		new Thread(task).start();
	}
	
	@FXML
	private void back() {
		FXRouter.goTo("selectImages", Param.name("model").inject(model));
	}
	
	private void setImage(Image image){
		imageView.setImage(SwingFXUtils.toFXImage((BufferedImage)image, null));
	}
	
	private final class MorphingTask extends Task<List<Image>> {
		@Override
		protected List<Image> call() throws Exception {
			Template template1 = new Template();
			model.fromImage.markers.forEach( p -> template1.addPoint((int)p.getX(), (int)p.getY()));
			
			Template template2 = new Template();
			model.toImage.markers.forEach( p -> template2.addPoint((int)p.getX(), (int)p.getY()));
			
			Image image1 = SwingFXUtils.fromFXImage(model.fromImage.value.get(), null);
			Image image2 = SwingFXUtils.fromFXImage(model.toImage.value.get(), null);
			
			return Transformer.morph(image1, template1, image2, template2, STEP_COUNT, null, false);
		}
	}
}
