package jtrash.components.scenes;

import java.util.Arrays;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import jtrash.components.factories.BoxFactory;

public class Gioco {

	private static Gioco instance;

	private Gioco() {

	}

	public static Gioco getInstance() {
		if (instance == null) {
			instance = new Gioco();
		}
		return instance;
	}

	public HBox getGioco() {
		GridPane playground = Playground.getInstance().getPlayground();
		GridPane actionground = Actionground.getInstance().getActionground();

		HBox box = BoxFactory.generaBoxOrizzontaleNodi(Arrays.asList(playground, actionground));
		HBox.setHgrow(playground, Priority.ALWAYS);
		return box;
	}
}
