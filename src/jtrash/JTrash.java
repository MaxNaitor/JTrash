package jtrash;

import javafx.application.Application;
import javafx.stage.Stage;
import jtrash.components.factories.SceneFactory;
import jtrash.components.objects.controllers.AudioManager;
import jtrash.components.objects.views.scenes.MainMenu;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.MUSICA_ENUM;

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
		AudioManager.getInstance().play(FOLDERS_ENUM.MUSICA.getFolderLocation() + MUSICA_ENUM.MUSICA_PRINCIPALE.getNomeCanzone());
	}

}
