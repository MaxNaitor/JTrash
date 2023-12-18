package jtrash.components.observables;

import java.util.Observable;

import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.Carta;
import jtrash.components.objects.box.CartaSelezionataBox;

public class SelezioneCartaObservable extends Observable{

	private static SelezioneCartaObservable instance;
	
	private SelezioneCartaObservable() {
		addObserver(GameHandler.getInstance());
		addObserver(CartaSelezionataBox.getInstance());
	}
	
	public static SelezioneCartaObservable getInstance() {
		if (instance == null) {
			instance = new SelezioneCartaObservable();
		}
		return instance;
	}
	
	public void aggiornaSelezioneCarta(Carta carta) {
		setChanged();
		notifyObservers(carta);
	}
}
