package jtrash.components.objects.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;

@SuppressWarnings("deprecation")
public class CartaSelezionataBox implements IboxInterface, Observer {

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

	@Override
	public void setBoxFill(Rectangle box) {
		this.box.setFill(box.getFill());
	}

	@Override
	public void setBox(Rectangle box) {
		//solo il primo set nel getinstance deve inizializzare il box
		if (this.box != null) {
			setBoxFill(box);
			return;
		}
		this.box = box;
	}

	@Override
	public void setBoxFill(Paint fill) {
		this.box.setFill(fill);
	}

}
