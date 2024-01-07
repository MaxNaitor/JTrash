package jtrash.enums;

/**
 * Rappresentazione delle possibili cartelle a cui accedere per ottenere risorse
 * @author tizia
 *
 */
public enum FOLDERS_ENUM {

	IMMAGINI("resources/immagini/"), IMMAGINI_CARTE("resources/immagini/carte/"),
	IMMAGINI_AVATAR("resources/immagini/avatar/"), MUSICA("resources/musica/");

	private String folderLocation;

	private FOLDERS_ENUM(String folderLocation) {
		this.folderLocation = folderLocation;
	}

	public String getFolderLocation() {
		return folderLocation;
	}

}
