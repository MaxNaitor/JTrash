package jtrash.components.factories;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

/**
 * Factory per la creazione di oggetti GridPane
 * @author tizia
 *
 */
public class GridPaneFactory {

	/**
	 * genera un GridPane con il Background passato in input.
	 * @param background
	 * @return GridPane
	 */
	public static GridPane generaGridPane(Background background) {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		grid.setBackground(background);
		return grid;
	}
}
