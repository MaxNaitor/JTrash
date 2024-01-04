package jtrash;

import javafx.application.Application;
import javafx.stage.Stage;
import jtrash.components.factories.SceneFactory;
import jtrash.components.scenes.MainMenu;

public class JTrash extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setMaximized(true);
		stage.setTitle("JTrash - Tiziano Massa");
		SceneFactory sceneFactory = SceneFactory.getInstance();
		sceneFactory.setStagePrimario(stage);
		stage.setScene(sceneFactory.creaScena(MainMenu.getInstance().getMenu()));
		stage.show();
	}

}
