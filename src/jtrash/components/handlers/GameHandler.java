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
import jtrash.components.scenes.Playground;
import jtrash.enums.VALORI_CARTE_ENUM;

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

	private Player giocatoreDiTurno;

	private EventHandler<ActionEvent> posizionaCartaEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (cartaSelezionata != null && !cartaSelezionata.getValore().equals(VALORI_CARTE_ENUM.JOLLY)
					&& !cartaSelezionata.getValore().equals(VALORI_CARTE_ENUM.RE)
					&& !cartaSelezionata.getValore().equals(VALORI_CARTE_ENUM.REGINA)
					&& !cartaSelezionata.getValore().equals(VALORI_CARTE_ENUM.JACK)) {
				int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(cartaSelezionata.getValore());
				List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
				int indexDaSostituire = valoreCarta - 1;

				Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);
				carteGiocatore.set(indexDaSostituire, cartaSelezionata);

				mazzo.getCarteScoperte().add(cartaDaSostituire);

				cartaSelezionata = null;

				CarteScartateBox.getInstance().update(null, mazzo);
				CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

				Playground.getInstance().updatePlayground(false);

				Actionground.getInstance().handlePescaCartaScartata(cartaDaSostituire);
				Actionground.getInstance().setEnablePescaCarta(true);
				Actionground.getInstance().setEnableScartaCarta(false);
				Actionground.getInstance().handlePosizionaCarta(cartaSelezionata);

				handleTurno();
			}
		}
	};

	private EventHandler<ActionEvent> pescaCartaMazzoEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				Carta cartaPescata = mazzo.pesca(false);
				cartaSelezionata = cartaPescata;
				cartaSelezionata.giraCarta();

				CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata.getCartaShape());

				// non utilizzo observable altrimenti si crea una dipendenza ciclica tra
				// gamehandler e actionground
				Actionground.getInstance().handlePosizionaCarta(cartaPescata);
				Actionground.getInstance().handlePescaCartaScartata(null);
				Actionground.getInstance().setEnablePescaCarta(false);
				Actionground.getInstance().setEnableScartaCarta(true);
			}
		}
	};

	private EventHandler<ActionEvent> pescaCartaScartataEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteScoperte().isEmpty()) {
				Carta cartaPescata = mazzo.pesca(true);
				cartaSelezionata = cartaPescata;

				CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata.getCartaShape());
				CarteScartateBox.getInstance().update(null, mazzo);

				// non utilizzo observable altrimenti si crea una dipendenza ciclica tra
				// gamehandler e actionground
				Actionground.getInstance().handlePosizionaCarta(cartaPescata);
				Actionground.getInstance().handlePescaCartaScartata(null);
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

				// non utilizzo observable altrimenti si crea una dipendenza ciclica
				CarteMazzoBox.getInstance().update(null, mazzo);
				CarteScartateBox.getInstance().update(null, mazzo);
				CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

				Actionground.getInstance().handlePescaCartaScartata(cartaScartata);
				Actionground.getInstance().setEnablePescaCarta(true);
				Actionground.getInstance().setEnableScartaCarta(false);
				handleTurno();
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

	public void handleTurno() {
		if (giocatoreDiTurno == null) {
			giocatoreDiTurno = getGiocatori().get(0);
		} else {
			boolean giocatoreDiTurnoSuperato = false;
			boolean giocatoreDiTurnoCambiato = false;
			for (Player giocatore : getGiocatori()) {

				if (giocatoreDiTurnoSuperato) {
					giocatoreDiTurno = giocatore;
					giocatoreDiTurnoCambiato = true;
					break;
				}

				if (giocatore.getNome().equals(giocatoreDiTurno.getNome())) {
					giocatoreDiTurnoSuperato = true;
					continue;
				}
			}
			if (!giocatoreDiTurnoCambiato) {
				giocatoreDiTurno = getGiocatori().get(0);
			}
		}
		Actionground.getInstance().handleTurno(giocatoreDiTurno.getNome());
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

	public EventHandler<ActionEvent> getPescaCartaEventHandler() {
		return pescaCartaMazzoEventHandler;
	}

	public EventHandler<ActionEvent> getScartaCartaPescataEventHandler() {
		return scartaCartaEventHandler;
	}

	public void setPescaCartaEventHandler(EventHandler<ActionEvent> pescaCartaEventHandler) {
		this.pescaCartaMazzoEventHandler = pescaCartaEventHandler;
	}

	public EventHandler<ActionEvent> getPosizionaCartaEventHandler() {
		return posizionaCartaEventHandler;
	}

	public void setPosizionaCartaEventHandler(EventHandler<ActionEvent> posizionaCartaEventHandler) {
		this.posizionaCartaEventHandler = posizionaCartaEventHandler;
	}

	public EventHandler<ActionEvent> getPescaCartaScartataEventHandler() {
		return pescaCartaScartataEventHandler;
	}

	public void setPescaCartaScartataEventHandler(EventHandler<ActionEvent> pescaCartaScartataEventHandler) {
		this.pescaCartaScartataEventHandler = pescaCartaScartataEventHandler;
	}

	public Mazzo getMazzo() {
		return mazzo;
	}

	public void setMazzo(Mazzo mazzo) {
		this.mazzo = mazzo;
	}

	public Player getGiocatoreDiTurno() {
		return giocatoreDiTurno;
	}

}
