package jtrash.components.observables;

import java.util.Observable;

import jtrash.components.objects.controllers.GameController;
import jtrash.components.objects.models.Carta;
import jtrash.components.objects.views.box.CartaSelezionataBox;

@SuppressWarnings("deprecation")
public class SelezioneCartaObservable extends Observable {

	private static SelezioneCartaObservable instance;

	private SelezioneCartaObservable() {
		addObserver(GameController.getInstance());
		addObserver(CartaSelezionataBox.getInstance());
	}

	public static SelezioneCartaObservable getInstance() {
		if (instance == null) {
			instance = new SelezioneCartaObservable();
		}
		return instance;
	}

	/**
	 * Quando viene pescata una nuova carta dal mazzo coperto o dal mazzo scoperto, notifica gli Observer
	 * @param carta
	 */
	public void aggiornaSelezioneCarta(Carta carta) {
		setChanged();
		notifyObservers(carta);
	}
}
