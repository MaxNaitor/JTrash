package jtrash.components.factories;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

public class GridPaneFactory {

	public static GridPane generaGridPane(Background background) {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		grid.setBackground(background);
		return grid;
	}
}
