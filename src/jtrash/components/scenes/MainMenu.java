package jtrash.components.scenes;

import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import jtrash.components.factories.ActionEventFactory;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.SceneFactory;
import jtrash.components.factories.TextFactory;
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

	public GridPane getMenu() {
		GridPane mainMenu = GridPaneFactory.generaGridPane(BackgroundFactory.generaBackground(
				FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.SFONDO_PRINCIPALE.getNomeImmagine()));

		HBox boxTitolo = BoxFactory.generaBoxOrizzontaleNodi(
				Arrays.asList(TextFactory.generaTesto("JTrash", Color.WHITE, FontWeight.BOLD, 75)));
		HBox boxSottotitolo = BoxFactory.generaBoxOrizzontaleNodi(Arrays.asList(
				TextFactory.generaTesto("Tiziano Massa - Matricola 2067791", Color.WHITE, FontWeight.THIN, 30)));

		Button tastoGioca = ButtonFactory.generaTasto("Gioca", ActionEventFactory
				.azioneCambioScena(SceneFactory.getInstance().creaScena(Gioco.getInstance().getGioco())));

		VBox boxVerticale = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(boxTitolo, boxSottotitolo, tastoGioca));

		mainMenu.add(boxVerticale, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}

}
