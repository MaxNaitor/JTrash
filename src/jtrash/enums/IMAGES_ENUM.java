package jtrash.enums;

public enum IMAGES_ENUM {
	SFONDO_PRINCIPALE("Trash.png");

	private String nomeImmagine;

	private IMAGES_ENUM(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	public String getNomeImmagine() {
		return nomeImmagine;
	}

}
