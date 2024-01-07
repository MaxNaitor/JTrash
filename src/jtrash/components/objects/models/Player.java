package jtrash.components.objects.models;

import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.BoxFactory;

public class Player {

	private String nome;

	private boolean hasTrash = false;

	private boolean isBot = true;

	private Rectangle avatar;

	private List<Carta> carte;

	/**
	 * Distribuisce le carte del giocatore sul tavolo da gioco in due file (HBox) contente 5 carte ciascuna
	 * @return List HBox
	 */
	public List<HBox> distribuisciCarteSulTavolo() {
		HBox primaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(0, 5));
		HBox secondaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(5, 10));
		return Arrays.asList(primaFila, secondaFila);
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

}
