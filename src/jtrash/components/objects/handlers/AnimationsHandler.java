package jtrash.components.objects.handlers;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationsHandler {

	public static void animazioneIngrandimento(Node nodo) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), nodo);

        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);

        scaleTransition.setAutoReverse(true);

        scaleTransition.setCycleCount(2);

        scaleTransition.play();
	}
}
