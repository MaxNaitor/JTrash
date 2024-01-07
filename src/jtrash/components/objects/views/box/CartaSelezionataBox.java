package jtrash.components.objects.views.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.handlers.AnimationsHandler;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.views.box.interfaces.IboxInterface;

/**
 * Classe che rappresenta visivamente la carta selezionata dal giocatore, cioè quella appena pescata, dal mazzo o dalle carte scartate
 * @author tizia
 *
 */
@SuppressWarnings("deprecation")
public class CartaSelezionataBox implements IboxInterface, Observer {

	private static CartaSelezionataBox instance;

	private Rectangle box;

	private CartaSelezionataBox() {

	}

	public static CartaSelezionataBox getInstance() {
		if (instance == null) {
			instance = new CartaSelezionataBox();
			instance.setBox(GameHandler.getInstance().getCartaSelezionata());
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		Carta carta = (Carta) arg;
		setBoxFill(carta.getFill());
	}

	@Override
	public void setBoxFill(Rectangle box) {
		setBoxFill(box.getFill());
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
		this.box.setFill(fill);
		handleAnimazioneIngrandimento();
	}
	
	/**
	 * Gestice l'animazione di ingrandimento della carta nel box, mostrandola se il box non è vuoto
	 */
	private void handleAnimazioneIngrandimento() {
		if (!this.box.getFill().equals(Color.WHITE)) {
			AnimationsHandler.animazioneIngrandimento(this.box);
		}
	}
	

	public Rectangle getBox() {
		return box;
	}

}
