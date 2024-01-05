package jtrash.components.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.SceneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.objects.Utente;
import jtrash.components.scenes.MainMenu;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class UtentiHandler {

	private static UtentiHandler instance;

	private UtentiHandler() {

	}

	private Utente utenteAttivo;

	private List<Utente> utentiRegistrati = new ArrayList<>();

	private String usernameUtente;
	private Rectangle avatarSelezionato;

	Button pulsanteRegistra;

	public static UtentiHandler getInstance() {
		if (instance == null) {
			instance = new UtentiHandler();
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
			usernameUtente = newValue;
			enableRegistra();
		});

		Text scegliAvatarText = TextFactory.generaTesto("Scegli un Avatar:", Color.WHITE, FontWeight.BOLD, 30);
		HBox avatarBox = BoxFactory.generaBoxOrizzontaleBottoni(ButtonFactory.generaTastiSelezioneAvatar());
		avatarBox.setAlignment(Pos.CENTER);

		pulsanteRegistra = ButtonFactory.generaTasto("Registra", e -> {
			registraUtente();
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

	public void handleFineParita(boolean vittoria) {
		utenteAttivo.handleFineParita(vittoria);
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
	}

	private void registraUtente() {
		utenteAttivo = new Utente(usernameUtente, avatarSelezionato);
		utentiRegistrati.add(utenteAttivo);
		usernameUtente = null;
		avatarSelezionato = null;
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
	}

	public Utente getUtenteAttivo() {
		return utenteAttivo;
	}

	public String getStatisticaGiocatore(boolean isPartiteVinte) {
		if (isPartiteVinte) {
			return utenteAttivo != null
					? "Partite vinte da " + utenteAttivo.getUsername() + ": " + utenteAttivo.getPartiteVinte()
					: "";
		}
		return utenteAttivo != null
				? "Partite giocate da " + utenteAttivo.getUsername() + ": " + utenteAttivo.getPartiteGiocate()
				: "";
	}

	private void enableRegistra() {
		if (usernameUtente != null && !usernameUtente.isBlank() && avatarSelezionato != null) {
			pulsanteRegistra.setDisable(false);
		} else {
			pulsanteRegistra.setDisable(true);
		}
	}

	public List<Utente> getUtentiRegistrati() {
		return utentiRegistrati;
	}

	public void setUtentiRegistrati(List<Utente> utentiRegistrati) {
		this.utentiRegistrati = utentiRegistrati;
	}

	public Rectangle getAvatarSelezionato() {
		return avatarSelezionato;
	}

	public void setAvatarSelezionato(Rectangle avatarSelezionato) {
		this.avatarSelezionato = avatarSelezionato;
		enableRegistra();
	}

	public void setUtenteAttivo(Utente utenteAttivo) {
		this.utenteAttivo = utenteAttivo;
	}

}
