package jtrash.components.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Player;

public class GameHandler implements Observer{

	private static GameHandler instance;

	private GameHandler() {

	}

	public static GameHandler getInstance() {
		if (instance == null) {
			instance = new GameHandler();
		}
		return instance;
	}

	private List<Player> giocatori = new ArrayList<>();

	private Carta cartaSelezionata;

	public void aggiungiGiocatore(String nome) {
		giocatori.add(PlayerFactory.creaPlayer(null, nome));
	}

	public List<Player> getGiocatori() {
		if (giocatori.isEmpty()) {
			giocatori.add(new Player(null, "player 1"));
			giocatori.add(new Player(null, "player 2"));
		}

		return giocatori;
	}

	public Carta getCartaSelezionata() {
		if (cartaSelezionata == null) {
			Carta nessunaCarta = new Carta();
			nessunaCarta.getCartaShape().setFill(Color.WHITE);
			return nessunaCarta;
		}
		return cartaSelezionata;
	}

	public void setCartaSelezionata(Carta cartaSelezionata) {
		this.cartaSelezionata = cartaSelezionata;
	}

	@Override
	public void update(Observable o, Object arg) {
		cartaSelezionata = (Carta) arg;
		System.out.println("Carta selezionata: " + cartaSelezionata.getValore() + " " + cartaSelezionata.getSeme());
	}

}
