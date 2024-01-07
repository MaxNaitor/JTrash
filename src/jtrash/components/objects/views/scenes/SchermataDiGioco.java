package jtrash.components.objects.views.scenes;

import java.util.Arrays;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import jtrash.components.factories.BoxFactory;

/**
 * Classe che rappresenta l'intera schermata di gioco durante la partita, divisa in PlayGround e ActionGround
 * @author tizia
 *
 */
public class SchermataDiGioco {

	private static SchermataDiGioco instance;

	private SchermataDiGioco() {

	}

	public static SchermataDiGioco getInstance() {
		if (instance == null) {
			instance = new SchermataDiGioco();
		}
		return instance;
	}

	/**
	 * Restituisce la schermata di gioco come una HBox composta da PlayGround e ActionGround
	 * @return HBox
	 */
	public HBox getSchermataDiGioco() {
		GridPane playground = PlayGround.getInstance().getPlayground();
		GridPane actionground = ActionGround.getInstance().getActionground();

		HBox box = BoxFactory.generaBoxOrizzontaleNodi(Arrays.asList(playground, actionground));
		HBox.setHgrow(playground, Priority.ALWAYS);
		return box;
	}
}
