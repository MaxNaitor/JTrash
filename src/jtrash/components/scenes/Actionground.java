package jtrash.components.scenes;

import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jtrash.components.factories.BackgroundFactory;
import jtrash.components.factories.BoxFactory;
import jtrash.components.factories.ButtonFactory;
import jtrash.components.factories.GridPaneFactory;
import jtrash.components.factories.TextFactory;
import jtrash.components.handlers.GameHandler;
import jtrash.components.objects.box.CartaSelezionataBox;
import jtrash.components.objects.box.CarteMazzoBox;
import jtrash.components.objects.box.CarteScartateBox;
import jtrash.enums.FOLDERS_ENUM;
import jtrash.enums.IMAGES_ENUM;

public class Actionground {
	
	private static Actionground instance;
	private static GameHandler gameHandler;
	
	public static Actionground getInstance() {
		if (instance == null) {
			instance = new Actionground();
		}
		return instance;
	}
	
	private static GridPane actionground;
	

	private static Text testoCartaSelezionata;
//	private static Button tastoGiraCarta;
	
	private static Text testoCarteScartare;
	private static Text testoCarteMazzo;
	
	private static Button pescaCarta;
	private static Button posizionaCarta;
	private static Button scartaCarta;
	private static Button posizionaCartaJolly;
	
	private static VBox azioniActionGround;
	
	private Actionground() {
		GameHandler gameHandler = GameHandler.getInstance();
		
		actionground = GridPaneFactory.generaGridPane(BackgroundFactory.generaBackground(
				FOLDERS_ENUM.IMMAGINI.getFolderLocation() + IMAGES_ENUM.SFONDO_PRINCIPALE.getNomeImmagine()));
		

		testoCartaSelezionata = TextFactory.generaTesto("Carta selezionata", Color.WHITE, null, 0);
//		tastoGiraCarta = ButtonFactory.generaTasto("Gira carta",gameHandler.getGiraCartaSelezionataEventHandler());
		
		testoCarteScartare = TextFactory.generaTesto("Carte scartate", Color.WHITE, null, 0);
		testoCarteMazzo = TextFactory.generaTesto("Mazzo", Color.WHITE, null, 0);
		
		pescaCarta = ButtonFactory.generaTasto("Pesca carta",gameHandler.getPescaCartaEventHandler());
		posizionaCarta = ButtonFactory.generaTasto("Posiziona carta",gameHandler.getGiraCartaSelezionataEventHandler());
		scartaCarta = ButtonFactory.generaTasto("Scarta carta",gameHandler.getScartaCartaPescataEventHandler());
		posizionaCartaJolly = ButtonFactory.generaTasto("Posiziona Jolly carta",gameHandler.getGiraCartaSelezionataEventHandler());
		
		azioniActionGround = BoxFactory
				.generaBoxVerticaleNodi(
						Arrays.asList(CartaSelezionataBox.getInstance().getBox(),
								testoCartaSelezionata,
//								tastoGiraCarta,
								CarteScartateBox.getInstance().getBox(),
								testoCarteScartare,
								CarteMazzoBox.getInstance().getBox(),
								testoCarteMazzo,
								pescaCarta,
								posizionaCarta,
								scartaCarta,
								posizionaCartaJolly));
		
		actionground.add(azioniActionGround,0,1);
	}
	
	public GridPane getActionground() {
		return actionground;
	}
	
	public void setEnablePescaCarta(boolean canPescareCarta) {
		pescaCarta.setDisable(!canPescareCarta);
	}
	public void setEnableScartaCarta(boolean canScartareCarta) {
		scartaCarta.setDisable(!canScartareCarta);
	}

}
