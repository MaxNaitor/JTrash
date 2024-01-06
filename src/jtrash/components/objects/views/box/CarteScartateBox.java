package jtrash.components.objects.views.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.handlers.AnimationsHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Mazzo;
import jtrash.components.objects.views.box.interfaces.IboxInterface;

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
			instance.cartaDaVisualizzare = new Carta();
			instance.cartaDaVisualizzare.setFill(Color.WHITE);
			instance.setBox(instance.cartaDaVisualizzare);
		}
		return instance;
	}

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

	private void resetCartaDaVisualizzare() {
		cartaDaVisualizzare = new Carta();
		cartaDaVisualizzare.setFill(Color.WHITE);
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
		// solo il primo set nel getinstance deve inizializzare il box
		if (this.box != null) {
			setBoxFill(box);
			return;
		}
		this.box = box;
	}

	public Carta getCartaDaVisualizzare() {
		if (instance.cartaDaVisualizzare == null) {
			resetCartaDaVisualizzare();
		}
		return instance.cartaDaVisualizzare;
	}

	@Override
	public void setBoxFill(Paint fill) {
		// TODO Auto-generated method stub

	}
	
	private void handleAnimazioneIngrandimento() {
		if (!this.box.getFill().equals(Color.WHITE)) {
			AnimationsHandler.animazioneIngrandimento(this.box);
		}
	}

}
