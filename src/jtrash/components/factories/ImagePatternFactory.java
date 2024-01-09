package jtrash.components.factories;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Factory per la creazione di oggetti ImagePattern
 * @author tizia
 *
 */
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
