package jtrash.enums;

/**
 * Rappresentazione delle immagini per gli avatar
 * @author tizia
 *
 */
public enum AVATAR_ENUM {
	AVATAR_BLU("blu.jpg"),
	AVATAR_GIALLO("giallo.jpg"),
	AVATAR_ROSSO("rosso.jpg"),
	AVATAR_VIOLA("viola.jpg"),
	AVATAR_BOT("bot.jpg");

	private String nomeImmagine;

	private AVATAR_ENUM(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	public String getNomeImmagine() {
		return nomeImmagine;
	}

}
