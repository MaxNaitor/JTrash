package jtrash.components.objects.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Mazzo;

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
			instance.setBox(GameHandler.getInstance().getCartaSelezionata().getCartaShape());
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		Mazzo mazzo = (Mazzo) arg;
		System.out.println("carte scoperte " + mazzo.getCarteScoperte().size());
		if (mazzo.getCarteScoperte().isEmpty()) {
			resetCartaDaVisualizzare();
		} else {
			instance.cartaDaVisualizzare = mazzo.getCarteScoperte().get(mazzo.getCarteScoperte().size() - 1);
			System.out.println("carta scartata: " + this.cartaDaVisualizzare.getSeme() + " " + this.cartaDaVisualizzare.getValore());
		}
		instance.setBox(instance.cartaDaVisualizzare.getCartaShape());

	}
	
	private void resetCartaDaVisualizzare() {
		cartaDaVisualizzare = new Carta();
		cartaDaVisualizzare.getCartaShape().setFill(Color.WHITE);
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
		// solo il primo set nel getinstance deve inizializzare il box
		if (this.box != null) {
			setBoxFill(box);
			return;
		}
		this.box = box;
	}

	public Carta getCartaDaVisualizzare() {
		if (cartaDaVisualizzare == null) {
			resetCartaDaVisualizzare();
		}
		return cartaDaVisualizzare;
	}

}
