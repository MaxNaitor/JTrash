package jtrash.components.objects.views.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.controllers.AnimationsController;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Mazzo;
import jtrash.components.objects.views.box.interfaces.IboxInterface;

/**
 * Classe che rappresenta visivamente la carta in cima alla lista di carte scartate
 * @author tizia
 *
 */
@SuppressWarnings("deprecation")
public class CarteScartateBox implements IboxInterface, Observer {

	private static CarteScartateBox instance;

	private Carta cartaDaVisualizzare;
	private Rectangle box;

	private CarteScartateBox() {

	}

	public static CarteScartateBox getInstance() {
		if (instance == null) {
			instance = new CarteScartateBox();
			inizializzaBox();
		}
		return instance;
	}

	/**
	 * Inizializza il box come una carta dallo sfondo bianco, non essendoci carte scartate all'inizio della partita
	 */
	private static void inizializzaBox() {
		instance.cartaDaVisualizzare = new Carta();
		instance.cartaDaVisualizzare.setFill(Color.WHITE);
		instance.setBox(instance.cartaDaVisualizzare);
	}

	/**
	 * Aggiorna la carta da visualizzare quando viene aggiunta una nuova carta alla lista delle carte scartate
	 */
	@Override
	public void update(Observable o, Object arg) {
		Mazzo mazzo = (Mazzo) arg;
		if (mazzo.getCarteScoperte().isEmpty()) {
			resetCartaDaVisualizzare();
		} else {
			instance.cartaDaVisualizzare = mazzo.getCarteScoperte().get(mazzo.getCarteScoperte().size() - 1);
			if (cartaDaVisualizzare.isCoperta()) {
				cartaDaVisualizzare.giraCarta();
			}
		}
		instance.setBoxFill(instance.cartaDaVisualizzare);

	}

	/**
	 * Resetta il box come una carta dallo sfondo bianco quando tutte le carte scartate vengono ripescate
	 */
	private void resetCartaDaVisualizzare() {
		cartaDaVisualizzare = new Carta();
		cartaDaVisualizzare.setFill(Color.WHITE);
	}
	
	/**
	 * Ritorna la carta visualizzata, resettandola se fosse null
	 * @return Carta
	 */
	public Carta getCartaDaVisualizzare() {
		if (instance.cartaDaVisualizzare == null) {
			resetCartaDaVisualizzare();
		}
		return instance.cartaDaVisualizzare;
	}

	public Rectangle getBox() {
		return box;
	}

	@Override
	public void setBoxFill(Rectangle box) {
		this.box.setFill(box.getFill());
		handleAnimazioneIngrandimento();
	}

	@Override
	public void setBox(Rectangle box) {
		if (this.box != null) {
			setBoxFill(box);
			return;
		}
		this.box = box;
	}

	@Override
	public void setBoxFill(Paint fill) {
	}

	private void handleAnimazioneIngrandimento() {
		if (!this.box.getFill().equals(Color.WHITE)) {
			AnimationsController.animazioneIngrandimento(this.box);
		}
	}

}
