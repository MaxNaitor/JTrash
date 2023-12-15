package jtrash.components.factories;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtrash.components.objects.Carta;

public class BoxFactory {

	public static HBox generaBoxOrizzontaleNodi(List<Node> nodi) {
		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren().addAll(nodi);
		return box;
	}

	public static HBox generaBoxOrizzontaleCarte(List<Carta> carte) {
		List<Button> pulsantiCarte = new ArrayList<>();
		carte.forEach(c -> pulsantiCarte.add(ButtonFactory.generaTasto("", c, ActionEventFactory.azioneGiraCarta(c))));
		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren().addAll(pulsantiCarte);
		return box;
	}

	public static VBox generaBoxVerticaleNodi(List<Node> nodi) {
		VBox box = new VBox();
		box.setSpacing(10);
		box.getChildren().addAll(nodi);
		return box;
	}
	
	public static VBox generaBoxVerticaleHbox(List<HBox> boxList) {
		VBox box = new VBox();
		box.setSpacing(10);
		box.getChildren().addAll(boxList);
		return box;
	}
}
