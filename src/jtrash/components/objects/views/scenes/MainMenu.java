package jtrash.components.objects.views.scenes;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.SceneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.handlers.ModalHandler;
import jtrash.components.objects.handlers.UtentiHandler;
import jtrash.components.objects.models.Utente;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class MainMenu {

	private static MainMenu instance;

	private static UtentiHandler utentiHandler;

	private MainMenu() {

	}

	public static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
			utentiHandler = UtentiHandler.getInstance();
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

		VBox boxVerticale = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(boxTitolo, boxSottotitolo));

		aggiungiSezioneGiocatoreAttivo(boxVerticale);

		aggiungiSezioneGiocatori(boxVerticale);

		Text numeroAvversariText = TextFactory.generaTesto("Numero avversari:", Color.WHITE);

		boxVerticale.getChildren().add(numeroAvversariText);

		ObservableList<Integer> avversari = FXCollections.observableArrayList(1, 2, 3);
		ComboBox<Integer> selettoreAvversari = new ComboBox<>(avversari);

		Button tastoGioca = ButtonFactory.generaTasto("Gioca", azioneIniziaPartita(
				SceneFactory.getInstance().creaScena(SchermataDiGioco.getInstance().getGioco()), selettoreAvversari));
		disableTastoGioca(tastoGioca, selettoreAvversari);

		selettoreAvversari.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				disableTastoGioca(tastoGioca, selettoreAvversari);
			}
		});

		boxVerticale.getChildren().add(selettoreAvversari);

		boxVerticale.getChildren().add(tastoGioca);
		
		boxVerticale.setSpacing(20);

		mainMenu.add(boxVerticale, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}

	private void aggiungiSezioneGiocatoreAttivo(VBox boxVerticale) {
		if (utentiHandler.getUtenteAttivo() != null) {
			boxVerticale.getChildren().add(BoxFactory.getBoxUtente());
		}
	}

	private void aggiungiSezioneGiocatori(VBox boxVerticale) {
		Text partiteGiocate = TextFactory.generaTesto(utentiHandler.getStatisticaGiocatore(false), Color.WHITE);

		Text partiteVinte = TextFactory.generaTesto(utentiHandler.getStatisticaGiocatore(true), Color.WHITE);

		if (utentiHandler.getUtenteAttivo() != null) {
			VBox statisticheUtente = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(partiteGiocate, partiteVinte));

			boxVerticale.getChildren().add(statisticheUtente);
		}

		Button tastoRegistraUtente = ButtonFactory.generaTasto("Registra Utente",
				e -> ModalHandler.getInstance().mostraModaleRegistrazioneUtente());
		boxVerticale.getChildren().add(tastoRegistraUtente);

		if (!utentiHandler.getUtentiRegistrati().isEmpty()) {
			Text utentiRegistratiText = TextFactory.generaTesto("Seleziona utente già registrato:", Color.WHITE);
			boxVerticale.getChildren().add(utentiRegistratiText);

			ObservableList<Utente> utenti = FXCollections.observableArrayList(utentiHandler.getUtentiRegistrati());
			ComboBox<Utente> selettoreUtentiRegistrati = new ComboBox<>(utenti);
			selettoreUtentiRegistrati.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					utentiHandler.setUtenteAttivo(selettoreUtentiRegistrati.getValue());
					SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
				}
			});
			boxVerticale.getChildren().add(selettoreUtentiRegistrati);
		}
	}

	private void disableTastoGioca(Button tastoGioca, ComboBox<Integer> selettoreAvversari) {
		if (tastoGioca == null || selettoreAvversari == null || selettoreAvversari.getValue() == null)
			return;

		int avversariSelezionati = selettoreAvversari.getValue();
		boolean utenteSelected = utentiHandler.getUtenteAttivo() != null;

		tastoGioca.setDisable(!(avversariSelezionati >= 1 && utenteSelected));
	}
	
	private EventHandler<ActionEvent> azioneIniziaPartita(Scene scene, ComboBox<Integer> selettoreAvversari) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameHandler.getInstance().startNewGame(UtentiHandler.getInstance().getUtenteAttivo().getUsername(),
						selettoreAvversari.getValue());
				SceneFactory.getInstance().cambiaScena(scene);
			}
		};
	}

}
