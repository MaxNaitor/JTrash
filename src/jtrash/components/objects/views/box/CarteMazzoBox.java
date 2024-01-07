package jtrash.components.objects.views.box;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Mazzo;
import jtrash.components.objects.views.box.interfaces.IboxInterface;

/**
 * Classe che rappresenta visivamente le carte del mazzo coperto
 * 
 * @author tizia
 *
 */
@SuppressWarnings("deprecation")
public class CarteMazzoBox implements IboxInterface, Observer {

	private static CarteMazzoBox instance;

	private Rectangle box;

	private CarteMazzoBox() {

	}

	public static CarteMazzoBox getInstance() {
		if (instance == null) {
			instance = new CarteMazzoBox();
			inizializzaBox();
		}
		return instance;
	}
	
	/**
	 * Inizializza la box come la prima carta coperta del mazzo, in modo che sia sempre una carta coperta
	 */
	private static void inizializzaBox() {
		List<Carta> carteCoperte = GameHandler.getInstance().getMazzo().getCarteCoperte();
		instance.setBox(carteCoperte.get(carteCoperte.size() - 1));
	}

	/**
	 * Gestisce la visualizzazione del mazzo coperto, impostando sfondo bianco se
	 * tutte le carte del mazzo sono state pescate
	 */
	@Override
	public void update(Observable o, Object arg) {
		Mazzo mazzo = (Mazzo) arg;
		if (mazzo.getCarteCoperte().isEmpty()) {
			Carta cartaDaVisualizzare = new Carta();
			cartaDaVisualizzare.setFill(Color.WHITE);
			box.setFill(cartaDaVisualizzare.getFill());
		}
	}

	@Override
	public void setBoxFill(Rectangle box) {
		this.box.setFill(box.getFill());
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

	public Rectangle getBox() {
		return box;
	}
}
