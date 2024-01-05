package jtrash.components.handlers;

import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.TextFactory;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class ModalHandler {

	private static ModalHandler instance;

	private ModalHandler() {

	}

	public static ModalHandler getInstance() {
		if (instance == null) {
			instance = new ModalHandler();
		}
		return instance;
	}

	public void mostraModale(Stage modalStage) {
		modalStage.showAndWait();
	}

	public void mostraModaleInformativo(String titolo, String testo) {
		Stage modalStage = getModalStage(titolo);

		StackPane modalLayout = new StackPane();
		Text testoModale = TextFactory.generaTesto(testo, Color.WHITE, FontWeight.BOLD, 50);
		Button pulsanteContinua = ButtonFactory.generaTasto("Continua", e -> modalStage.close());

		VBox box = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(testoModale, pulsanteContinua));
		box.setAlignment(Pos.CENTER);

		modalLayout.getChildren().add(box);

		modalLayout.setBackground(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

		modalStage.setScene(new Scene(modalLayout));

		mostraModale(modalStage);
	}

	public void mostraModaleRegistrazioneUtente() {
		Stage modalStage = getModalStage("Registrazione Utente");

		StackPane modalLayout = new StackPane();

		Text inserisciNomeGiocatoreText = TextFactory.generaTesto("Inserisci username:", Color.WHITE, FontWeight.BOLD,30);
		TextField inputNomeGiocatore = new TextField("Player");
		inputNomeGiocatore.setMaxWidth(300);

		Text scegliAvatarText = TextFactory.generaTesto("Scegli un Avatar:", Color.WHITE,FontWeight.BOLD,30);
		HBox avatarBox = BoxFactory.generaBoxOrizzontaleBottoni(ButtonFactory.generaTastiSelezioneAvatar());
		avatarBox.setAlignment(Pos.CENTER);

		VBox box = BoxFactory.generaBoxVerticaleNodi(
				Arrays.asList(inserisciNomeGiocatoreText, inputNomeGiocatore, scegliAvatarText, avatarBox));
		box.setAlignment(Pos.CENTER);

		modalLayout.getChildren().add(box);

		modalLayout.setBackground(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

		modalStage.setScene(new Scene(modalLayout));

		mostraModale(modalStage);
	}

	private Stage getModalStage(String titolo) {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);
		modalStage.setTitle(titolo);
		modalStage.setMinWidth(500);
		modalStage.setMinHeight(500);

		return modalStage;
	}
}
