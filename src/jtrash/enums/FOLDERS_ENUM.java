package jtrash.enums;

public enum FOLDERS_ENUM {
	
	IMMAGINI("resources/immagini/");

	private String folderLocation;

	private FOLDERS_ENUM(String folderLocation) {
		this.folderLocation = folderLocation;
	}

	public String getFolderLocation() {
		return folderLocation;
	}

}
