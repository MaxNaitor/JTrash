package jtrash.components.objects.models;

import javafx.scene.shape.Rectangle;

/**
 * Model di rappresentazione dell'utente che utilizza l'applicazione.
 * @author tizia
 *
 */
public class Utente {
	private String username;

	private int partiteGiocate = 0;

	private int partiteVinte = 0;

	private Rectangle avatar;

	public Utente(String username, Rectangle avatar) {
		super();
		this.username = username;
		this.avatar = avatar;
	}

	/**
	 * Incrementa il numero di partite giocate dall'utente e, nel caso abbia vinto (parametro = true), incrementa anche le partite vinte
	 * @param vittoria
	 */
	public void handleFinePartita(boolean vittoria) {
		partiteGiocate++;
		if (vittoria)
			partiteVinte++;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPartiteGiocate() {
		return partiteGiocate;
	}

	public void setPartiteGiocate(int partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}

	public int getPartiteVinte() {
		return partiteVinte;
	}

	public void setPartiteVinte(int partiteVinte) {
		this.partiteVinte = partiteVinte;
	}

	public Rectangle getAvatar() {
		return avatar;
	}

	public void setAvatar(Rectangle avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return username;
	}

}
