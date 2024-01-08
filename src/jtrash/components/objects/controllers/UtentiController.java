package jtrash.components.objects.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Rectangle;
import jtrash.components.factories.SceneFactory;
import jtrash.components.objects.models.Utente;
import jtrash.components.objects.views.scenes.MainMenu;
import jtrash.components.objects.views.scenes.RegistrazioneUtente;

/**
 * Controller di gestione degli utenti. Tiene traccia dell'utente attivo, dell'utente che si sta registrando e degli utenti già registrati
 * @author tizia
 *
 */
public class UtentiController {

	private static UtentiController instance;

	private UtentiController() {

	}

	private Utente utenteAttivo;

	private List<Utente> utentiRegistrati = new ArrayList<>();

	private String usernameUtente;
	private Rectangle avatarSelezionato;

	public static UtentiController getInstance() {
		if (instance == null) {
			instance = new UtentiController();
		}
		return instance;
	}

	/**
	 * Gestisce la fine della partita per l'utente attivo e ritorna al menu principale
	 * @param vittoria
	 */
	public void handleFinePartita(boolean vittoria) {
		utenteAttivo.handleFinePartita(vittoria);
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
	}

	/**
	 * Registra l'utente creando una nuova istanza, impostandola come utente attivo e aggiungendolo alla lista degli utenti registrati. <br>
	 * Ritorna al menu principale aggiornandone lo stato.
	 */
	public void registraUtente() {
		utenteAttivo = new Utente(usernameUtente, avatarSelezionato);
		utentiRegistrati.add(utenteAttivo);
		usernameUtente = null;
		avatarSelezionato = null;
		SceneFactory.getInstance().cambiaScena(SceneFactory.getInstance().creaScena(MainMenu.getInstance().getMenu()));
	}

	/**
	 * Ritorna la statistica di partite vinte o partite giocate dell'utente attivo, in base al valore booleano passato in input.
	 * @param isPartiteVinte
	 * @return String
	 */
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

	/**
	 * Imposta l'avatar selezionato dall'utente in registrazione e verifica la possibilità di cliccare il tasto registra sulla view
	 * @param avatarSelezionato
	 */
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
	
	public Utente getUtenteAttivo() {
		return utenteAttivo;
	}


}
