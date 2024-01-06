package jtrash.components.factories;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import jtrash.components.objects.handlers.UtentiHandler;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Player;

public class BoxFactory {
	
	public static HBox generaBoxOrizzontaleBase() {
		HBox box = new HBox();
		box.setSpacing(10);
		return box;
	}

	public static HBox generaBoxOrizzontaleNodi(List<Node> nodi) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}

	/**
	 * crea una HBox con le carte disposte in fila,inserendoli come button per abilitarne la selezione
	 * @param carte
	 * @return HBox
	 */
	public static HBox generaBoxOrizzontaleCarte(List<Carta> carte) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(carte);
		return box;
	}
	
	public static VBox generaBoxVerticaleBase() {
		VBox box = new VBox();
		box.setSpacing(10);
		return box;
	}

	public static VBox generaBoxVerticaleNodi(List<Node> nodi) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}
	
	public static VBox generaBoxVerticaleHbox(List<HBox> boxList) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(boxList);
		return box;
	}
	
	public static HBox generaBoxOrizzontaleBottoni(List<Button> bottoni) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(bottoni);
		return box;
	}
	
	public static HBox getBoxUtente() {
		HBox boxUtenteAttivo = generaBoxOrizzontaleNodi(Arrays.asList(TextFactory.generaTesto(
				"Ciao " + UtentiHandler.getInstance().getUtenteAttivo().getUsername() + "!", Color.WHITE, FontWeight.BOLD, 30)));
		boxUtenteAttivo.getChildren().add(UtentiHandler.getInstance().getUtenteAttivo().getAvatar());
		
		boxUtenteAttivo.setAlignment(Pos.CENTER_LEFT);
		return boxUtenteAttivo;
	}
	
	public static HBox getBoxGiocatore(Player giocatore,int dimensioneNome) {
		HBox boxUtenteAttivo = generaBoxOrizzontaleNodi(Arrays.asList(TextFactory.generaTesto(
				giocatore.getNome(), Color.WHITE, FontWeight.BOLD, dimensioneNome)));
		boxUtenteAttivo.getChildren().add(giocatore.getAvatar());
		
		boxUtenteAttivo.setAlignment(Pos.CENTER_LEFT);
		return boxUtenteAttivo;
	}
}
