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
			posizionaCarta(cartaSelezionata);
			handleTurno();
		}
	};

	private void posizionaCarta(Carta carta) {
		int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(carta.getValore());
		int indexDaSostituire = valoreCarta - 1;
		List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();

		Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);
		carteGiocatore.set(indexDaSostituire, carta);

		mazzo.getCarteScoperte().add(cartaDaSostituire);

		cartaSelezionata = null;

		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		Playground.getInstance().updatePlayground(false);

		Actionground.getInstance().handlePescaCartaScartata(cartaDaSostituire);
		Actionground.getInstance().setEnablePescaCarta(true);
		Actionground.getInstance().setEnableScartaCarta(false);
		Actionground.getInstance().handlePosizionaCarta(cartaSelezionata);
	}

	private EventHandler<ActionEvent> posizionaWildcardEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			posizionaWildCard(cartaSelezionata, null);
			handleTurno();
		}
	};

	private void posizionaWildCard(Carta carta, Integer index) {
		List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
		int indexDaSostituire = index != null ? index : Actionground.getInstance().getPosizioneWildcardSelezionata();

		Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);
		carteGiocatore.set(indexDaSostituire, carta);

		mazzo.getCarteScoperte().add(cartaDaSostituire);

		cartaSelezionata = null;

		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		Playground.getInstance().updatePlayground(false);

		Actionground.getInstance().handlePescaCartaScartata(cartaDaSostituire);
		Actionground.getInstance().setEnablePescaCarta(true);
		Actionground.getInstance().setEnableScartaCarta(false);
		Actionground.getInstance().handlePosizionaCarta(cartaSelezionata);
	}

	private EventHandler<ActionEvent> pescaCartaMazzoEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				pescaCarta(false);
			}
		}

	};

	private EventHandler<ActionEvent> pescaCartaScartataEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteScoperte().isEmpty()) {
				pescaCarta(true);
			}
		}
	};

	private Carta pescaCarta(boolean pescaCartaScoperta) {
		Carta cartaPescata = mazzo.pesca(pescaCartaScoperta);
		cartaSelezionata = cartaPescata;
		if (!pescaCartaScoperta)
			cartaSelezionata.giraCarta();

		CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata.getCartaShape());
		if (pescaCartaScoperta)
			CarteScartateBox.getInstance().update(null, mazzo);

		// non utilizzo observable altrimenti si crea una dipendenza ciclica tra
		// gamehandler e actionground
		Actionground.getInstance().handlePosizionaCarta(cartaPescata);
		Actionground.getInstance().handlePescaCartaScartata(null);
		Actionground.getInstance().setEnablePescaCarta(false);
		Actionground.getInstance().setEnableScartaCarta(true);
		return cartaPescata;
	}

	private EventHandler<ActionEvent> scartaCartaEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if (!mazzo.getCarteCoperte().isEmpty()) {
				scartaCarta(cartaSelezionata);
				handleTurno();
			}
		}

	};
	
	private void scartaCarta(Carta carta) {
		mazzo.getCarteScoperte().add(carta);
		cartaSelezionata = null;

		// non utilizzo observable altrimenti si crea una dipendenza ciclica
		CarteMazzoBox.getInstance().update(null, mazzo);
		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		Actionground.getInstance().handlePescaCartaScartata(carta);
		Actionground.getInstance().setEnablePescaCarta(true);
		Actionground.getInstance().setEnableScartaCarta(false);
	}

	public void aggiungiGiocatore(String nome) {
		giocatori.add(PlayerFactory.creaPlayer(null, nome));
	}

	public List<Player> getGiocatori() {
		return giocatori;
	}

	public void handleTurno() {
		if (!getGiocatori().isEmpty()) {
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

			if (giocatoreDiTurno.getNome().contains("BOT")) {
				turnoBot();
			}
		}

	}

	private void turnoBot() {
		// controllo prima le carte scoperte
		Carta primaCartaScoperta = mazzo.getPrimaCartaScoperta(false);

		List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
		int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(primaCartaScoperta.getValore());
		int indexDaSostituire = valoreCarta - 1;

		Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);

		if (primaCartaScoperta.isPosizionabile()) {
			if (!primaCartaScoperta.isWildcard()) {
				if (cartaDaSostituire.isCoperta() || !cartaDaSostituire.isCoperta() && cartaDaSostituire.isWildcard()) {
					// se la carta è ancora coperta o è una wildcard,allora la sostituisco
					posizionaCarta(pescaCarta(true));
					handleTurno();
					return;
				}
			} else {
				if (posizionaWildcardBot(primaCartaScoperta)) {
					handleTurno();
					return;
				}
			}
		}

		Carta cartaPescata = pescaCarta(false);
		valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(cartaPescata.getValore());
		indexDaSostituire = valoreCarta - 1;

		cartaDaSostituire = carteGiocatore.get(indexDaSostituire);

		if (cartaPescata.isPosizionabile()) {
			if (!cartaPescata.isWildcard()) {
				if (cartaDaSostituire.isCoperta() || !cartaDaSostituire.isCoperta() && cartaDaSostituire.isWildcard()) {
					// se la carta è ancora coperta o è una wildcard,allora la sostituisco
					posizionaCarta(cartaPescata);
					handleTurno();
					return;
				}
			} else {
				if (posizionaWildcardBot(cartaPescata)) {
					handleTurno();
					return;
				}
			}
		}
		scartaCarta(cartaPescata);
		handleTurno();
	}

	private boolean posizionaWildcardBot(Carta cartaDaPosizionare) {
		for (int i = 0; i < giocatoreDiTurno.getCarte().size(); i++) {
			Carta cartaGiocatore = giocatoreDiTurno.getCarte().get(i);
			if (cartaGiocatore.isCoperta()) {
				posizionaWildCard(cartaDaPosizionare, i);
				return true;
			}
		}
		return false;
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

	public EventHandler<ActionEvent> getPosizionaWildcardEventHandler() {
		return posizionaWildcardEventHandler;
	}

}
