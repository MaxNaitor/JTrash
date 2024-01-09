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
import jtrash.components.objects.controllers.UtentiController;

/**
 * Factory per la creazione di oggetti Button
 * @author tizia
 *
 */
public class ButtonFactory {

	/**
	 * Restituisce un button che svolge l'azione passata in input 
	 * @param testo
	 * @param azione
	 * @return Button
	 */
	public static Button generaTasto(String testo, EventHandler<ActionEvent> azione) {
		Button button = new Button(testo);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(azione);
		return button;
	}

	
	/**
	 * Restituisce un button contentente un nodo che svolge l'azione passata in input 
	 * @param testo
	 * @param azione
	 * @return Button
	 */
	public static Button generaTasto(String testo, Node nodo, EventHandler<ActionEvent> azione) {
		Button button = new Button(testo, nodo);
		aggiungiEffettiMouseHover(button);
		button.setOnAction(azione);
		return button;
	}


	/**
	 * Restituisce una lista di button che permettono la selezione degli avatar in fase di registrazione utente
	 * @return List button
	 */
	public static List<Button> generaTastiSelezioneAvatar() {
		List<Button> bottoniAvatar = new ArrayList<>();
		for (Rectangle avatar : AvatarFactory.getAvatarSelezionabiliGiocatore()) {
			bottoniAvatar.add(generaTasto("", avatar, e -> UtentiController.getInstance().setAvatarSelezionato(avatar)));
		}
		return bottoniAvatar;
	}

	/**
	 * Aggiunge un effetto al button che cambia il cursore e l'ombreggiatura quando il mouse passa sopra
	 * @param button
	 */
	private static void aggiungiEffettiMouseHover(Button button) {
		DropShadow shadow = new DropShadow();

		button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
			button.setCursor(Cursor.HAND);
			button.setEffect(shadow);
		});

	}
}
