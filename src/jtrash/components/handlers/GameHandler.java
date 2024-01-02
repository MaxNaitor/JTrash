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
import jtrash.components.scenes.Actionground;

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
				Carta cartaPescata = mazzo.pesca(false);
				System.out.println("carta pescata: " + cartaPescata.getSeme() + " " + cartaPescata.getValore());
				cartaSelezionata = cartaPescata;
				cartaSelezionata.giraCarta();
				CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata.getCartaShape());
				
				//non utilizzo observable altrimenti si crea una dipendenza ciclica tra gamehandler e actionground
				Actionground.getInstance().setEnablePescaCarta(false); 
				Actionground.getInstance().setEnableScartaCarta(true); 
			}
		}
	};

	private EventHandler<ActionEvent> scartaCartaEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				Carta cartaScartata = cartaSelezionata;
				mazzo.getCarteScoperte().add(cartaScartata);
				cartaSelezionata = null;
				// TODO evento che aggiorna i box delle carte mazzo e scoperte
				CarteMazzoBox.getInstance().update(null, mazzo);
				CarteScartateBox.getInstance().update(null, mazzo);
				CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);
				Actionground.getInstance().setEnablePescaCarta(true);
				Actionground.getInstance().setEnableScartaCarta(false); 
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
		return scartaCartaEventHandler;
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
