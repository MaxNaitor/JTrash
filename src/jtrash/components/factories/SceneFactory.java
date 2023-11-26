package jtrash.components.factories;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneFactory {
	
	private static double WIDTH = 1080;
	private static double HEIGHT = 720;

	private static SceneFactory instance;

	private SceneFactory() {

	}

	public static SceneFactory getInstance() {
		if (instance == null) {
			instance = new SceneFactory();
		}
		return instance;
	}

	private Stage stagePrimario;
	
	public Scene creaScena(Parent parent) {
		return new Scene(parent,WIDTH,HEIGHT);
	}
	
	public void cambiaScena(Scene scena) {
		if (stagePrimario != null) stagePrimario.setScene(scena);
	}

	public Stage getStagePrimario() {
		return stagePrimario;
	}

	public void setStagePrimario(Stage stagePrimario) {
		this.stagePrimario = stagePrimario;
	}

}
