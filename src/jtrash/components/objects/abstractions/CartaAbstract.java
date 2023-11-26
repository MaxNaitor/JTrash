package jtrash.components.objects.abstractions;

import javafx.scene.Parent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.objects.interfaces.ICarta;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public abstract class CartaAbstract extends Parent implements ICarta {

	protected Rectangle cartaShape;
	protected ImagePattern retro;
	protected ImagePattern immagineCarta;

	public void generaCarta() {
		this.cartaShape = new Rectangle(WIDTH, HEIGHT);

		// bordo tondo per le carte
		this.cartaShape.setArcWidth(15);
		this.cartaShape.setArcHeight(15);

		this.retro = ImagePatternFactory.generaImmagine(
				FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + IMAGES_ENUM.RETRO_CARTE.getNomeImmagine());

		this.cartaShape.setFill(this.retro);
	};

}
