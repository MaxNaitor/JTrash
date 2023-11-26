package jtrash.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class ButtonFactory {

	public static Button generaTasto(String testo,EventHandler<ActionEvent> azione) {
		Button button = new Button(testo);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(azione);
		return button;
	}

	private static void aggiungiEffettiMouseHover(Button button) {
		DropShadow shadow = new DropShadow();
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setCursor(Cursor.HAND);
				button.setEffect(shadow);
			}
		});

	}
}
