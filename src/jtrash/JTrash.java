package jtrash;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jtrash.components.MainMenu;

public class JTrash extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("JTrash - Tiziano Massa");
		Scene scene = new Scene(MainMenu.getInstance().getMenu(), 1080, 720);
		stage.setScene(scene);
		stage.show();
	}

}
