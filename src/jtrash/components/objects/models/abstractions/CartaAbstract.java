package jtrash.components.objects.models.abstractions;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.objects.models.interfaces.ICarta;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public abstract class CartaAbstract extends Rectangle implements ICarta {

	protected ImagePattern retro;
	protected ImagePattern immagineCarta;

	public void generaCarta() {
		setHeight(HEIGHT);
		setWidth(WIDTH);

		// bordo tondo per le carte
		setArcWidth(ARC_DIMENSIONS);
		setArcHeight(ARC_DIMENSIONS);

		this.retro = ImagePatternFactory.generaImmagine(
				FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + IMAGES_ENUM.RETRO_CARTE.getNomeImmagine());

		setFill(this.retro);
	}

	public ImagePattern getRetro() {
		return retro;
	}

	public void setRetro(ImagePattern retro) {
		this.retro = retro;
	}

	public ImagePattern getImmagineCarta() {
		return immagineCarta;
	}

	public void setImmagineCarta(ImagePattern immagineCarta) {
		this.immagineCarta = immagineCarta;
	};

}
