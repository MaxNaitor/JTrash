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
		handleAnimazioneIngrandimento();
	}
	
	private void handleAnimazioneIngrandimento() {
		if (!this.box.getFill().equals(Color.WHITE)) {
			AnimationsHandler.animazioneIngrandimento(this.box);
		}
	}

}
