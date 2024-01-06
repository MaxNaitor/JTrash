package jtrash.components.objects.views.scenes;

import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.objects.handlers.UtentiHandler;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class RegistrazioneUtente {

	private static RegistrazioneUtente instance;

	private static UtentiHandler utentiHandler;

	Button pulsanteRegistra;

	private RegistrazioneUtente() {

	}

	public static RegistrazioneUtente getInstance() {
		if (instance == null) {
			instance = new RegistrazioneUtente();
			utentiHandler = UtentiHandler.getInstance();
		}

		return instance;
	}

	public StackPane getRegistrazioneUtenteLayout(Stage modalStage) {
		StackPane modalLayout = new StackPane();

		Text inserisciNomeGiocatoreText = TextFactory.generaTesto("Inserisci username:", Color.WHITE, FontWeight.BOLD,
				30);
		TextField inputNomeGiocatore = new TextField();
		inputNomeGiocatore.setMaxWidth(300);
		inputNomeGiocatore.textProperty().addListener((observable, oldValue, newValue) -> {
			utentiHandler.setUsernameUtente(newValue);
			enableRegistra();
		});

		Text scegliAvatarText = TextFactory.generaTesto("Scegli un Avatar:", Color.WHITE, FontWeight.BOLD, 30);
		HBox avatarBox = BoxFactory.generaBoxOrizzontaleBottoni(ButtonFactory.generaTastiSelezioneAvatar());
		avatarBox.setAlignment(Pos.CENTER);

		pulsanteRegistra = ButtonFactory.generaTasto("Registra", e -> {
			utentiHandler.registraUtente();
			modalStage.close();
		});
		pulsanteRegistra.setDisable(true);

		VBox box = BoxFactory.generaBoxVerticaleNodi(Arrays.asList(inserisciNomeGiocatoreText, inputNomeGiocatore,
				scegliAvatarText, avatarBox, pulsanteRegistra));
		box.setAlignment(Pos.CENTER);

		modalLayout.getChildren().add(box);

		modalLayout.setBackground(BackgroundFactory
				.generaBackground(FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.TAVOLO.getNomeImmagine()));

		return modalLayout;
	}

	public void enableRegistra() {
		if (utentiHandler.getUsernameUtente() != null && !utentiHandler.getUsernameUtente().isBlank()
				&& utentiHandler.getAvatarSelezionato() != null) {
			pulsanteRegistra.setDisable(false);
		} else {
			pulsanteRegistra.setDisable(true);
		}
	}
}
