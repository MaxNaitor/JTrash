package jtrash.components.factories;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BackgroundFactory {

	/**
	 * Crea un Background con l'immagine indicata in input.
	 * @param urlImmagine
	 * @return Background con l'immagine selezionata
	 */
	public static Background generaBackground(String urlImmagine) {
		Image image = new Image("file:" + urlImmagine);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);

		return new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, size));
	}
}
