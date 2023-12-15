package jtrash.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import jtrash.components.objects.Carta;

public class ActionEventFactory {

	public static EventHandler<ActionEvent> azioneCambioScena(Scene scene) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SceneFactory.getInstance().cambiaScena(scene);

			}
		};
	}

	public static EventHandler<ActionEvent> azioneGiraCarta(Carta carta) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				carta.giraCarta();

			}
		};
	}
}
