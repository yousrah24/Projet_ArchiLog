package bserveur;
import java.net.Socket;

import mediatheque.Mediatheque;


public abstract class  Service implements Runnable{
	protected Socket socket;
	protected static int num;
	protected static Mediatheque mediatheque;
	
	public Service(Socket socket) {
		Service.num++;
		this.socket = socket;
	}
	
	public static void laMediatheque(Mediatheque mediatheque) {
		Service.mediatheque = mediatheque;
	}
	
	@Override
	public void run() {}

	
	protected void finalize() throws Throwable {
		 socket.close(); 
	}
	
	public void lancer() {
		new Thread(this).start();	
	}
	

}
