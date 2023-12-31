package jtrash.enums;

/**
 * Rappresentazione delle immagini di base da mostrare durante la partita e nel menu principale
 * @author tizia
 *
 */
public enum IMAGES_ENUM {
	SFONDO_PRINCIPALE("Trash.png"),
	TAVOLO("tavolo.jpg"),
	RETRO_CARTE("retro.png");

	private String nomeImmagine;

	private IMAGES_ENUM(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	public String getNomeImmagine() {
		return nomeImmagine;
	}

}
