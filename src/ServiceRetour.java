

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import mediatheque.Mediatheque;
import mediatheque.objects.RestrictionException;

public class ServiceRetour implements Runnable{
	private static int num;
	private Mediatheque mediatheque;
	private Socket socket;
	
	
	public ServiceRetour(Mediatheque mediatheque, Socket socket) {
		ServiceRetour.num++;
		this.mediatheque = mediatheque;
		this.socket = socket;
	}


	@Override
	public void run() {
		try {
			System.out.println("Connexion " + num + " demarree");
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			socketOut.println("Quel est le numéro de document ?");
			int numDoc = Integer.parseInt(socketIn.readLine());
			
			try {
				mediatheque.retour(numDoc);
			} catch (RestrictionException e) {
				socketOut.println(e);
			}
			
			socketOut.println("Retour reussie du document " + numDoc);
			
			this.socket.close();
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
