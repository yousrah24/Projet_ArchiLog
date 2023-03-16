

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import mediatheque.Mediatheque;
import mediatheque.objects.RestrictionException;

public class ServiceEmprunt implements Runnable{
	private Socket socket;
	private static int num;
	private Mediatheque mediatheque;
	
	
	public ServiceEmprunt(Mediatheque mediatheque, Socket socket) {
		ServiceEmprunt.num++;
		this.mediatheque = mediatheque;
		this.socket = socket;
	}


	@Override
	public void run() {
		try {
			System.out.println("Connexion " + num + " demarree");
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
						
			socketOut.println("Quel est le numéro de l'abonné ?");
			int numAb = Integer.parseInt(socketIn.readLine());
			
			socketOut.println("Quel est le numéro de document ?");
			int numDoc = Integer.parseInt(socketIn.readLine());
			
			try {
				synchronized (mediatheque) {
					mediatheque.emprunt(numAb, numDoc);
				}
			} catch (RestrictionException e) {
				socketOut.println(e);
			}
			
			socketOut.println("Emprunt reussie du document " + numDoc + "par l'abonné " + numAb);
			
			socket.close();
			System.out.println("Connexion " + num + " terminee");
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	
	}
	
	protected void finalize(){
		 try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void lancer() {
		new Thread(this).start();	
	}

	

}
