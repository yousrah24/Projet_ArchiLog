import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mediatheque.Mediatheque;

public class ServiceReservation implements Runnable{
	
	private static int num;
	private Mediatheque mediatheque;
	private Socket socket;
	
	
	public ServiceReservation(Mediatheque mediatheque, Socket socket) {
		ServiceReservation.num++;
		this.mediatheque = mediatheque;
		this.socket = socket;
	}


	@Override
	public void run() {
		
		System.out.println("Connexion " + num + " demarree");
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			String response;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void finalize() throws Throwable {
		 socket.close(); 
	}
	
	public void lancer() {
		new Thread(this).start();	
	}
}
