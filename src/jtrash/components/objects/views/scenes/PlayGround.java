package jtrash.components.objects.views.scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.controllers.GameController;
import jtrash.components.objects.models.Player;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

/**
 * Classe che rappresenta il campo da gioco, dove i giocatori dispongono le carte
 * @author tizia
 *
 */
public class PlayGround {

	private static PlayGround instance;
	private static GameController gameHandler = GameController.getInstance();

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

	/**
	 * Aggiorna il campo da gioco disponendo le carte in funzione dei giocatori in partita. <br>
	 * Se invocato all'inizio della partita, il metodo genera solo carte coperte e invoca la gestione dei turni per iniziare la partita. <br>
	 * Se invocato durante la partita, il metodo serve ad aggiornare le carte in modo da mostrare scoperte le carte che sono state posizionate
	 * @param startGame
	 */
	public void updatePlayground(boolean startGame) {
		if (!GameController.getInstance().getGiocatori().isEmpty()) {
			playground.getChildren().clear();
			switch (GameController.getInstance().getGiocatori().size()) {
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

	/**
	 * Genera la sezione di campo di ogni giocatore, in base al numero che lo rappresenta e al fatto se stiamo iniziando una nuova partita
	 * o è ancora in corso
	 * @param numeroPlayer
	 * @param startGame
	 * @return VBox
	 */
	private VBox generaBoxPlayer(int numeroPlayer, boolean startGame) {
		Player player = gameHandler.getGiocatori().get(numeroPlayer - 1);
		if (startGame)
			player.setCarte(gameHandler.getMazzo().distribuisciMano(player.getNumeroCarteTurno()));
		VBox boxCampoPlayer = PlayerFactory.generaCampoPlayer(player);
		
		HBox boxInfoGiocatore = BoxFactory.getBoxGiocatore(player, 15);
		boxInfoGiocatore.setAlignment(Pos.CENTER);
		
		boxCampoPlayer.getChildren().add(boxInfoGiocatore);
		boxCampoPlayer.setId(player.getNome());
		return boxCampoPlayer;
	}
}
