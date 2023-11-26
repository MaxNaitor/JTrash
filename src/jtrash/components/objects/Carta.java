package jtrash.components.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;
import jtrash.enums.SEMI_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

public class Carta extends Parent {

	// dimensioni delle carte
	public final int WIDTH = 80;
	public final int HEIGHT = 120;

	private SEMI_ENUM seme;
	private VALORI_CARTE_ENUM valore;

	private Rectangle carta;
	private ImagePattern retro;
	private ImagePattern immagineCarta;
	private SimpleBooleanProperty isCoperta = new SimpleBooleanProperty(true);

	public Carta(SEMI_ENUM seme, VALORI_CARTE_ENUM valore) {
		this.seme = seme;
		this.valore = valore;

		this.retro = ImagePatternFactory.generaImmagine(
				FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + IMAGES_ENUM.RETRO_CARTE.getNomeImmagine());

		this.carta = new Rectangle(WIDTH, HEIGHT);

		// bordo tondo per le carte
		this.carta.setArcWidth(15);
		this.carta.setArcHeight(15);

		this.carta.setFill(this.retro);

		// aggiungo la carta come children di Parent per poterla visualizzare
		getChildren().add(this.carta);
	}

	public void giraCarta() {
		boolean cartaIsCoperta = isCoperta.get();

		if (cartaIsCoperta) {
			if (this.immagineCarta == null) {
				String nomeImmagineCarta = this.seme + "_" + this.valore;
				this.immagineCarta = ImagePatternFactory
						.generaImmagine(FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + nomeImmagineCarta);
			}
			//se la carta è coperta, la scopro rivelando l'immagine
			this.carta.setFill(this.immagineCarta);
		} else {
			this.carta.setFill(this.retro);
		}
		this.isCoperta.set(!cartaIsCoperta);
	}

	public SEMI_ENUM getSeme() {
		return seme;
	}

	public VALORI_CARTE_ENUM getValore() {
		return valore;
	}

	public boolean isCoperta() {
		return isCoperta.get();
	}

}
