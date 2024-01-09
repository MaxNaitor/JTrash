package jtrash.components.objects.views.box.interfaces;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Interfaccia per le box di visualizzazione delle carte nell'actionground
 * @author tizia
 *
 */
public interface IboxInterface {
	/**
	 * Imposta il Fill del box con il Fill del Rectangle in input
	 * @param box
	 */
	void setBoxFill(Rectangle box);
	
	/**
	 * Imposta il Fill del box con il Paint in input
	 * @param fill
	 */
	void setBoxFill(Paint fill);

	/**
	 * inizializza il box per la visualizzazione della carta. <br>
	 * Se già inizializzato, ne modifica solo il Fill
	 */
	void setBox(Rectangle box);

}
