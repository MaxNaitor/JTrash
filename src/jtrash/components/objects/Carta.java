package jtrash.components.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Rectangle;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.objects.abstractions.CartaAbstract;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.SEMI_ENUM;
import jtrash.enums.VALORI_CARTE_ENUM;

public class Carta extends CartaAbstract {

	private SEMI_ENUM seme;
	private VALORI_CARTE_ENUM valore;

	private SimpleBooleanProperty isCoperta = new SimpleBooleanProperty(true);

	public Carta(SEMI_ENUM seme, VALORI_CARTE_ENUM valore) {
		this.seme = seme;
		this.valore = valore;

		generaCarta();

		// aggiungo la carta come children di Parent per poterla visualizzare
		getChildren().add(this.cartaShape);
	}

	public Carta() {
		generaCarta();

		// aggiungo la carta come children di Parent per poterla visualizzare
		getChildren().add(this.cartaShape);
	}

	public Rectangle giraCarta() {
		boolean cartaIsCoperta = isCoperta.get();

		if (cartaIsCoperta) {
			if (this.immagineCarta == null) {
				String nomeImmagineCarta = this.seme + "_" + this.valore;
				this.immagineCarta = ImagePatternFactory
						.generaImmagine(FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + nomeImmagineCarta + ".png");
			}
			// se la carta è coperta, la scopro rivelando l'immagine
			this.cartaShape.setFill(this.immagineCarta);
		} else {
			// se la carta è scoperta, la copro mostrando il retro
			this.cartaShape.setFill(this.retro);
		}
		// aggiorno lo stato della carta
		this.isCoperta.set(false);

		return getCartaShape();
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
	
	public boolean isWildcard() {
		return valore.equals(VALORI_CARTE_ENUM.RE) || valore.equals(VALORI_CARTE_ENUM.JOLLY);
	}

}
