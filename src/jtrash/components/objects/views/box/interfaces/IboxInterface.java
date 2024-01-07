package jtrash.components.objects.views.box.interfaces;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public interface IboxInterface {
	/**
	 * Imposta il Fill del box con il Fill del Rectangle in input
	 * @param fill
	 */
	void setBoxFill(Rectangle box);
	
	/**
	 * Imposta il Fill del box con il Paint in input
	 * @param fill
	 */
	void setBoxFill(Paint fill);

	/**
	 * inizializza il box per la visualizzazione della carta. Se già inizializzato, ne modifica solo il Fill
	 */
	void setBox(Rectangle box);

}
