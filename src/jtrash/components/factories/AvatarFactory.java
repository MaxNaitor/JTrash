package jtrash.components.factories;

import java.util.Arrays;
import java.util.List;

import javafx.scene.shape.Rectangle;
import jtrash.enums.AVATAR_ENUM;
import jtrash.enums.FOLDERS_ENUM;

/**
 * Factory per la creazione di Rectangle relativi agli avatar utente
 * @author tizia
 *
 */
public class AvatarFactory {

	/**
	 * Restituisce l'avatar richiesto in input.
	 * @param avatar
	 * @return un Rectangle con l'immagine dell'avatar scelto
	 */
	public static Rectangle getAvatar(AVATAR_ENUM avatar) {
		Rectangle avatarShape = new Rectangle(50, 50);

		avatarShape.setFill(ImagePatternFactory
				.generaImmagine(FOLDERS_ENUM.IMMAGINI_AVATAR.getFolderLocation() + avatar.getNomeImmagine()));

		return avatarShape;
	}

	/**
	 * Restituisce la lista di tutti gli avatar che l'utente può scegliere in fase di registrazione.
	 * @return lista di Rectangle con le immagini degli avatar utente
	 */
	public static List<Rectangle> getAvatarSelezionabiliGiocatore() {
		return Arrays.asList(getAvatar(AVATAR_ENUM.AVATAR_BLU), getAvatar(AVATAR_ENUM.AVATAR_GIALLO),
				getAvatar(AVATAR_ENUM.AVATAR_ROSSO), getAvatar(AVATAR_ENUM.AVATAR_VIOLA));
	}
}
