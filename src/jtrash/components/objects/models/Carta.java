package jtrash.components.objects.models;

import javafx.beans.property.SimpleBooleanProperty;
import jtrash.components.factories.ImagePatternFactory;
import jtrash.components.objects.models.abstractions.CartaAbstract;
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
	}

	public Carta() {
		generaCarta();
	}

	/**
	 * Gestisce il cambio tra carta coperta e scoperta, mostrando il retro o
	 * l'immagine specifica della carta e impostando un flag che indica logicamente
	 * lo stato della carta.
	 */
	public void giraCarta() {
		boolean cartaIsCoperta = isCoperta.get();

		if (cartaIsCoperta) {
			if (this.immagineCarta == null) {
				String nomeImmagineCarta = this.seme + "_" + this.valore;
				this.immagineCarta = ImagePatternFactory
						.generaImmagine(FOLDERS_ENUM.IMMAGINI_CARTE.getFolderLocation() + nomeImmagineCarta + ".png");
			}
			// se la carta è coperta, la scopro rivelando l'immagine
			setFill(this.immagineCarta);
			// aggiorno lo stato della carta
			this.isCoperta.set(false);
		} else {
			// se la carta è scoperta, la copro mostrando il retro
			setFill(this.retro);
			// aggiorno lo stato della carta
			this.isCoperta.set(true);
		}
	}

	/**
	 * Restituisce true se la carta è una wildcard (Re o Jolly)
	 * 
	 * @return true se la carta è una wildcard
	 */
	public boolean isWildcard() {
		return valore.equals(VALORI_CARTE_ENUM.RE) || valore.equals(VALORI_CARTE_ENUM.JOLLY);
	}

	/**
	 * Restituisce true se la carta è posizionabile (diversa da Regina o Jack)
	 * 
	 * @return true se la carta è posizionabile
	 */
	public boolean isPosizionabile() {
		return !valore.equals(VALORI_CARTE_ENUM.REGINA) && !valore.equals(VALORI_CARTE_ENUM.JACK);
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
