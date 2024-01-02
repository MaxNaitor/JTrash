package jtrash.components.scenes;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Player;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class Playground {

	private static Playground instance;
	private static GameHandler gameHandler = GameHandler.getInstance();

	private static GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
			.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

	private Playground() {

	}

	public static Playground getInstance() {
		if (instance == null) {
			instance = new Playground();
		}
		return instance;
	}

	public GridPane getPlayground() {
		updatePlayground(true);
		gameHandler.handleTurno();
		return playground;
	}

	public void updatePlayground(boolean startGame) {

		switch (GameHandler.getInstance().getGiocatori().size()) {
		case 4:
			Player player4 = gameHandler.getGiocatori().get(0);
			if (startGame) player4.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer4 = PlayerFactory.generaCampoPlayer(player4);
			boxPlayer4.getChildren().add(TextFactory.generaTesto(player4.getNome(), Color.WHITE));
			playground.add(boxPlayer4, 15, 5); // cosa aggiungere, left-margin,top margin
		case 3:
			Player player3 = gameHandler.getGiocatori().get(0);
			if (startGame) player3.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer3 = PlayerFactory.generaCampoPlayer(player3);
			boxPlayer3.getChildren().add(TextFactory.generaTesto(player3.getNome(), Color.WHITE));
			playground.add(boxPlayer3, 15, 5); // cosa aggiungere, left-margin,top margin
		case 2:
			Player player1 = gameHandler.getGiocatori().get(0);
			if (startGame) player1.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer1 = PlayerFactory.generaCampoPlayer(player1);
			boxPlayer1.getChildren().add(TextFactory.generaTesto(player1.getNome(), Color.WHITE));

			Player player2 = gameHandler.getGiocatori().get(1);
			if (startGame) player2.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer2 = PlayerFactory.generaCampoPlayer(player2);
			boxPlayer2.getChildren().add(TextFactory.generaTesto(player2.getNome(), Color.WHITE));

			playground.add(boxPlayer1, 15, 5); // cosa aggiungere, left-margin,top margin
			playground.add(boxPlayer2, 15, 15);
		}

	}
}
