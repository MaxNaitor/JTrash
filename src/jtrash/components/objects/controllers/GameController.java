package jtrash.components.objects.controllers;

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
import jtrash.components.objects.views.scenes.ActionGround;
import jtrash.components.objects.views.scenes.PlayGround;
import jtrash.enums.AVATAR_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

@SuppressWarnings("deprecation")
public class GameController implements Observer {

	private static GameController instance;

	private GameController() {
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();

		}
		return instance;
	}

	private List<Player> giocatori = new ArrayList<>();

	private Carta cartaSelezionata;
	private Mazzo mazzo = new Mazzo();

	private Player giocatoreDiTurno;

	private int contatoreTurniDopoTrash = 0;

	/**
	 * Posiziona la carta all'indice corrispondente al suo valore tra la lista delle
	 * carte del giocatore attivo.
	 * 
	 * @param carta
	 */
	private void posizionaCarta(Carta carta) {
		int valoreCarta = VALORI_CARTE_ENUM.getValoreNumerico(carta.getValore());
		int indexDaSostituire = valoreCarta - 1;

		posizionaCartaGenerica(carta, indexDaSostituire);
	}

	/**
	 * Posiziona la wildcard all'indice in input. <br>
	 * Se l'indice passato è null, l'indice viene recuperato dal selettore di
	 * posizionamento delle wildcard.
	 * 
	 * @param carta
	 */
	private void posizionaWildCard(Carta carta, Integer index) {
		int indexDaSostituire = index != null ? index : ActionGround.getInstance().getPosizioneWildcardSelezionata();

		posizionaCartaGenerica(carta, indexDaSostituire);
	}

	/**
	 * Posiziona la carta in input nella posizione uguale index della lista delle
	 * carte del giocatore attivo e scarta la carta che viene sostituita. <br>
	 * Aggiorna lo stato delle azioni che possono essere svolte e, se il giocatore
	 * non può pescare la carta appena scartata oppure ha fatto trash, il turno
	 * passa al giocatore successivo.
	 * 
	 * @param carta
	 * @param index
	 */
	private void posizionaCartaGenerica(Carta carta, Integer index) {
		List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
		Carta cartaDaSostituire = carteGiocatore.get(index);
		carteGiocatore.set(index, carta);

		mazzo.getCarteScoperte().add(cartaDaSostituire);

		cartaSelezionata = null;

		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		PlayGround.getInstance().updatePlayground(false);

		AnimationsController.animazioneIngrandimento(carta);

		ActionGround.getInstance().setEnablePescaCarta(false);
		ActionGround.getInstance().setEnableScartaCarta(false);
		ActionGround.getInstance().handlePosizionaCarta(cartaSelezionata);
		boolean disablePescaCartaScartata = ActionGround.getInstance()
				.handleDisablePescaCartaScartata(cartaDaSostituire, giocatoreDiTurno);

		if ((disablePescaCartaScartata && !giocatoreDiTurno.isBot()) || checkTrashGiocatore(giocatoreDiTurno)) {
			handleTurno();
		}
	}

	/**
	 * Pesca una nuova carta dal mazzo coperto o dal mazzo scoperto, in funzione del
	 * boolean passato in input. <br>
	 * Se si pesca una carta coperta, questa viene girata <br>
	 * Se si pesca una carta scartata, viene aggiornato il mazzo delle carte
	 * scoperte mostrando la carta successiva. <br>
	 * Aggiorna lo stato delle azioni che possono essere svolte.
	 * 
	 * @param pescaCartaScoperta
	 * @return la carta pescata
	 */
	private Carta pescaCarta(boolean pescaCartaScoperta) {
		Carta cartaPescata = mazzo.pesca(pescaCartaScoperta);
		cartaSelezionata = cartaPescata;
		if (!pescaCartaScoperta)
			cartaSelezionata.giraCarta();

		CartaSelezionataBox.getInstance().setBoxFill(cartaSelezionata);
		if (pescaCartaScoperta)
			CarteScartateBox.getInstance().update(null, mazzo);

		ActionGround.getInstance().handlePosizionaCarta(cartaPescata);
		ActionGround.getInstance().handleDisablePescaCartaScartata(null, giocatoreDiTurno);
		ActionGround.getInstance().setEnablePescaCarta(false);
		ActionGround.getInstance().setEnableScartaCarta(true);
		return cartaPescata;
	}

	/**
	 * Scarta la carta selezionata e visualizzata nel box delle carte selezionate.
	 * <br>
	 * Successivamente aggiorna la visualizzazione dei box carta selezionata e box
	 * carte scartate e lo stato delle possibili azioni da svolgere.
	 * 
	 * @param carta
	 */
	private void scartaCarta(Carta carta) {
		mazzo.getCarteScoperte().add(carta);
		cartaSelezionata = null;

		CarteMazzoBox.getInstance().update(null, mazzo);
		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);

		ActionGround.getInstance().handleDisablePescaCartaScartata(carta, giocatoreDiTurno);
		ActionGround.getInstance().setEnablePescaCarta(true);
		ActionGround.getInstance().setEnableScartaCarta(false);
	}

	/**
	 * Aggiunge un nuovo giocatore alla partita, specificando se l'utente è un bot o
	 * meno. <br>
	 * Se ci sono più di due giocatori, aggiungo un mazzo in più alla partita.
	 * 
	 * @param nome
	 * @param isBot
	 * @return il giocatore aggiunto
	 */
	public Player aggiungiGiocatore(String nome, boolean isBot) {

		Rectangle avatar;

		if (isBot) {
			avatar = AvatarFactory.getAvatar(AVATAR_ENUM.AVATAR_BOT);
		} else {
			avatar = UtentiController.getInstance().getUtenteAttivo().getAvatar();
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

	/**
	 * Gestisce il passaggio del turno da un giocatore al successivo. <br>
	 * Al passaggio del turno, verifica se il giocatore ancora in turno ha fatto trash e se il round è finito o meno. <br>
	 * Se un giocaore ha fatto trash, aggiorna il counter dei turni successivi al trash. <br>
	 * Se il round è finito, la partita termina. <br>
	 * Se il round non è ancora finito, il giocatore successivo diventa quello attivo e svolge il suo turno. <br>
	 */
	public void handleTurno() {

		if (!getGiocatori().isEmpty()) {
			if (giocatoreDiTurno == null) {
				giocatoreDiTurno = getGiocatori().get(0);
				ActionGround.getInstance().handleTurno(giocatoreDiTurno.getNome());
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
					ActionGround.getInstance().handleTurno(giocatoreDiTurno.getNome());

					ModalController.getInstance().mostraModaleInformativo("Cambio turno",
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

	/**
	 * Svolge il turno dei bot. <br>
	 * Vengono prima controllate le carte scoperte: se il bot può giocare una carta scoperta, lo fa,
	 * altrimenti, il bot pesca una carta dal mazzo. <br>
	 * Finchè il bot può giocare carte dal mazzo scartato, lo fa, successivamente il turno passa al giocatore successivo
	 */
	private void turnoBot() {

		if (giocatoreDiTurno.isBot()) {
			List<Carta> carteGiocatore = giocatoreDiTurno.getCarte();
			// controllo prima le carte scoperte
			boolean hasGiocatoCartaScoperta = turnoBotCarteScoperte(carteGiocatore);

			if (!hasGiocatoCartaScoperta) {
				turnoBotCarteMazzo(carteGiocatore);
			}
			if (giocatoreDiTurno.isBot()) {
				handleTurno();
			}
		}

	}

	/**
	 * Esegue il turno del bot giocando dalle carte scoperte. <br>
	 * Se il bot ha fatto trash, il turno viene saltato. <br>
	 * Se la prima carta scartata è giocabile, questa viene posizionata e il turno viene ripetuto ricorsivamente
	 * finchè il bot non ha più possibilità di posizionare altre carte.
	 * @param carteGiocatore
	 * @return true se è stata posizionata una carta
	 */
	private boolean turnoBotCarteScoperte(List<Carta> carteGiocatore) {
		if (giocatoreDiTurno != null && giocatoreDiTurno.isBot()) {
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

			if (cartaPosizionata) {
				turnoBotCarteScoperte(carteGiocatore);
			}
			return cartaPosizionata;
		}

		return false;
	}

	/**
	 * Esegue il turno del bot giocando dalle carte coperte. <br>
	 * Se il bot ha fatto trash, il turno viene saltato. <br>
	 * Se la carta pescata è giocabile, questa viene posizionata e il turno viene ripetuto giocando dalle carte scoperte 
	 * ricorsivamente finchè il bot non ha più possibilità di posizionare altre carte. <br>
	 * Se la carta pescata non è giocabile, questa viene scartata.
	 * @param carteGiocatore
	 * @return true se è stata posizionata una carta
	 */
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

	/**
	 * Restituisce l'indice della prima carta coperta che può essere sostituita da una wildcard
	 * @return l'indice per il posizionamento della wildcard
	 */
	private int getIndexPosizionamentoWildcard() {
		for (int i = 0; i < giocatoreDiTurno.getCarte().size(); i++) {
			Carta cartaGiocatore = giocatoreDiTurno.getCarte().get(i);
			if (cartaGiocatore.isCoperta()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Restituisce la carta selezionata al momento, può restituire null
	 * @return la carta selezionata
	 */
	public Carta getCartaSelezionata() {
		if (this.cartaSelezionata == null) {
			Carta nessunaCarta = new Carta();
			nessunaCarta.setFill(Color.WHITE);
			return nessunaCarta;
		}
		return this.cartaSelezionata;
	}

	/**
	 * Se un giocatore ha fatto trash, viene aggiornato il suo stato e viene notificato all'utente.
	 * @param giocatore
	 * @return true se il giocatore ha fatto trash
	 */
	private boolean checkTrash(Player giocatore) {
		boolean trash = checkTrashGiocatore(giocatore);
		if (trash) {
			giocatore.setHasTrash(true);
			handleTrash();
		}
		return trash;
	}

	/**
	 * Restituisce true se il giocatore ha fatto trash
	 * @param giocatore
	 * @return true se il giocatore ha fatto trash
	 */
	private boolean checkTrashGiocatore(Player giocatore) {
		if (giocatore == null)
			return false;

		boolean trash = true;
		for (Carta carta : giocatore.getCarte()) {
			if (carta.isCoperta()) {
				trash = false;
				break;
			}
		}
		return trash;
	}

	/**
	 * Mostra un modale che informa che il giocatore di turno ha fatto trash, aggiornando il contatore dei turni dopo il trash.
	 */
	private void handleTrash() {
		if (contatoreTurniDopoTrash == 0)
			contatoreTurniDopoTrash++;
		ModalController.getInstance().mostraModaleInformativo("TRASH!!!",
				giocatoreDiTurno.getNome() + " ha fatto Trash!");
	}

	/**
	 * Gestisce la fine della partita. <br>
	 * Se un solo giocatore ha fatto trash, la partita si conclude. Si controlla se il vincitore è l'utente e si gestiscono
	 * le sue statistiche. <br>
	 * Se invece ci sono più giocatori che hanno fatto trash, si controlla che l'utente sia tra questi. <br>
	 * Se l'utente è tra i giocatori che hanno fatto trash, inizia un nuovo round con tali giocatori, altrimenti la partita finisce
	 * e si torna al menù principale.
	 */
	private void handleFinePartita() {
		giocatori = giocatori.stream().filter(p -> p.isHasTrash()).collect(Collectors.toList());
		giocatori.stream().forEach(g -> g.setHasTrash(false));

		if (giocatori.size() == 1) {
			String nomeGiocatore = giocatori.get(0).getNome();
			ModalController.getInstance().mostraModaleInformativo("Fine Partita",
					"Partita finita! Vince " + nomeGiocatore + "!");
			boolean vittoriaGiocatore = nomeGiocatore
					.equals(UtentiController.getInstance().getUtenteAttivo().getUsername());
			giocatoreDiTurno = null;
			UtentiController.getInstance().handleFinePartita(vittoriaGiocatore);
		} else {
			boolean giocatoreEliminato = true;
			for (Player giocatore : giocatori) {
				if (giocatore.getNome().equals(UtentiController.getInstance().getUtenteAttivo().getUsername())) {
					giocatoreEliminato = false;
					break;
				}
			}
			if (!giocatoreEliminato) {
				startNewRound();
			} else {
				ModalController.getInstance().mostraModaleInformativo("Fine Partita",
						"Partita finita, sei stato eliminato!");
				giocatoreDiTurno = null;
				UtentiController.getInstance().handleFinePartita(false);
			}
		}
	}

	/**
	 * Restituisce true se tutti i giocatori hanno svolto il proprio turno dopo che un giocatore ha fatto trash.
	 * @return true se il round è finito
	 */
	private boolean checkFineRound() {
		if (contatoreTurniDopoTrash == giocatori.size()) {
			contatoreTurniDopoTrash = 0;
			return true;
		}
		return false;
	}

	/**
	 * Inizia una nuova partita. <br>
	 * Inizializza la lista dei giocatori in funzione al numero di avverari (bot) scelti, oltre all'utente.
	 * @param nomeGiocatore
	 * @param numeroAvversari
	 */
	public void startNewGame(String nomeGiocatore, int numeroAvversari) {
		setMazzo(new Mazzo());
		giocatori = new ArrayList<>();
		aggiungiGiocatore(nomeGiocatore, false);

		for (int i = 1; i <= numeroAvversari; i++) {
			aggiungiGiocatore("BOT " + i, true);
		}
		resetCampo();
	}

	/**
	 * Inizia un nuovo round se il primo si è concluso con più giocatori che hanno fatto trash.
	 */
	public void startNewRound() {
		StringBuilder nomiGiocatori = new StringBuilder();
		giocatori.stream().forEach(p -> nomiGiocatori.append(p.getNome() + "; "));
		ModalController.getInstance().mostraModaleInformativo("Nuovo Round",
				"Nuovo round con i seguenti giocatori: \n" + nomiGiocatori);
		giocatoreDiTurno = null;
		setMazzo(new Mazzo());
		resetCampo();
	}

	/**
	 * Reimposta allo stato iniziale il campo da gioco
	 */
	private void resetCampo() {
		PlayGround.getInstance().updatePlayground(true);
		CarteMazzoBox.getInstance().update(null, mazzo);
		CarteScartateBox.getInstance().update(null, mazzo);
		CartaSelezionataBox.getInstance().setBoxFill(Color.WHITE);
		resetActionGround();
	}

	/**
	 * Reimposta allo stato iniziale le azioni dell' ActionGround
	 */
	private void resetActionGround() {
		ActionGround.getInstance().setEnablePescaCarta(true);
		ActionGround.getInstance().setEnableScartaCarta(false);
		ActionGround.getInstance().handleDisablePescaCartaScartata(mazzo.getPrimaCartaScoperta(false),
				giocatoreDiTurno);
	}

	/**
	 * Imposta la carta attualmente selezionata
	 * @param cartaSelezionata
	 */
	public void setCartaSelezionata(Carta cartaSelezionata) {
		this.cartaSelezionata = cartaSelezionata;
	}

	@Override
	public void update(Observable o, Object arg) {
		setCartaSelezionata((Carta) arg);
	}

	/**
	 * Restituisce la funzione da invocare quando viene pescata una carta
	 * 
	 * @return EventHandler
	 */
	public EventHandler<ActionEvent> getPescaCartaEventHandler() {
		return e -> pescaCarta(false);
	}

	/**
	 * Restituisce la funzione da invocare quando viene scartata una carta
	 * 
	 * @return EventHandler
	 */
	public EventHandler<ActionEvent> getScartaCartaPescataEventHandler() {
		return e -> {
			scartaCarta(cartaSelezionata);
			handleTurno();
		};
	}

	/**
	 * Restituisce la funzione da invocare al posizionamento delle carte normali
	 * 
	 * @return EventHandler
	 */
	public EventHandler<ActionEvent> getPosizionaCartaEventHandler() {
		return e -> posizionaCarta(cartaSelezionata);
	}

	/**
	 * Restituisce la funzione da invocare quando si pesca una carta scartata
	 * 
	 * @return EventHandler
	 */
	public EventHandler<ActionEvent> getPescaCartaScartataEventHandler() {
		return e -> pescaCarta(true);
	}

	/**
	 * Restituisce la funzione da invocare al posizionamento delle wildcard
	 * 
	 * @return EventHandler
	 */
	public EventHandler<ActionEvent> getPosizionaWildcardEventHandler() {
		return e -> posizionaWildCard(cartaSelezionata, null);
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
