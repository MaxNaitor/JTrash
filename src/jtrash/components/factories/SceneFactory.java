package jtrash.components.factories;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneFactory {

	private static SceneFactory instance;

	private SceneFactory() {

	}

	public static SceneFactory getInstance() {
		if (instance == null) {
			instance = new SceneFactory();
		}
		return instance;
	}

	/**
	 * Lo stage principale utilizzato dall'applicazione
	 */
	private Stage stagePrimario;
	
	
	/**
	 * Crea una Scene contenete il Parent in input delle dimensioni dello schermo
	 * @param parent
	 * @return Scene
	 */
	public Scene creaScena(Parent parent) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		return new Scene(parent,bounds.getWidth(),bounds.getHeight());
	}
	
	
	/**
	 * Cambia la scena contenuta nello stage primario
	 * @param scena
	 */
	public void cambiaScena(Scene scena) {
		if (stagePrimario != null) {
			stagePrimario.setScene(scena);
		}
	}

	public Stage getStagePrimario() {
		return stagePrimario;
	}

	public void setStagePrimario(Stage stagePrimario) {
		this.stagePrimario = stagePrimario;
	}

}
