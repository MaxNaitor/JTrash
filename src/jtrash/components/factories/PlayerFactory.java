package jtrash.components.factories;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Player;

/**
 * Factory per la creazione di oggetti Player
 * @author tizia
 *
 */
public class PlayerFactory {

	/**
	 * Crea un nuovo giocatore per la partita
	 * @param carte
	 * @param nome
	 * @param avatar
	 * @return Player
	 */
	public static Player creaPlayer(List<Carta> carte,String nome, Rectangle avatar) {
		return new Player(carte,nome,avatar);
	}
	
	/**
	 * Restituisce una VBox contente due HBox, che rappresentano le carte del giocatore distribuite sul tavolo
	 * @param player
	 * @return VBox
	 */
	public static VBox generaCampoPlayer(Player player) {
	return BoxFactory.generaBoxVerticaleHbox(player.distribuisciCarteSulTavolo());
}
}
