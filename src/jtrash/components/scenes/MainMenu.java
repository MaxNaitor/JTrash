package jtrash.components.scenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import jtrash.components.objects.Utente;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class MainMenu {

	private static MainMenu instance;

	private Utente utenteAttivo;

	private List<Utente> utentiRegistrati = new ArrayList<>();

	private MainMenu() {

	}

	public static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
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

		Text inserisciNomeGiocatoreText = TextFactory.generaTesto("Inserisci username:", Color.WHITE);

		TextField inputNomeGiocatore = new TextField(
				utenteAttivo != null && utenteAttivo.getUsername() != null ? utenteAttivo.getUsername() : "Player");

		VBox boxVerticale = BoxFactory.generaBoxVerticaleNodi(
				Arrays.asList(boxTitolo, boxSottotitolo, inserisciNomeGiocatoreText, inputNomeGiocatore));

		if (utenteAttivo != null) {
			VBox statisticheUtente = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(
					TextFactory.generaTesto("Partite giocate da " + utenteAttivo.getUsername() + ": " + utenteAttivo.getPartiteGiocate(), Color.WHITE),
					TextFactory.generaTesto("Partite vinte da " + utenteAttivo.getUsername() + ": " + utenteAttivo.getPartiteVinte(), Color.WHITE)));

			boxVerticale.getChildren().add(statisticheUtente);
		}

		if (!utentiRegistrati.isEmpty()) {
			Text utentiRegistratiText = TextFactory.generaTesto("Seleziona utente già registrato:", Color.WHITE);
			boxVerticale.getChildren().add(utentiRegistratiText);
			
			ObservableList<Utente> utenti = FXCollections.observableArrayList(utentiRegistrati);
			ComboBox<Utente> selettoreUtentiRegistrati = new ComboBox<>(utenti);
			boxVerticale.getChildren().add(selettoreUtentiRegistrati);
		}

		Text numeroAvversariText = TextFactory.generaTesto("Numero avversari:", Color.WHITE);

		boxVerticale.getChildren().add(numeroAvversariText);

		ObservableList<Integer> avversari = FXCollections.observableArrayList(1, 2, 3);
		ComboBox<Integer> selettoreAvversari = new ComboBox<>(avversari);

		boxVerticale.getChildren().add(selettoreAvversari);

		Button tastoGioca = ButtonFactory.generaTasto("Gioca",
				ActionEventFactory.azioneIniziaPartita(
						SceneFactory.getInstance().creaScena(Gioco.getInstance().getGioco()), inputNomeGiocatore,
						selettoreAvversari));
		boxVerticale.getChildren().add(tastoGioca);

		mainMenu.add(boxVerticale, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}

	public void handleFineParita(boolean vittoria) {
		utenteAttivo.handleFineParita(vittoria);
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(instance.getMenu()));
	}

	public void handleUtenteAttivo(String username) {
		if (utenteAttivo == null) {
			registraUtente(username);
		} else {
			List<Utente> utentiFiltrati = utentiRegistrati.stream()
					.filter(u -> u.getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
			if (utentiFiltrati.isEmpty()) {
				registraUtente(username);
			} else {
				utenteAttivo = utentiFiltrati.get(0);
			}
		}

	}

	private void registraUtente(String username) {
		utenteAttivo = new Utente(username);
		utentiRegistrati.add(utenteAttivo);
	}

	public Utente getUtenteAttivo() {
		return utenteAttivo;
	}

}
