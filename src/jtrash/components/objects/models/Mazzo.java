package jtrash.components.objects.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jtrash.components.objects.models.abstractions.CartaAbstract;
import jtrash.enums.SEMI_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

/**
 * Rappresentazione di un Mazzo di carte, diviso in carte coperte (ancora da pescare) e carte scoperte (carte scartate)
 * @author tizia
 *
 */
public class Mazzo extends CartaAbstract {

	private List<Carta> carteCoperte = new ArrayList<>();
	private List<Carta> carteScoperte = new ArrayList<>();

	/**
	 * Genera un mazzo e la sua rappresentazione grafica come carta singola coperta. <br>
	 * Il mazzo è generato scorrendo tutti i semi e tutti i valori delle carte,
	 * creando una nuova carta con ogni combinazione di essi e aggiungendola alla
	 * lista delle carte coperte. <br>
	 * Inoltre aggiunge due JOLLY rappresentati da due
	 * colori, rosso e nero
	 */
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

	/**
	 * Mischia le carte tramite il metodo shuffle() di Collections
	 */
	public void mischia() {
		Collections.shuffle(this.carteCoperte);
	}

	/**
	 * Ritorna la carta in cima al mazzo coperto o la carta in cima al mazzo
	 * scoperto, in funzione del valore booleano passato in input
	 * 
	 * @param pescaCartaScoperta
	 * @return la carta pescata
	 */
	public Carta pesca(boolean pescaCartaScoperta) {
		Carta cartaPescata;
		// se ho ancora carte, rimuovo dal mazzo la prima e la ritorno,
		// altrimenti ritorno null
		if (pescaCartaScoperta) {
			cartaPescata = getPrimaCartaScoperta(true);
		} else {
			cartaPescata = this.carteCoperte.isEmpty() ? null : this.carteCoperte.remove(0);
		}

		return cartaPescata;
	}

	/**
	 * Restituisce la carta in cima al mazzo scoperto. Se il parametro in input è
	 * true, la carta viene rimossa dalla lista delle carte scoperte
	 * 
	 * @param rimuoviCarta
	 * @return la prima carta scoperta
	 */
	public Carta getPrimaCartaScoperta(boolean rimuoviCarta) {
		if (!this.carteScoperte.isEmpty()) {
			if (rimuoviCarta) {
				return this.carteScoperte.remove(this.carteScoperte.size() - 1);
			} else {
				return this.carteScoperte.get(this.carteScoperte.size() - 1);
			}
		}
		return null;
	}

	
	/**
	 * Pesca le prime n carte dal mazzo coperto e le restituisce, per assegnarle a un giocatore
	 * @return List di carte
	 */
	public List<Carta> distribuisciMano(int numeroCarte) {
		List<Carta> mano = new ArrayList<>();
		while (mano.size() < numeroCarte) {
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
