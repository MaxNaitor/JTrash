package jtrash.components.factories;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Factory per la creazione di oggetti Text
 * @author tizia
 *
 */
public class TextFactory {

	/**
	 * Genera un Text semplice di un colore specificato
	 * @param testo
	 * @param colore
	 * @return Text
	 */
	public static Text generaTesto(String testo, Color colore) {
		Text text = new Text(testo);
		text.setFill(colore);
		return text;
	}

	
	/**
	 * Genera un Text di un colore specificato, personalizzabile con un FontWeight e una dimensione per il testo
	 * @param testo
	 * @param colore
	 * @param fontWeight
	 * @param dimensione
	 * @return Text
	 */
	public static Text generaTesto(String testo, Color colore, FontWeight fontWeight, int dimensione) {
		Text text = generaTesto(testo, colore);
		text.setFont(Font.font("Consolas", fontWeight, dimensione));
		return text;
	}
}
