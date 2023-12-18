package jtrash.components.objects.box;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Mazzo;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

@SuppressWarnings("deprecation")
public class CarteMazzoBox implements IboxInterface, Observer {

	private static CarteMazzoBox instance;

	private Rectangle box;

	private CarteMazzoBox() {

	}

	public static CarteMazzoBox getInstance() {
		if (instance == null) {
			instance = new CarteMazzoBox();
			instance.setBox(GameHandler.getInstance().getCartaSelezionata().getCartaShape());
			instance.box.setFill(ImagePatternFactory.generaImmagine(
					FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + IMAGES_ENUM.RETRO_CARTE.getNomeImmagine()));
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

}
