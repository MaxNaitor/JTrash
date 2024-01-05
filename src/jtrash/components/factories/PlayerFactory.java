package jtrash.components.factories;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import jtrash.components.objects.Carta;
import jtrash.components.objects.Player;

public class PlayerFactory {

	public static Player creaPlayer(List<Carta> carte,String nome, Rectangle avatar) {
		return new Player(carte,nome,avatar);
	}
	
//	public static VBox generaCampoPlayer(List<Carta> carte) {
//		Player player = PlayerFactory.creaPlayer(carte);
//		return BoxFactory.generaBoxVerticaleHbox(player.distribuisciCarteSulTavolo());
//	}
	
	public static VBox generaCampoPlayer(Player player) {
	return BoxFactory.generaBoxVerticaleHbox(player.distribuisciCarteSulTavolo());
}
}
