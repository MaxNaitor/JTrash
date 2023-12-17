package jtrash.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import jtrash.components.objects.Carta;
import jtrash.components.observables.SelezioneCartaObservable;

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
				System.out.println("Carta da girare: " + carta.getValore() + " " + carta.getSeme());
				carta.giraCarta();

			}
		};
	}
	
	public static EventHandler<ActionEvent> azioneSelezionaCarta(Carta carta) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SelezioneCartaObservable.getInstance().aggiornaSelezioneCarta(carta);

			}
		};
	}
}
