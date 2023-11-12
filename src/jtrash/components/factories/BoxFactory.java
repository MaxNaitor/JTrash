package jtrash.components.factories;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxFactory {

	public static HBox generaBoxOrizzontale(List<Node> nodi) {
		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren().addAll(nodi);
		return box;
	}

	public static VBox generaBoxVerticale(List<Node> nodi) {
		VBox box = new VBox();
		box.setSpacing(10);
		box.getChildren().addAll(nodi);
		return box;
	}
}
