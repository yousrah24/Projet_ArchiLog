package mediatheque.objects;


public enum Etat {
	Disponible("disponible"), Emprunté("emprunte"), Reservé("reserve");
	private String etat;
	
	private Etat(String etat) {
		this.etat = etat;
	}

	public static Etat get(String etat) {
		for (Etat e : Etat.values())
			if (e.etat == etat)
				return e;
		throw new IllegalArgumentException(
				"symbole inconnu " + etat);
	}
}
