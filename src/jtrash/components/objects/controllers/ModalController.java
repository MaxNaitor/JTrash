package jtrash.components.objects.controllers;

import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import jtrash.components.factories.SceneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.objects.views.scenes.RegistrazioneUtente;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

/**
 * Controller per gestire la visualizzazione di modali
 * @author tizia
 *
 */
public class ModalController {

	private static ModalController instance;

	private ModalController() {

	}

	public static ModalController getInstance() {
		if (instance == null) {
			instance = new ModalController();
		}
		return instance;
	}

	/**
	 * Mostra un modale che informa l'utente di un evento specificato in input come testo. <br>
	 * Contiene un tasto "continua" che chiude il modale
	 * @param titolo del modale
	 * @param testo del modale
	 */
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

		modalStage.setScene(SceneFactory.getInstance().creaScenaSemplice(modalLayout));

		modalStage.showAndWait();
	}

	/**
	 * Mostra un modale contenente la vista per la registrazione degli utenti
	 */
	public void mostraModaleRegistrazioneUtente() {
		Stage modalStage = getModalStage("Registrazione Utente");

		modalStage.setScene(SceneFactory.getInstance()
				.creaScenaSemplice(RegistrazioneUtente.getInstance().getRegistrazioneUtenteLayout(modalStage)));

		modalStage.showAndWait();
	}

	/**
	 * Restituisce lo Stage di base da mostrare in un modale, con titolo in input
	 * 
	 * @param titolo
	 * @return Stage
	 */
	private Stage getModalStage(String titolo) {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);
		modalStage.setTitle(titolo);
		modalStage.setMinWidth(500);
		modalStage.setMinHeight(500);

		return modalStage;
	}
}
