package jtrash.components.objects;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Rectangle;
import jtrash.components.handlers.GameHandler;

public class CartaSelezionataBox implements Observer {

	private static CartaSelezionataBox instance;

	private Rectangle box;

	private CartaSelezionataBox() {

	}

	public static CartaSelezionataBox getInstance() {
		if (instance == null) {
			instance = new CartaSelezionataBox();
			instance.setBox(GameHandler.getInstance().getCartaSelezionata().getCartaShape());
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		Carta carta = (Carta) arg;
		box.setFill(carta.getCartaShape().getFill());

	}

	public Rectangle getBox() {
		return box;
	}

	public void setBox(Rectangle box) {
		this.box = box;
	}

}
