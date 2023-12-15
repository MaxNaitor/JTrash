package jtrash.components.scenes;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.Mazzo;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class Playground {

	private static Playground instance;

	private Playground() {

	}

	public static Playground getInstance() {
		if (instance == null) {
			instance = new Playground();
		}
		return instance;
	}

	private Mazzo mazzo;

	public GridPane getPlayground() {
		mazzo = new Mazzo();

		GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

		VBox player1 = PlayerFactory.generaCampoPlayer(mazzo.distribuisciMano());
		VBox player2 = PlayerFactory.generaCampoPlayer(mazzo.distribuisciMano());

		playground.add(player1, 15, 5); // cosa aggiungere, left-margin,top margin
		playground.add(player2, 15, 15);

		return playground;
	}
}
