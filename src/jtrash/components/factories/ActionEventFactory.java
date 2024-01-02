package jtrash.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;
import jtrash.components.observables.SelezioneCartaObservable;
import jtrash.components.scenes.MainMenu;
import jtrash.components.scenes.Playground;

public class ActionEventFactory {

	public static EventHandler<ActionEvent> azioneIniziaPartita(Scene scene, TextField inputUsername,ComboBox<Integer> selettoreAvversari) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainMenu.getInstance().setUsernameUtente(inputUsername.getText());
				
				GameHandler.getInstance().aggiungiGiocatore(inputUsername.getText());
				
				for (int i = 1; i <= selettoreAvversari.getValue(); i++) {
					GameHandler.getInstance().aggiungiGiocatore("BOT " + i);
				}
				
				Playground.getInstance().updatePlayground(true);
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
