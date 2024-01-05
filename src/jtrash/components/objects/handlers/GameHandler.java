package jtrash.components.objects.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.AvatarFactory;
import jtrash.components.factories.PlayerFactory;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Mazzo;
import jtrash.components.objects.models.Player;
import jtrash.components.objects.views.box.CartaSelezionataBox;
import jtrash.components.objects.views.box.CarteMazzoBox;
import jtrash.components.objects.views.box.CarteScartateBox;
import jtrash.components.objects.views.scenes.Actionground;
import jtrash.components.objects.views.scenes.Playground;
import jtrash.enums.AVATAR_ENUM;
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

	private int contatoreTurniDopoTrash = 0;

	private void posizionaCarta(Carta carta) {
		int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(carta.getValore());
		int indexDaSostituire = valoreCarta - 1;

		posizionaCartaGenerica(carta, indexDaSostituire);
	}

	private void posizionaWildCard(Carta carta, Integer index) {
		int indexDaSostituire = index != null ? index : Actionground.getInstance().getPosizioneWildcardSelezionata();

		posizionaCartaGenerica(carta, indexDaSostituire);
	}

	private void posizionaCartaGenerica(Carta carta, Integer index) {
		List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
		Carta cartaDaSostituire = carteGiocatore.get(index);
		carteGiocatore.set(index, carta);

		mazzo.getCarteScoperte().add(cartaDaSostituire);

		cartaSelezionata = null;

		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		Playground.getInstance().updatePlayground(false);

		AnimationsHandler.animazioneIngrandimento(carta);

		Actionground.getInstance().setEnablePescaCarta(false);
		Actionground.getInstance().setEnableScartaCarta(false);
		Actionground.getInstance().handlePosizionaCarta(cartaSelezionata);
		boolean disablePescaCartaScartata = Actionground.getInstance()
				.handleDisablePescaCartaScartata(cartaDaSostituire, giocatoreDiTurno);

		if ((disablePescaCartaScartata && !giocatoreDiTurno.isBot()) || checkTrashGiocatore(giocatoreDiTurno)) {
			handleTurno();
		}
	}

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
		Actionground.getInstance().handleDisablePescaCartaScartata(null, giocatoreDiTurno);
		Actionground.getInstance().setEnablePescaCarta(false);
		Actionground.getInstance().setEnableScartaCarta(true);
		return cartaPescata;
	}

	private EventHandler<ActionEvent> scartaCartaEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			scartaCarta(cartaSelezionata);
			handleTurno();
		}

	};

	private void scartaCarta(Carta carta) {
		mazzo.getCarteScoperte().add(carta);
		cartaSelezionata = null;

		// non utilizzo observable altrimenti si crea una dipendenza ciclica
		CarteMazzoBox.getInstance().update(null, mazzo);
		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		Actionground.getInstance().handleDisablePescaCartaScartata(carta, giocatoreDiTurno);
		Actionground.getInstance().setEnablePescaCarta(true);
		Actionground.getInstance().setEnableScartaCarta(false);
	}

	public Player aggiungiGiocatore(String nome, boolean isBot) {

		Rectangle avatar;

		if (isBot) {
			avatar = AvatarFactory.getAvatar(AVATAR_ENUM.AVATAR_BOT);
		} else {
			avatar = UtentiHandler.getInstance().getUtenteAttivo().getAvatar();
		}

		Player giocatore = PlayerFactory.creaPlayer(null, nome, avatar);
		giocatore.setBot(isBot);
		giocatori.add(giocatore);
		if (giocatori.size() > 2) {
			Mazzo secondoMazzo = new Mazzo();
			mazzo.getCarteCoperte().addAll(secondoMazzo.getCarteCoperte());
		}
		return giocatore;
	}

	public List<Player> getGiocatori() {
		return giocatori;
	}

	public void handleTurno() {

		if (!getGiocatori().isEmpty()) {
			if (giocatoreDiTurno == null) {
				giocatoreDiTurno = getGiocatori().get(0);
				Actionground.getInstance().handleTurno(giocatoreDiTurno.getNome());
			} else {
				checkTrash(giocatoreDiTurno);
				if (!checkFineRound()) {
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
					Actionground.getInstance().handleTurno(giocatoreDiTurno.getNome());

					ModalHandler.getInstance().mostraModaleInformativo("Cambio turno",
							"Turno di " + giocatoreDiTurno.getNome());

					if (contatoreTurniDopoTrash > 0) {
						contatoreTurniDopoTrash++;
					}

					if (giocatoreDiTurno.isBot()) {
						turnoBot();
					} else {
						resetActionGround();
					}

				} else {
					handleFinePartita();
				}
			}
		}

	}

	private void turnoBot() {

		if (giocatoreDiTurno.isBot()) {
			List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
			// controllo prima le carte scoperte
			boolean hasGiocatoCartaScoperta = turnoBotCarteScoperte(carteGiocatore);

			// se il bot non gioca dalle scoperte, allora pesco
			if (!hasGiocatoCartaScoperta) {
				turnoBotCarteMazzo(carteGiocatore);
			}
			if (giocatoreDiTurno.isBot()) {
				handleTurno();
			}
		}

	}

	private boolean turnoBotCarteScoperte(List<Carta> carteGiocatore) {
		if (giocatoreDiTurno.isBot()) {
			if (checkTrashGiocatore(giocatoreDiTurno)) {
				return false;
			}

			Carta primaCartaScoperta = mazzo.getPrimaCartaScoperta(false);

			int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(primaCartaScoperta.getValore());
			int indexDaSostituire = valoreCarta - 1;

			Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);

			boolean cartaPosizionata = false;

			if (primaCartaScoperta.isPosizionabile()) {
				if (!primaCartaScoperta.isWildcard()) {
					if (cartaDaSostituire.isCoperta()
							|| !cartaDaSostituire.isCoperta() && cartaDaSostituire.isWildcard()) {
						// se la carta è ancora coperta o è una wildcard,allora la sostituisco
						posizionaCarta(pescaCarta(true));
						cartaPosizionata = true;
					}
				} else {
					int indexPosizionamentoWildcard = getIndexPosizionamentoWildcard();
					if (indexPosizionamentoWildcard >= 0) {
						posizionaWildCard(pescaCarta(true), indexPosizionamentoWildcard);
						cartaPosizionata = true;
					}
				}
			}

			// se ho posizionato, continuo a giocare finchè posso posizionare dalle carte
			// scoperte
			if (cartaPosizionata) {
				turnoBotCarteScoperte(carteGiocatore);
			}
			return cartaPosizionata;
		}

		return false;
	}

	private boolean turnoBotCarteMazzo(List<Carta> carteGiocatore) {

		if (giocatoreDiTurno.isBot()) {
			if (checkTrashGiocatore(giocatoreDiTurno)) {
				return false;
			}

			Carta cartaPescata = pescaCarta(false);
			int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(cartaPescata.getValore());
			int indexDaSostituire = valoreCarta - 1;
			Carta cartaDaSostituire = carteGiocatore.get(indexDaSostituire);

			cartaDaSostituire = carteGiocatore.get(indexDaSostituire);

			boolean cartaPosizionata = false;

			if (cartaPescata.isPosizionabile()) {
				if (!cartaPescata.isWildcard()) {
					if (cartaDaSostituire.isCoperta()
							|| !cartaDaSostituire.isCoperta() && cartaDaSostituire.isWildcard()) {
						// se la carta è ancora coperta o è una wildcard,allora la sostituisco
						posizionaCarta(cartaPescata);
						cartaPosizionata = true;
					}
				} else {
					int indexPosizionamentoWildcard = getIndexPosizionamentoWildcard();
					if (indexPosizionamentoWildcard >= 0) {
						posizionaWildCard(cartaPescata, indexPosizionamentoWildcard);
						cartaPosizionata = true;
					}
				}
			}

			// se ho posizionato, continuo a giocare finchè posso posizionare dalle carte
			// scoperte
			if (cartaPosizionata) {
				turnoBotCarteScoperte(carteGiocatore);
			} else {
				scartaCarta(cartaPescata);
			}
			return cartaPosizionata;
		}

		return false;

	}

	private int getIndexPosizionamentoWildcard() {
		for (int i = 0; i < giocatoreDiTurno.getCarte().size(); i++) {
			Carta cartaGiocatore = giocatoreDiTurno.getCarte().get(i);
			if (cartaGiocatore.isCoperta()) {
				return i;
			}
		}
		return -1;
	}

	public Carta getCartaSelezionata() {
		if (this.cartaSelezionata == null) {
			Carta nessunaCarta = new Carta();
			nessunaCarta.getCartaShape().setFill(Color.WHITE);
			return nessunaCarta;
		}
		return this.cartaSelezionata;
	}

	private boolean checkTrash(Player giocatoreDiTurno) {
		boolean trash = checkTrashGiocatore(giocatoreDiTurno);
		if (trash) {
			giocatoreDiTurno.setHasTrash(true);
			handleTrash();
		}
		return trash;
	}

	private boolean checkTrashGiocatore(Player giocatoreDiTurno) {
		if (giocatoreDiTurno == null)
			return false;

		boolean trash = true;
		for (Carta carta : giocatoreDiTurno.getCarte()) {
			if (carta.isCoperta()) {
				trash = false;
				break;
			}
		}
		return trash;
	}

	private void handleTrash() {
		if (contatoreTurniDopoTrash == 0)
			contatoreTurniDopoTrash++;
		ModalHandler.getInstance().mostraModaleInformativo("TRASH!!!", giocatoreDiTurno.getNome() + " ha fatto Trash!");
	}

	private void handleFinePartita() {
		giocatori = giocatori.stream().filter(p -> p.isHasTrash()).collect(Collectors.toList());
		giocatori.stream().forEach(g -> g.setHasTrash(false));

		if (giocatori.size() == 1) {
			String nomeGiocatore = giocatori.get(0).getNome();
			ModalHandler.getInstance().mostraModaleInformativo("Fine Partita",
					"Partita finita! Vince " + nomeGiocatore + "!");
			boolean vittoriaGiocatore = nomeGiocatore
					.equals(UtentiHandler.getInstance().getUtenteAttivo().getUsername());
			giocatoreDiTurno = null;
			UtentiHandler.getInstance().handleFineParita(vittoriaGiocatore);
		} else {
			boolean giocatoreEliminato = true;
			for (Player giocatore : giocatori) {
				if (giocatore.getNome().equals(UtentiHandler.getInstance().getUtenteAttivo().getUsername())) {
					giocatoreEliminato = false;
					break;
				}
			}
			if (!giocatoreEliminato) {
				startNewRound();
			} else {
				ModalHandler.getInstance().mostraModaleInformativo("Fine Partita",
						"Partita finita, sei stato eliminato!");
				giocatoreDiTurno = null;
				UtentiHandler.getInstance().handleFineParita(false);
			}
		}
	}

	private boolean checkFineRound() {
		if (contatoreTurniDopoTrash == giocatori.size()) {
			contatoreTurniDopoTrash = 0;
			return true;
		}
		return false;
	}

	public void startNewGame(String nomeGiocatore, int numeroGiocatori) {
		setMazzo(new Mazzo());
		giocatori = new ArrayList<>();
		aggiungiGiocatore(nomeGiocatore, false);

		for (int i = 1; i <= numeroGiocatori; i++) {
			aggiungiGiocatore("BOT " + i, true);
		}
		resetCampo();
	}

	public void startNewRound() {
		StringBuilder nomiGiocatori = new StringBuilder();
		giocatori.stream().forEach(p -> nomiGiocatori.append(p.getNome() + "; "));
		ModalHandler.getInstance().mostraModaleInformativo("Nuovo Round",
				"Nuovo round con i seguenti giocatori: \n" + nomiGiocatori);
		giocatoreDiTurno = null;
		setMazzo(new Mazzo());
		resetCampo();
	}

	private void resetCampo() {
		Playground.getInstance().updatePlayground(true);
		CarteMazzoBox.getInstance().update(null, mazzo);
		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);
		resetActionGround();
	}

	private void resetActionGround() {
		Actionground.getInstance().setEnablePescaCarta(true);
		Actionground.getInstance().setEnableScartaCarta(false);
		Actionground.getInstance().handleDisablePescaCartaScartata(mazzo.getPrimaCartaScoperta(false),
				giocatoreDiTurno);
	}

	public void setCartaSelezionata(Carta cartaSelezionata) {
		this.cartaSelezionata = cartaSelezionata;
	}

	@Override
	public void update(Observable o, Object arg) {
		setCartaSelezionata((Carta) arg);
	}

	public EventHandler<ActionEvent> getPescaCartaEventHandler() {
		return e -> pescaCarta(false);
	}

	public EventHandler<ActionEvent> getScartaCartaPescataEventHandler() {
		return scartaCartaEventHandler;
	}

	public EventHandler<ActionEvent> getPosizionaCartaEventHandler() {
		return e -> posizionaCarta(cartaSelezionata);
	}

	public EventHandler<ActionEvent> getPescaCartaScartataEventHandler() {
		return e -> pescaCarta(true);
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
		return e -> posizionaWildCard(cartaSelezionata, null);
	}

}
