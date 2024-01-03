package jtrash.components.objects;

public class Utente {
	private String username;

	private int partiteGiocate = 0;

	private int partiteVinte = 0;

	public Utente(String username) {
		super();
		this.username = username;
	}
	
	public void handleFineParita(boolean vittoria) {
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

}
