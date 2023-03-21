package appli.serveur;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import mediatheque.Mediatheque;
import mediatheque.document.RestrictionException;



public class Serveur implements Runnable{
	private ServerSocket listen_socket;
	private Class<? extends Service> serviceClass;
	public Serveur(Class<? extends Service> laClasse, int port) throws IOException {
		listen_socket = new ServerSocket(port); 
		serviceClass = laClasse;
	}

	@Override
	public void run(){
		try {
			System.out.println("Serveur lance sur le port "+ listen_socket.getLocalPort());
			while(true) {
				Socket socket = listen_socket.accept();
				Service service = this.serviceClass.getConstructor(Socket.class).newInstance(socket);
				service.lancer();
			}
		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				System.err.println("Pb sur le port d'ecoute :\n"+e);
		} 
	}
	
	public void lancer() {
		new Thread(this).start();	
	}
	
	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
			try {this.listen_socket.close();} catch (IOException e1) {}
	}

}
