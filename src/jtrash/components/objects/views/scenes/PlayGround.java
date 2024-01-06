package jtrash.components.objects.views.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.models.Player;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class PlayGround {

	private static PlayGround instance;
	private static GameHandler gameHandler = GameHandler.getInstance();

	private static GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
			.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

	private PlayGround() {

	}

	public static PlayGround getInstance() {
		if (instance == null) {
			instance = new PlayGround();
		}
		return instance;
	}

	public GridPane getPlayground() {
		return playground;
	}

	public void updatePlayground(boolean startGame) {
		if (!GameHandler.getInstance().getGiocatori().isEmpty()) {
			playground.getChildren().clear();
			switch (GameHandler.getInstance().getGiocatori().size()) {
			case 4:
				playground.add(generaBoxPlayer(4, startGame), 25, 10); // cosa aggiungere, left-margin,top margin
			case 3:
				playground.add(generaBoxPlayer(3, startGame), 25, 5); // cosa aggiungere, left-margin,top margin
			case 2:
				playground.add(generaBoxPlayer(1, startGame), 10, 5); // cosa aggiungere, left-margin,top margin
				playground.add(generaBoxPlayer(2, startGame), 10, 10);
			}
			if (startGame)
				gameHandler.handleTurno();
		}
	}

	private VBox generaBoxPlayer(int numeroPlayer, boolean startGame) {
		Player player = gameHandler.getGiocatori().get(numeroPlayer - 1);
		if (startGame)
			player.setCarte(gameHandler.getMazzo().distribuisciMano());
		VBox boxCampoPlayer = PlayerFactory.generaCampoPlayer(player);
		
		HBox boxInfoGiocatore = BoxFactory.getBoxGiocatore(player, 15);
		boxInfoGiocatore.setAlignment(Pos.CENTER);
		
		boxCampoPlayer.getChildren().add(boxInfoGiocatore);
		boxCampoPlayer.setId(player.getNome());
		return boxCampoPlayer;
	}
}