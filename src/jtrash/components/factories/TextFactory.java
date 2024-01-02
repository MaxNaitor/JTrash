package jtrash.components.factories;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TextFactory {

	public static Text generaTesto(String testo, Color colore) {
		Text text = new Text(testo);
		text.setFill(colore);
		return text;
	}

	public static Text generaTesto(String testo, Color colore, FontWeight fontWeight, int dimensione) {
		Text text = generaTesto(testo, colore);
		text.setFont(Font.font("Consolas", fontWeight, dimensione));
		return text;
	}
}
