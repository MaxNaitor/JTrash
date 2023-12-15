package jtrash.components.factories;

import java.util.List;

import javafx.scene.layout.VBox;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Player;

public class PlayerFactory {

	public static Player creaPlayer(List<Carta> carte) {
		return new Player(carte);
	}
	
	public static VBox generaCampoPlayer(List<Carta> carte) {
		Player player = PlayerFactory.creaPlayer(carte);
		return BoxFactory.generaBoxVerticaleHbox(player.distribuisciCarteSulTavolo());
	}
}
