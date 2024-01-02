package jtrash.components.scenes;

import java.util.Arrays;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Player;
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

	public GridPane getPlayground() {

		GameHandler gameHandler = GameHandler.getInstance();

		GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));
		
		switch (GameHandler.getInstance().getGiocatori().size()) {
		case 4:
			Player player4 = gameHandler.getGiocatori().get(0);
			player4.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer4 = PlayerFactory.generaCampoPlayer(player4);
			boxPlayer4.getChildren().add(TextFactory.generaTesto(player4.getNome(), Color.WHITE, null, 0));
			playground.add(boxPlayer4, 15, 5); // cosa aggiungere, left-margin,top margin
		case 3:
			Player player3 = gameHandler.getGiocatori().get(0);
			player3.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer3 = PlayerFactory.generaCampoPlayer(player3);
			boxPlayer3.getChildren().add(TextFactory.generaTesto(player3.getNome(), Color.WHITE, null, 0));
			playground.add(boxPlayer3, 15, 5); // cosa aggiungere, left-margin,top margin
		case 2:
			Player player1 = gameHandler.getGiocatori().get(0);
			player1.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer1 = PlayerFactory.generaCampoPlayer(player1);
			boxPlayer1.getChildren().add(TextFactory.generaTesto(player1.getNome(), Color.WHITE, null, 0));

			Player player2 = gameHandler.getGiocatori().get(1);
			player2.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer2 = PlayerFactory.generaCampoPlayer(player2);
			boxPlayer2.getChildren().add(TextFactory.generaTesto(player2.getNome(), Color.WHITE, null, 0));

			playground.add(boxPlayer1, 15, 5); // cosa aggiungere, left-margin,top margin
			playground.add(boxPlayer2, 15, 15);
		}

		gameHandler.handleTurno();
		
		return playground;
	}
}
