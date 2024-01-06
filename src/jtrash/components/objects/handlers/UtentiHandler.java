package jtrash.components.objects.handlers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Rectangle;
import jtrash.components.factories.SceneFactory;
import jtrash.components.objects.models.Utente;
import jtrash.components.objects.views.scenes.MainMenu;
import jtrash.components.objects.views.scenes.RegistrazioneUtente;

public class UtentiHandler {

	private static UtentiHandler instance;

	private UtentiHandler() {

	}

	private Utente utenteAttivo;

	private List<Utente> utentiRegistrati = new ArrayList<>();

	private String usernameUtente;
	private Rectangle avatarSelezionato;

	public static UtentiHandler getInstance() {
		if (instance == null) {
			instance = new UtentiHandler();
		}
		return instance;
	}

	public void handleFineParita(boolean vittoria) {
		utenteAttivo.handleFineParita(vittoria);
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
	}

	public void registraUtente() {
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
		if (utenteAttivo == null)
			return "";

		String intestazioneStatistica = isPartiteVinte ? "Partite vinte da " + utenteAttivo.getUsername() + ": "
				: "Partite giocate da " + utenteAttivo.getUsername() + ": ";

		String statistica = isPartiteVinte ? "" + utenteAttivo.getPartiteVinte()
				: "" + utenteAttivo.getPartiteGiocate();

		return intestazioneStatistica + statistica;
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
		RegistrazioneUtente.getInstance().enableRegistra();
	}

	public void setUtenteAttivo(Utente utenteAttivo) {
		this.utenteAttivo = utenteAttivo;
	}

	public String getUsernameUtente() {
		return usernameUtente;
	}

	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}

}
