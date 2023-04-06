package mediatheque;

import java.io.BufferedReader;



import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bserveur.Codage;
import bserveur.Service;
import document.RestrictionException;

public class ServiceReservation extends Service{
	private int numDoc;
	private long tempsDeService = 2L * 60L * 60L * 1000L;
	private Timer timer;
	
	public ServiceReservation(Socket socket) {
		super(socket);
		timer = new Timer();
	}
	
	private class FinReservation extends TimerTask{
		private Thread thread;
		
		public FinReservation(Thread currentThread) {
			thread = currentThread;
		}
		@Override
		public void run() {
			try {
				mediatheque.retour(numDoc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			thread.interrupt();
		}
		
	}
	
	

	@Override
	public void run() {
		
		System.out.println("Connexion " + num + " demarree");
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			@SuppressWarnings("unchecked")
			List<IDocument> docs = (List<IDocument>) Mediatheque.getListeDocument();
	        
			if (docs.size() == 0)
				socketOut.println(Codage.coder("Tous les livres sont empruntés :("));
			else {
				socketOut.println(Codage.coder("*".repeat(15)+ " Catalogue " + "*".repeat(35)+"\n"));
				for (IDocument doc : docs) {
					socketOut.println(Codage.coder(doc.toString() + "\n"));
				}
			}
				
			socketOut.println(Codage.coder("*".repeat(61)+"\n"));
			socketOut.println(Codage.coder("Quel est le numéro d'abonné ?"));
			
			int numAb = Integer.parseInt(Codage.decoder(socketIn.readLine()));
			
			System.out.println("Connexion " + num + " a reçu "+ numAb + " comme numéro d'abonné");
			
			socketOut.println(Codage.coder("Quel est le numéro de document ?"));
			numDoc = Integer.parseInt(Codage.decoder(socketIn.readLine()));
			
			System.out.println("Connexion " + num + " a reçu "+ numDoc + " comme numéro de document");

			try {
				synchronized (mediatheque) {
					mediatheque.reservation(numAb, numDoc);
				}
				socketOut.println(Codage.coder("Vous devez aller le prendre à la médiathque avant ce document avant " + 
				LocalDateTime.now().plusHours(2).getHour() +"h "
						+LocalDateTime.now().plusHours(2).getMinute() ));
			} catch (RestrictionException e) {
				socketOut.println(Codage.coder(e.toString()));
			}
			socketOut.close();
			socketIn.close();
			
			try {
				Thread.sleep(tempsDeService);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.timer.schedule(new FinReservation(Thread.currentThread()), tempsDeService);
			timer.cancel();
			
			System.out.println("Connexion " + num + " terminee");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
