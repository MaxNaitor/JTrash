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

@SuppressWarnings("deprecation")
public class CarteMazzoBox implements IboxInterface, Observer {

	private static CarteMazzoBox instance;

	private Rectangle box;
	
	private Carta cartaInCima;

	private CarteMazzoBox() {

	}

	public static CarteMazzoBox getInstance() {
		if (instance == null) {
			List<Carta> carteCoperte = GameHandler.getInstance().getMazzo().getCarteCoperte();
			instance = new CarteMazzoBox();
			instance.cartaInCima = carteCoperte.get(carteCoperte.size() - 1);
			instance.setBox(instance.cartaInCima.getCartaShape());
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		Mazzo mazzo = (Mazzo) arg;
		if (mazzo.getCarteCoperte().isEmpty()) {
			Carta cartaDaVisualizzare = new Carta();
			cartaDaVisualizzare.getCartaShape().setFill(Color.WHITE);
			box.setFill(cartaDaVisualizzare.getCartaShape().getFill());
		}  else {
			instance.cartaInCima = mazzo.getCarteCoperte().get(mazzo.getCarteCoperte().size() - 1);
			instance.setBox(instance.cartaInCima.getCartaShape());
		}
		
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
		// TODO Auto-generated method stub
		
	}

}
