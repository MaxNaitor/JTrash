package jtrash.components.objects.models;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.BoxFactory;

/**
 * Model di rappresentazione dei giocatori durante la partita
 * 
 * @author tizia
 *
 */
public class Player {

	private String nome;

	private boolean hasTrash = false;

	private boolean isBot = true;

	private Rectangle avatar;

	private List<Carta> carte;

	private int numeroCarteTurno = 10;

	/**
	 * Distribuisce le carte del giocatore sul tavolo da gioco in due file (HBox)
	 * in base al numero di carte che il giocatore ha a disposizione.
	 * 
	 * @return List HBox
	 */
	public List<HBox> distribuisciCarteSulTavolo() {
		List<HBox> lista = new ArrayList<>();
		
		HBox primaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(0, numeroCarteTurno > 5 ? 5 : numeroCarteTurno));
		
		lista.add(primaFila);
		
		if (numeroCarteTurno > 5) {
			HBox secondaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(5, numeroCarteTurno));
			lista.add(secondaFila);
		}
		
		return lista;
	}
	
	/**
	 * Quando un giocatore termina il round con un trash, diminuisce il numero di carte totali per il prossimo
	 */
	public void handleTrashFineRound() {
		numeroCarteTurno--;
	}

	public Player(List<Carta> carte, String nome, Rectangle avatar) {
		super();
		this.carte = carte;
		this.nome = nome;
		this.avatar = avatar;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Carta> getCarte() {
		return carte;
	}

	public void setCarte(List<Carta> carte) {
		this.carte = carte;
	}

	public boolean isHasTrash() {
		return hasTrash;
	}

	public void setHasTrash(boolean hasTrash) {
		this.hasTrash = hasTrash;
	}

	public boolean isBot() {
		return isBot;
	}

	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}

	public Rectangle getAvatar() {
		return avatar;
	}

	public void setAvatar(Rectangle avatar) {
		this.avatar = avatar;
	}

	public int getNumeroCarteTurno() {
		return numeroCarteTurno;
	}

	public void setNumeroCarteTurno(int numeroCarteTurno) {
		this.numeroCarteTurno = numeroCarteTurno;
	}

}
