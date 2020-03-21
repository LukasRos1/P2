package p2;

import java.net.Socket;
import java.io.*;
import java.util.LinkedList;
import p1.Message;
import p1.MessageListener;

/**
 * Skapar en klient som kan ansluta till server
 * och ta emot meddelanden
 * @author Lukas Rosberg
 * Datum: 14/3-2019
 */
public class MessageClient {
	private Connection connection;
	private Socket socket;
	private String ip;
	private int port;
	private LinkedList<MessageListener> list = new LinkedList<MessageListener>();


	/**
	 * skapar ett MessageClient-objekt
	 * 
	 * @param ip �r serverns IP
	 * @param port �r serverns port
	 * @throws IOException 
	 */
	public MessageClient(String ip, int port) throws IOException {
		this.ip = ip;
		this.port = port;
		connect();
	}
	
	/**
	 * l�gger till en lyssnare i lista
	 * @param p2Viewer
	 */
	public void addMessageListener(P2Viewer p2Viewer) {
		list.add(p2Viewer);
	}

	/**
	 * tar emot meddelande fr�n socket
	 * och skickar vidare till lyssnare
	 * @param message meddelandet fr�n socket
	 */
	public void sendMessage(Message message) {
			for (MessageListener l : list) {
				l.receive(message);
			}
		}
	
	/**
	 * Ansluter till server
	 * @throws IOException 
	 */
	public void connect() throws IOException {
		if(connection == null) {
			try {
				socket = new Socket(ip, port);
				connection = new Connection(socket);
				connection.start();
			} catch(IOException e) {
				e.printStackTrace();
				socket.close();
			}
		}
	}
	
	/**
	 * S�tter upp en connection
	 * @author Lukas Rosberg
	 *
	 */
	private class Connection extends Thread {
		private ObjectInputStream ois;

		/**
		 * Skapar ett connection-objekt
		 * 
		 * @param socket f�r connection
		 * @throws IOException 
		 */
		public Connection(Socket socket) throws IOException {
			ois = new ObjectInputStream(socket.getInputStream());
		}
		
		/**
		 * l�ser meddelanden fr�n socket
		 * och skickar till lyssnare
		 */

		public void run() {
			Object obj;
			try {
				while(!Thread.interrupted()) {
					obj = ois.readObject();
					sendMessage((Message)obj);
				}
			} catch(IOException | ClassNotFoundException e) {
				System.out.println(e.toString());
			}
		}
	}
}

