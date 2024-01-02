package jtrash.components.scenes;

import java.util.Arrays;

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
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class MainMenu {

	private static MainMenu instance;

	private String usernameUtente;

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

		TextField inputNomeGiocatore = new TextField("Player");
		
		Text numeroAvversariText = TextFactory.generaTesto("Numero avversari:", Color.WHITE);
		
		ObservableList<Integer> avversari = FXCollections.observableArrayList(
                1,2,3
        );
		ComboBox<Integer> selettoreAvversari = new ComboBox<>(avversari);

		Button tastoGioca = ButtonFactory.generaTasto("Gioca", ActionEventFactory.azioneIniziaPartita(
				SceneFactory.getInstance().creaScena(Gioco.getInstance().getGioco()), inputNomeGiocatore,selettoreAvversari));

		VBox boxVerticale = BoxFactory.generaBoxVerticaleNodi(
				Arrays.asList(boxTitolo, boxSottotitolo, inserisciNomeGiocatoreText, inputNomeGiocatore,numeroAvversariText,selettoreAvversari, tastoGioca));

		mainMenu.add(boxVerticale, 5, 5); // cosa aggiungere, left-margin,top margin

		return mainMenu;
	}

	public String getUsernameUtente() {
		return instance.usernameUtente;
	}

	public void setUsernameUtente(String usernameUtente) {
		instance.usernameUtente = usernameUtente;
	}

}
