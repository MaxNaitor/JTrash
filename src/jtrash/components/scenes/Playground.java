package jtrash.components.scenes;

import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Mazzo;
import jtrash.components.objects.Player;
import jtrash.components.objects.box.CartaSelezionataBox;
import jtrash.components.objects.box.CarteMazzoBox;
import jtrash.components.objects.box.CarteScartateBox;
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

//	public GridPane getPlayground() {
//		mazzo = new Mazzo();
//
//		GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
//				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));
//
//		VBox player1 = PlayerFactory.generaCampoPlayer(mazzo.distribuisciMano());
//		VBox player2 = PlayerFactory.generaCampoPlayer(mazzo.distribuisciMano());
//
//		playground.add(player1, 15, 5); // cosa aggiungere, left-margin,top margin
//		playground.add(player2, 15, 15);
//		
//		GridPane.setRowIndex(player1, 0);
//		GridPane.setColumnIndex(player1, 0);
//		
//		GridPane.setRowIndex(player2, 5);
//		GridPane.setColumnIndex(player2, 0);
//		
//		return playground;
//	}

	public HBox getPlayground() {
		
		GameHandler gameHandler = GameHandler.getInstance();

		GridPane playground = GridPaneFactory.generaGridPane(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

		switch (GameHandler.getInstance().getGiocatori().size()) {
		case 2:
			Player player1 = gameHandler.getGiocatori().get(0);
			player1.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer1 = PlayerFactory.generaCampoPlayer(player1);
			boxPlayer1.getChildren().add(TextFactory.generaTesto(player1.getNome(), Color.WHITE, null, 0));
			Player player2 = gameHandler.getGiocatori().get(0);
			player2.setCarte(gameHandler.getMazzo().distribuisciMano());
			VBox boxPlayer2 = PlayerFactory.generaCampoPlayer(player2);
			boxPlayer2.getChildren().add(TextFactory.generaTesto(player2.getNome(), Color.WHITE, null, 0));
			playground.add(boxPlayer1, 15, 5); // cosa aggiungere, left-margin,top margin
			playground.add(boxPlayer2, 15, 15);
		}

//		GridPane.setRowIndex(player1, 0);
//		GridPane.setColumnIndex(player1, 0);
//		
//		GridPane.setRowIndex(player2, 5);
//		GridPane.setColumnIndex(player2, 0);

		
		//TODO gestire come componente singolo, servono vari observable e observer
		GridPane actionground = GridPaneFactory.generaGridPane(BackgroundFactory.generaBackground(
				FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.SFONDO_PRINCIPALE.getNomeImmagine()));
		

		Text testoCartaSelezionata = TextFactory.generaTesto("Carta selezionata", Color.WHITE, null, 0);
		Button tastoGiraCarta = ButtonFactory.generaTasto("Gira carta",gameHandler.getGiraCartaSelezionataEventHandler());
		
		Text testoCarteScartare = TextFactory.generaTesto("Carte scartate", Color.WHITE, null, 0);
		Text testoCarteMazzo = TextFactory.generaTesto("Mazzo", Color.WHITE, null, 0);
		VBox azioniActionGround = BoxFactory
				.generaBoxVerticaleNodi(
						Arrays.asList(CartaSelezionataBox.getInstance().getBox(),
								testoCartaSelezionata,
								tastoGiraCarta,
								CarteScartateBox.getInstance().getBox(),
								testoCarteScartare,
								CarteMazzoBox.getInstance().getBox(),
								testoCarteMazzo));
		//fine TODO gestire come componente singolo, servono vari observable e observer
	

		actionground.add(azioniActionGround,0,1);

		HBox box = BoxFactory.generaBoxOrizzontaleNodi(Arrays.asList(playground, actionground));
		HBox.setHgrow(playground, Priority.ALWAYS);
		return box;
	}
}
