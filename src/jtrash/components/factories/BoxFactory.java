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
import jtrash.components.objects.controllers.UtentiController;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.models.Player;

public class BoxFactory {

	/**
	 * Crea una nuova HBox con uno spacing di 10.
	 * 
	 * @return HBox
	 */
	private static HBox generaBoxOrizzontaleBase() {
		HBox box = new HBox();
		box.setSpacing(10);
		return box;
	}

	/**
	 * Crea una nuova VBox con uno spacing di 10.
	 * 
	 * @return VBox
	 */
	private static VBox generaBoxVerticaleBase() {
		VBox box = new VBox();
		box.setSpacing(10);
		return box;
	}

	/**
	 * Crea una nuova HBox contenente la lista di nodi passati in input.
	 * 
	 * @param nodi da inserire nella HBox
	 * @return HBox
	 */
	public static HBox generaBoxOrizzontaleNodi(List<Node> nodi) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}

	/**
	 * Crea una nuova HBox con le carte disposte in fila.
	 * 
	 * @param carte
	 * @return HBox
	 */
	public static HBox generaBoxOrizzontaleCarte(List<Carta> carte) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(carte);
		return box;
	}

	/**
	 * Crea una nuova HBox contenenti la lista di bottoni passati in input.
	 * 
	 * @param bottoni
	 * @return HBox
	 */
	public static HBox generaBoxOrizzontaleBottoni(List<Button> bottoni) {
		HBox box = generaBoxOrizzontaleBase();
		box.getChildren().addAll(bottoni);
		return box;
	}

	/**
	 * Crea una nuova HBox contenente il nome e l'avatar dell'utente per la
	 * visualizzazione nel menu principale.
	 * 
	 * @return HBox
	 */
	public static HBox getBoxUtente() {
		HBox boxUtenteAttivo = generaBoxOrizzontaleNodi(Arrays.asList(
				TextFactory.generaTesto("Ciao " + UtentiController.getInstance().getUtenteAttivo().getUsername() + "!",
						Color.WHITE, FontWeight.BOLD, 30)));
		boxUtenteAttivo.getChildren().add(UtentiController.getInstance().getUtenteAttivo().getAvatar());

		boxUtenteAttivo.setAlignment(Pos.CENTER_LEFT);
		return boxUtenteAttivo;
	}

	/**
	 * Crea una nuova HBox contenente il nome e l'avatar del giocatore per la
	 * visualizzazione durante le partite.
	 * 
	 * @param giocatore
	 * @param dimensioneNome
	 * @return HBox
	 */
	public static HBox getBoxGiocatore(Player giocatore, int dimensioneNome) {
		HBox boxUtenteAttivo = generaBoxOrizzontaleNodi(Arrays
				.asList(TextFactory.generaTesto(giocatore.getNome(), Color.WHITE, FontWeight.BOLD, dimensioneNome)));
		boxUtenteAttivo.getChildren().add(giocatore.getAvatar());

		boxUtenteAttivo.setAlignment(Pos.CENTER_LEFT);
		return boxUtenteAttivo;
	}

	/**
	 * Crea una nuova VBox contenente la lista di nodi passati in input.
	 * 
	 * @param nodi da inserire nella VBox
	 * @return VBox
	 */
	public static VBox generaBoxVerticaleNodi(List<Node> nodi) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(nodi);
		return box;
	}

	/**
	 * Crea una nuova VBox contenente la lista di HBox passati in input.
	 * 
	 * @param boxList da inserire nella VBox
	 * @return VBox
	 */
	public static VBox generaBoxVerticaleHbox(List<HBox> boxList) {
		VBox box = generaBoxVerticaleBase();
		box.getChildren().addAll(boxList);
		return box;
	}

}
