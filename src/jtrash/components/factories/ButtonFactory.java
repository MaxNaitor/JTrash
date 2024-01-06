package jtrash.components.factories;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.handlers.UtentiHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.observables.SelezioneCartaObservable;

public class ButtonFactory {

	public static Button generaTasto(String testo, EventHandler<ActionEvent> azione) {
		Button button = new Button(testo);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(azione);
		return button;
	}

	public static Button generaTasto(String testo, Node nodo, EventHandler<ActionEvent> azione) {
		Button button = new Button(testo, nodo);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(azione);
		return button;
	}

	public static Button generaTastoSelezioneCarta(Carta carta, boolean disabilita) {
		Button button = new Button("", carta);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(e -> SelezioneCartaObservable.getInstance().aggiornaSelezioneCarta(carta));
		button.setDisable(disabilita);
		return button;
	}

	public static List<Button> generaTastiSelezioneAvatar() {
		List<Button> bottoniAvatar = new ArrayList<>();
		for (Rectangle avatar : AvatarFactory.getAvatarSelezionabiliGiocatore()) {
			bottoniAvatar.add(generaTasto("", avatar, e -> UtentiHandler.getInstance().setAvatarSelezionato(avatar)));
		}
		return bottoniAvatar;
	}

	private static void aggiungiEffettiMouseHover(Button button) {
		DropShadow shadow = new DropShadow();

		button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
			button.setCursor(Cursor.HAND);
			button.setEffect(shadow);
		});

	}
}
