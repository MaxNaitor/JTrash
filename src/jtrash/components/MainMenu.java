package jtrash.components;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import jtrash.components.factories.BackgroundFactory;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class MainMenu {

	private static MainMenu instance;

	private MainMenu() {

	}

	public static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}

	public BorderPane getMenu() {
		BorderPane mainMenu = new BorderPane();

		Group group = new Group();

		Button startButton = new Button("START");
		ChoiceBox<String> utentiChoiceBox = new ChoiceBox<String>(
				FXCollections.observableArrayList("First", "Second", "Third"));

		group.getChildren().add(startButton);
		group.getChildren().add(utentiChoiceBox);

		mainMenu.setBackground(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.SFONDO_PRINCIPALE.getNomeImmagine()));
		return mainMenu;
	}
}
