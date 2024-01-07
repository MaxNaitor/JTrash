package jtrash.components.factories;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ImagePatternFactory {

	
	/**
	 * Restituisce un ImagePattern contentente l'immagine passata in input come url
	 * @param urlImmagine
	 * @return ImagePattern
	 */
	public static ImagePattern generaImmagine(String urlImmagine) {
		return new ImagePattern(new Image("file:" + urlImmagine));
	}
}
