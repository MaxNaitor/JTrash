package jtrash.components.scenes;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtrash.components.factories.ActionEventFactory;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.SceneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.ModalHandler;
import jtrash.components.handlers.UtentiHandler;
import jtrash.components.objects.Utente;
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

		Button tastoGioca = ButtonFactory.generaTasto("Gioca", ActionEventFactory.azioneIniziaPartita(
				SceneFactory.getInstance().creaScena(Gioco.getInstance().getGioco()), selettoreAvversari));
		disableTastoGioca(tastoGioca, selettoreAvversari);

		selettoreAvversari.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				disableTastoGioca(tastoGioca, selettoreAvversari);
			}
		});

		boxVerticale.getChildren().add(selettoreAvversari);

		boxVerticale.getChildren().add(tastoGioca);

		mainMenu.add(boxVerticale, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}

	private void aggiungiSezioneGiocatoreAttivo(VBox boxVerticale) {
		if (utentiHandler.getUtenteAttivo() != null) {
			HBox boxUtenteAttivo = BoxFactory.generaBoxOrizzontaleNodi(Arrays.asList(TextFactory.generaTesto(
					"Ciao " + utentiHandler.getUtenteAttivo().getUsername() + "!", Color.WHITE, FontWeight.BOLD, 30)));
			boxUtenteAttivo.getChildren().add(utentiHandler.getUtenteAttivo().getAvatar());
			
			boxUtenteAttivo.setAlignment(Pos.CENTER_LEFT);
			
			boxVerticale.getChildren().add(boxUtenteAttivo);
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

}
