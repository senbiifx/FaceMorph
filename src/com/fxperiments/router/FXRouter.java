package com.fxperiments.router;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class FXRouter {
	private static Logger logger = Logger.getGlobal();
	private static Pane root;
	private static Map<String, String> whenMap = new HashMap<>();
	private static Scene scene;
	private FXRouter(){}
	
	public static void appRoot(Pane pane){
		root = pane;
	}
	
	public static void when(String name, String fxml){
		whenMap.put(name, fxml);
	}
	
	public static  <T> T goTo(String name){
		return goTo(name, new InjectableParam[]{});
	}
	
	public static <T> T goTo(String name, InjectableParam... iParam){
		String fxmlName = whenMap.get(name);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Pane pane = fxmlLoader.load(FXRouter.class.getResource(fxmlName).openStream());
			root.getChildren().clear();
			root.getChildren().add(pane);
			T controller = fxmlLoader.getController();
			
			Method[]  methods = controller.getClass().getDeclaredMethods();
			Arrays.asList(methods)
				  .stream()
				  .filter(m -> m.isAnnotationPresent(RoutingSetup.class))
				  .forEach( executeSetupMethod(controller, iParam));
			
			return controller;
		} catch (IOException e) {
			String error = "Error in FXRouter.goTo";
			logger.log(Level.SEVERE, error, e);
			throw new RoutingError(error);
		}
	}

	private static <T> Consumer<? super Method> executeSetupMethod(T controller, InjectableParam... iParam) {
		return m -> {
			  try {
				  m.setAccessible(true);
				  Parameter[] parameters = m.getParameters();
				  List<Object> list = IntStream.range(0, parameters.length)
						  .mapToObj( injectParams(parameters, iParam))
						  .collect(Collectors.toList());
				  Object[] paramValues = list.toArray(new Object[parameters.length]);
				  m.invoke(controller, paramValues);
			  } catch (InvocationTargetException | IllegalAccessException e) {
				  logger.log(Level.SEVERE, "Error in FXRouter.executeSetupMethod", e);
				  throw new RoutingError("Error in FXRouter.executeSetupMethod");
			  }
		  };
	}

	private static IntFunction<? extends Object> injectParams(Parameter[] parameters,
			InjectableParam... iParam) {
		return i -> {
			   Parameter p = parameters[i];
			   if(p.isAnnotationPresent(Var.class)){
				   Var var = p.getAnnotation(Var.class);
				   String value = var.value();
				   return  Arrays.asList(iParam)
		  				   		 .stream()
		  				   		 .filter( injectedParam -> value.equals(injectedParam.getName()) )
		  				   		 .findFirst()
		  				   		 .map(InjectableParam::getObject)
		  				   		 .orElseGet(() -> null);
			   }
			   return null;
		   };
	}
	
}
