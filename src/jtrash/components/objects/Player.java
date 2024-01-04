package jtrash.components.objects;

import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.HBox;
import jtrash.components.factories.BoxFactory;

public class Player {

	private String nome;

	private boolean hasTrash = false;

	private boolean isBot = true;

	private List<Carta> carte;

	public List<HBox> distribuisciCarteSulTavolo() {
		HBox primaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(0, 5));
		primaFila.setId("primaFila");
		HBox secondaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(5, 10));
		secondaFila.setId("secondaFila");
		return Arrays.asList(primaFila, secondaFila);
	}

	public List<HBox> distribuisciCarteSulTavoloButton() {
		HBox primaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(0, 5));
		HBox secondaFila = BoxFactory.generaBoxOrizzontaleCarte(carte.subList(5, 10));

		return Arrays.asList(primaFila, secondaFila);
	}

	public Player(List<Carta> carte, String nome) {
		super();
		this.carte = carte;
		this.nome = nome;
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

}
