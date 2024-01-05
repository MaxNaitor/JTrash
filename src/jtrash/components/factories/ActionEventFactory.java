package jtrash.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import jtrash.components.objects.handlers.GameHandler;
import jtrash.components.objects.handlers.UtentiHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.observables.SelezioneCartaObservable;

public class ActionEventFactory {

	public static EventHandler<ActionEvent> azioneIniziaPartita(Scene scene,ComboBox<Integer> selettoreAvversari) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameHandler.getInstance().startNewGame(UtentiHandler.getInstance().getUtenteAttivo().getUsername(), selettoreAvversari.getValue());
				SceneFactory.getInstance().cambiaScena(scene);
			}
		};
	}

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
