package document;


public enum Etat {
	Disponible(1), Emprunté(0), Reservé(-1);
	private int etat;
	
	private Etat(int i) {
		this.etat = i;
	}

	public static Etat get(int etat) {
		for (Etat e : Etat.values())
			if (e.etat == etat)
				return e;
		throw new IllegalArgumentException(
				"symbole inconnu " + etat);
	}
}
