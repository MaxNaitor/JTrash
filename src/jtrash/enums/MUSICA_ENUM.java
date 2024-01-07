package jtrash.enums;

/**
 * Rappresentazione dei file musicali dell'applicazione
 * @author tizia
 *
 */
public enum MUSICA_ENUM {
	MUSICA_PRINCIPALE("music.wav");

	private String nomeCanzone;

	private MUSICA_ENUM(String nomeCanzone) {
		this.nomeCanzone = nomeCanzone;
	}

	public String getNomeCanzone() {
		return nomeCanzone;
	}

}
