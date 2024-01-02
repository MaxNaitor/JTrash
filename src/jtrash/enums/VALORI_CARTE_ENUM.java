package jtrash.enums;

public enum VALORI_CARTE_ENUM {
	ASSO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, OTTO, NOVE, DIECI, JACK, REGINA, RE, JOLLY;

	public static int getValoreNumerico(VALORI_CARTE_ENUM valoreEnum) {
		switch (valoreEnum) {
		case ASSO:
			return 1;
		case DUE:
			return 2;
		case TRE:
			return 3;
		case QUATTRO:
			return 4;
		case CINQUE:
			return 5;
		case SEI:
			return 6;
		case SETTE:
			return 7;
		case OTTO:
			return 8;
		case NOVE:
			return 9;
		case DIECI:
		case JACK:
		case REGINA:
		case RE:
		case JOLLY:
			return 10;
		default:
			return 0;
		}
	}
}
