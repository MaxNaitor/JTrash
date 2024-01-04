package jtrash.components.scenes;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;
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
			
//			for (Node g : playground.getChildren()) {
//				VBox v = (VBox)g;
//				System.out.println(g.getId());
//				for (Node fila : v.getChildren()) {
//					HBox h = (HBox)fila;
//					for (Node carta : h.getChildren()) {
//						Carta c = (Carta) carta;
//						System.out.println(c.getValore());
//					}
//				}
//			}
		}
	}

	private VBox generaBoxPlayer(int numeroPlayer, boolean startGame) {
		Player player = gameHandler.getGiocatori().get(numeroPlayer - 1);
		if (startGame)
			player.setCarte(gameHandler.getMazzo().distribuisciMano());
		VBox boxPlayer = PlayerFactory.generaCampoPlayer(player);
		boxPlayer.getChildren().add(TextFactory.generaTesto(player.getNome(), Color.WHITE, FontWeight.BOLD, 15));
		boxPlayer.setId(player.getNome());
		return boxPlayer;
	}
}
