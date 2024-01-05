package jtrash.components.objects.views.scenes;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Player;
import jtrash.components.objects.views.box.CartaSelezionataBox;
import jtrash.components.objects.views.box.CarteMazzoBox;
import jtrash.components.objects.views.box.CarteScartateBox;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

public class Actionground {

	private static Actionground instance;

	public static Actionground getInstance() {
		if (instance == null) {
			instance = new Actionground();
		}
		return instance;
	}

	private static GridPane actionground;

	private static Text giocatoreDiTurno;
	private static Text testoCartaSelezionata;

	private static Text testoCarteScartare;
	private static Text testoCarteMazzo;

	private static Button pescaCartaMazzo;
	private static Button pescaCartaScartata;

	private static Button posizionaCarta;
	private static Button scartaCarta;

	private static Button posizionaWildcard;
	private static ComboBox<Integer> selettorePosizioneWildcard;

	private static VBox azioniActionGround;

	private Actionground() {
		GameHandler gameHandler = GameHandler.getInstance();

		actionground = GridPaneFactory.generaGridPane(BackgroundFactory.generaBackground(
				FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.SFONDO_PRINCIPALE.getNomeImmagine()));

		giocatoreDiTurno = TextFactory.generaTesto("Turno di giocatore", Color.WHITE, FontWeight.BOLD, 20);

		testoCartaSelezionata = TextFactory.generaTesto("Carta selezionata", Color.WHITE);

		testoCarteScartare = TextFactory.generaTesto("Carte scartate", Color.WHITE);
		testoCarteMazzo = TextFactory.generaTesto("Mazzo", Color.WHITE);

		pescaCartaMazzo = ButtonFactory.generaTasto("Pesca carta dal mazzo", gameHandler.getPescaCartaEventHandler());
		pescaCartaScartata = ButtonFactory.generaTasto("Pesca carta scartata",
				gameHandler.getPescaCartaScartataEventHandler());
		pescaCartaScartata.setDisable(true); // all'inizio, non posso pescare carte scartate, visto che non ce ne sono

		posizionaCarta = ButtonFactory.generaTasto("Posiziona carta", gameHandler.getPosizionaCartaEventHandler());
		posizionaCarta.setDisable(true); // all'inizio, non posso posizionare carte

		posizionaWildcard = ButtonFactory.generaTasto("Posiziona Wildcard",
				gameHandler.getPosizionaWildcardEventHandler());
		posizionaWildcard.setDisable(true);

		Text wildcardLabel = TextFactory.generaTesto("Seleziona posizione wildcard:", Color.WHITE);
		ObservableList<Integer> posizioni = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		selettorePosizioneWildcard = new ComboBox<>(posizioni);
		selettorePosizioneWildcard.setDisable(true);

		scartaCarta = ButtonFactory.generaTasto("Scarta carta", gameHandler.getScartaCartaPescataEventHandler());
		scartaCarta.setDisable(true); // all'inizio, non posso scartare carte

		azioniActionGround = BoxFactory
				.generaBoxVerticaleNodi(Arrays.asList(giocatoreDiTurno, CartaSelezionataBox.getInstance().getBox(),
						testoCartaSelezionata, CarteScartateBox.getInstance().getBox(), testoCarteScartare,
						CarteMazzoBox.getInstance().getBox(), testoCarteMazzo, pescaCartaMazzo, pescaCartaScartata,
						posizionaCarta, scartaCarta, posizionaWildcard, wildcardLabel, selettorePosizioneWildcard));
		actionground.setAlignment(Pos.BASELINE_CENTER);
		actionground.add(azioniActionGround, 0, 1);
	}

	public GridPane getActionground() {
		return actionground;
	}

	public void setEnablePescaCarta(boolean canPescareCarta) {
		pescaCartaMazzo.setDisable(!canPescareCarta);
	}

	public void setEnableScartaCarta(boolean canScartareCarta) {
		scartaCarta.setDisable(!canScartareCarta);
	}

	public void handleTurno(String nomeGiocatoreDiTurno) {
		giocatoreDiTurno.setText("Turno di " + nomeGiocatoreDiTurno);
	}

	public void handlePosizionaCarta(Carta cartaSelezionata) {
		if (cartaSelezionata != null) {
			switch (cartaSelezionata.getValore()) {
			case JOLLY:
			case RE:
				posizionaCarta.setDisable(true);
				posizionaWildcard.setDisable(false);
				selettorePosizioneWildcard.setDisable(false);
				break;
			case REGINA:
			case JACK:
				posizionaCarta.setDisable(true);
				posizionaWildcard.setDisable(true);
				disableSelettorePosizioneWildcard();
				break;
			default:
				posizionaCarta.setDisable(false);
				posizionaWildcard.setDisable(true);
				disableSelettorePosizioneWildcard();
			}
		} else {
			posizionaCarta.setDisable(true);
			posizionaWildcard.setDisable(true);
			disableSelettorePosizioneWildcard();
		}
	}

	private void disableSelettorePosizioneWildcard() {
		selettorePosizioneWildcard.setValue(null);
		selettorePosizioneWildcard.setDisable(true);
	}

	public boolean handleDisablePescaCartaScartata(Carta cartaScartata, Player giocatoreDiTurno) {
		if (cartaScartata != null) {
			if (!cartaScartata.isPosizionabile()) {
				pescaCartaScartata.setDisable(true);
				return true;
			}

			if (cartaScartata.isWildcard()) {
				pescaCartaScartata.setDisable(false);
				return false;
			}

			int indexCarta = VALORI_CARTE_ENUM.getValoreNumerico(cartaScartata.getValore()) - 1;
			Carta cartaGiocatore = giocatoreDiTurno.getCarte().get(indexCarta);

			if (cartaGiocatore.isCoperta() || !cartaGiocatore.isCoperta() && cartaGiocatore.isWildcard()) {
				pescaCartaScartata.setDisable(false);
				return false;
			}
			pescaCartaScartata.setDisable(true);
			return true;

		} else {
			pescaCartaScartata.setDisable(true);
			return true;
		}
	}

	public int getPosizioneWildcardSelezionata() {
		return selettorePosizioneWildcard.getValue() - 1;
	}

}
