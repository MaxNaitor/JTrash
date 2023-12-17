package jtrash.components.factories;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtrash.components.objects.Carta;

public class BoxFactory {
	
	public static HBox generaBoxOrizzontaleBase() {
		HBox box = new HBox();
		box.setSpacing(10);
		return box;
	}

	public static HBox generaBoxOrizzontaleNodi(List<Node> nodi) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}

	public static HBox generaBoxOrizzontaleCarte(List<Carta> carte) {
		List<Button> pulsantiCarte = new ArrayList<>();
		carte.forEach(c -> pulsantiCarte.add(ButtonFactory.generaTasto("", c, ActionEventFactory.azioneSelezionaCarta(c))));
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(pulsantiCarte);
		return box;
	}
	
	public static VBox generaBoxVerticaleBase() {
		VBox box = new VBox();
		box.setSpacing(10);
		return box;
	}

	public static VBox generaBoxVerticaleNodi(List<Node> nodi) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}
	
	public static VBox generaBoxVerticaleHbox(List<HBox> boxList) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(boxList);
		return box;
	}
}
