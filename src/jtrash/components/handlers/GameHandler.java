package jtrash.components.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Mazzo;
import jtrash.components.objects.Player;
import jtrash.components.objects.box.CartaSelezionataBox;
import jtrash.components.objects.box.CarteMazzoBox;
import jtrash.components.objects.box.CarteScartateBox;

@SuppressWarnings("deprecation")
public class GameHandler implements Observer {

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
	private Mazzo mazzo = new Mazzo();

	private EventHandler<ActionEvent> giraCartaSelezionataEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("Carta da girare: " + cartaSelezionata.getValore() + " " + cartaSelezionata.getSeme());
			if (cartaSelezionata.isCoperta()) {
				cartaSelezionata.giraCarta();
			}
			CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata.getCartaShape());
		}
	};

	private EventHandler<ActionEvent> pescaCartaEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				Carta ultimaCarta = mazzo.getCarteCoperte().get(mazzo.getCarteCoperte().size() - 1);
				ultimaCarta.giraCarta();
				System.out.println("carta pescata: " + ultimaCarta.getSeme() + " " + ultimaCarta.getValore());
				// TODO evento per bloccarte il tasto pesca e attivare il tasto scarta
			}
		}
	};

	private EventHandler<ActionEvent> scartaCartaPescataEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				Carta ultimaCarta = mazzo.getCarteCoperte().remove(mazzo.getCarteCoperte().size() - 1);
				if (ultimaCarta.isCoperta()) {
					ultimaCarta.giraCarta();
				}
				mazzo.getCarteScoperte().add(ultimaCarta);
				//TODO evento che aggiorna i box delle carte mazzo e scoperte
				CarteMazzoBox.getInstance().update(null, mazzo);
				CarteScartateBox.getInstance().update(null, mazzo);
			}
		}
	};

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
		if (this.cartaSelezionata == null) {
			Carta nessunaCarta = new Carta();
			nessunaCarta.getCartaShape().setFill(Color.WHITE);
			return nessunaCarta;
		}
		return this.cartaSelezionata;
	}

	public void setCartaSelezionata(Carta cartaSelezionata) {
		this.cartaSelezionata = cartaSelezionata;
	}

	@Override
	public void update(Observable o, Object arg) {
		setCartaSelezionata((Carta) arg);
	}

	public EventHandler<ActionEvent> getGiraCartaSelezionataEventHandler() {
		return giraCartaSelezionataEventHandler;
	}

	public EventHandler<ActionEvent> getPescaCartaEventHandler() {
		return pescaCartaEventHandler;
	}

	public EventHandler<ActionEvent> getScartaCartaPescataEventHandler() {
		return scartaCartaPescataEventHandler;
	}

	public void setPescaCartaEventHandler(EventHandler<ActionEvent> pescaCartaEventHandler) {
		this.pescaCartaEventHandler = pescaCartaEventHandler;
	}

	public Mazzo getMazzo() {
		return mazzo;
	}

	public void setMazzo(Mazzo mazzo) {
		this.mazzo = mazzo;
	}

}
