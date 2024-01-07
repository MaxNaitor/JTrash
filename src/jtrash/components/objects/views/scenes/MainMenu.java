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

/**
 * Classe che rappresenta il menu principale dell'applicazione
 * 
 * @author tizia
 *
 */
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

		VBox menu = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(boxTitolo, boxSottotitolo));

		aggiungiSezioneGiocatoreAttivo(menu);

		aggiungiSezioneGiocatori(menu);

		ComboBox<Integer> selettoreAvversari = generaSelettoreAvversari(menu);

		generaTastoGioca(selettoreAvversari, menu);

		menu.setSpacing(20);

		mainMenu.add(menu, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}


	/**
	 * Aggiunge al menu la sezione dell'utente attivo con relative statistiche, se
	 * c'è
	 * 
	 * @param menu
	 */
	private void aggiungiSezioneGiocatoreAttivo(VBox menu) {
		if (utentiHandler.getUtenteAttivo() != null) {
			menu.getChildren().add(BoxFactory.getBoxUtente());
			Text partiteGiocate = TextFactory.generaTesto(utentiHandler.getStatisticaGiocatore(false), Color.WHITE);

			Text partiteVinte = TextFactory.generaTesto(utentiHandler.getStatisticaGiocatore(true), Color.WHITE);

			if (utentiHandler.getUtenteAttivo() != null) {
				VBox statisticheUtente = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(partiteGiocate, partiteVinte));

				menu.getChildren().add(statisticheUtente);
			}
		}
	}

	/**
	 * Aggiunge la sezione della selezione degli utenti già registrati
	 * 
	 * @param menu
	 */
	private void aggiungiSezioneGiocatori(VBox menu) {

		Button tastoRegistraUtente = ButtonFactory.generaTasto("Registra Utente",
				e -> ModalHandler.getInstance().mostraModaleRegistrazioneUtente());
		menu.getChildren().add(tastoRegistraUtente);

		if (!utentiHandler.getUtentiRegistrati().isEmpty()) {
			Text utentiRegistratiText = TextFactory.generaTesto("Seleziona utente già registrato:", Color.WHITE);
			menu.getChildren().add(utentiRegistratiText);

			ObservableList<Utente> utenti = FXCollections.observableArrayList(utentiHandler.getUtentiRegistrati());
			ComboBox<Utente> selettoreUtentiRegistrati = new ComboBox<>(utenti);
			selettoreUtentiRegistrati.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					utentiHandler.setUtenteAttivo(selettoreUtentiRegistrati.getValue());
					SceneFactory.getInstance()
							.cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
				}
			});
			menu.getChildren().add(selettoreUtentiRegistrati);
		}
	}

	/**
	 * Genera e aggiunge al menu il selettore degli avversari e la sua label, impostando di default un
	 * avversario selezionato
	 * 
	 * @param menu
	 * @return ComboBox
	 */
	private ComboBox<Integer> generaSelettoreAvversari(VBox menu) {
		Text numeroAvversariText = TextFactory.generaTesto("Numero avversari:", Color.WHITE);

		menu.getChildren().add(numeroAvversariText);

		ObservableList<Integer> avversari = FXCollections.observableArrayList(1, 2, 3);
		ComboBox<Integer> selettoreAvversari = new ComboBox<>(avversari);
		selettoreAvversari.setValue(1);
		menu.getChildren().add(selettoreAvversari);
		return selettoreAvversari;
	}
	
	/**
	 * Genera e aggiunge al menu il tasto gioca, con annessa gestione dell'abilitazione del tasto
	 * @param selettoreAvversari
	 * @param menu
	 * @return Button
	 */
	private Button generaTastoGioca(ComboBox<Integer> selettoreAvversari, VBox menu) {
		Button tastoGioca = ButtonFactory.generaTasto("Gioca", azioneIniziaPartita(
				SceneFactory.getInstance().creaScena(SchermataDiGioco.getInstance().getSchermataDiGioco()), selettoreAvversari));
		disableTastoGioca(tastoGioca, selettoreAvversari);

		selettoreAvversari.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				disableTastoGioca(tastoGioca, selettoreAvversari);
			}
		});
		menu.getChildren().add(tastoGioca);
		return tastoGioca;
	}

	/**
	 * Disabilita il tasto gioca se non c'è un utente attivo o non è stato
	 * selezionato il numero di avversari desiderato
	 * 
	 * @param tastoGioca
	 * @param selettoreAvversari
	 */
	private void disableTastoGioca(Button tastoGioca, ComboBox<Integer> selettoreAvversari) {
		if (tastoGioca == null || selettoreAvversari == null || selettoreAvversari.getValue() == null)
			return;

		int avversariSelezionati = selettoreAvversari.getValue();
		boolean utenteSelected = utentiHandler.getUtenteAttivo() != null;

		tastoGioca.setDisable(!(avversariSelezionati >= 1 && utenteSelected));
	}

	/**
	 * Genera l'azione che da inizio alla partita, impostando il gioco tramite
	 * GameHandler e mostrando la scena di gioco
	 * 
	 * @param scene
	 * @param selettoreAvversari
	 * @return EventHandler
	 */
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
