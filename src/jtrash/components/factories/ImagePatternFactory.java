package jtrash.components.factories;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ImagePatternFactory {

	public static ImagePattern generaImmagine(String urlImmagine) {
		return new ImagePattern(new Image("file:" + urlImmagine));
	}
}
