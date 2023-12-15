package jtrash.components.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.paint.Color;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.objects.abstractions.CartaAbstract;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.SEMI_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

public class Mazzo extends CartaAbstract {

	List<Carta> carteCoperte = new ArrayList<>();
	List<Carta> carteScoperte = new ArrayList<>();

	public Mazzo() {
		// creo la Shape che rappresenterà il mazzo coperto
		generaCarta();

		for (SEMI_ENUM seme : SEMI_ENUM.values()) {
			// escludo i JOLLY e li inserisco dopo

			Stream<VALORI_CARTE_ENUM> filterStream = Arrays.asList(VALORI_CARTE_ENUM.values()).stream()
					.filter(value -> !value.equals(VALORI_CARTE_ENUM.JOLLY));
			for (VALORI_CARTE_ENUM valore : filterStream.collect(Collectors.toList())) {
				carteCoperte.add(new Carta(seme, valore));
			}

		}

		// aggiungo i due jolly, usando come colore un seme del colore corrispondente
		carteCoperte.add(new Carta(SEMI_ENUM.CUORI, VALORI_CARTE_ENUM.JOLLY));
		carteCoperte.add(new Carta(SEMI_ENUM.PICCHE, VALORI_CARTE_ENUM.JOLLY));

		mischia();

	}

	public void mischia() {
		Collections.shuffle(this.carteCoperte);
	}

	public Carta pesca(boolean pescaCartaScoperta) {
		Carta cartaPescata;
		// se ho ancora carte, rimuovo dal mazzo la prima e la ritorno,
		// altrimenti ritorno null
		if (pescaCartaScoperta) {
			cartaPescata = this.carteScoperte.isEmpty() ? null : this.carteScoperte.remove(0);
			aggiornaMazzoScoperto(cartaPescata);
		} else {
			cartaPescata = this.carteCoperte.isEmpty() ? null : this.carteCoperte.remove(0);
			checkFineMazzo();
		}

		return cartaPescata;
	}

	public void aggiungiCartaScoperta(Carta carta) {
		aggiornaMazzoScoperto(carta);
		this.carteScoperte.add(carta);
	}

	public boolean checkFineMazzo() {
		// se la carta che ho pescato era l'ultima,aggiorno lo sfondo per segnalarne
		// l'esaurimento
		if (this.carteCoperte.isEmpty()) {
			this.cartaShape.setFill(Color.WHITE);
			return true;
		}
		return false;
	}

	public void aggiornaMazzoScoperto(Carta carta) {
		// quando pesco una carta da un mazzo scoperto o la aggiungo,aggiorno l'immagine
		// per mostrare la carta subito sotto
		// o la carta appena aggiunta

		boolean isFineMazzo = checkFineMazzo();

		if (!isFineMazzo) {
			String nomeImmagineCarta = carta.getSeme() + "_" + carta.getValore();
			this.immagineCarta = ImagePatternFactory
					.generaImmagine(FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + nomeImmagineCarta);
		}
	}

	public List<Carta> distribuisciMano() {
		// assegna le carte ai giocatori, prendendo le prime 10 e rimuovendole dalla
		// lista
		List<Carta> mano = new ArrayList<>();
		while (mano.size() < 10) {
			mano.add(pesca(false));
		}
		return mano;
	}

	public List<Carta> getCarteCoperte() {
		return carteCoperte;
	}

	public void setCarteCoperte(List<Carta> carteCoperte) {
		this.carteCoperte = carteCoperte;
	}

	public List<Carta> getCarteScoperte() {
		return carteScoperte;
	}

	public void setCarteScoperte(List<Carta> carteScoperte) {
		this.carteScoperte = carteScoperte;
	}

}
