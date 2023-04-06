package database;


import mediatheque.Repository;

public class FabriqueRepos {
	
	
	public static Repository  creerRepository(String user, String password, String typeDeLObjet) throws Exception {
			try {
				Class<?> classe = Class.forName(typeDeLObjet);
				// tester que cette clase implémente Repository
				return (Repository) classe.getConstructor(String.class, String.class).newInstance(user, password);
			} catch (Exception e) {
				System.out.println(typeDeLObjet+" non géré par la fabrique" );
				e.printStackTrace();
			}
			return null;
	} // fin creer

}
