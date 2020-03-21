package p2;

import java.io.*;
import java.net.*;
import java.util.*;

import p1.*;

/**
 * Klass som skapar server som tar in meddelande
 * från lyssnare och skickar dessa till klienter
 * 
 * @author Lukas Rosberg
 * Datum: 14/03-2019
 */
public class MessageServer implements MessageListener, Runnable {
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private LinkedList<Message> list = new LinkedList<Message>();
	

	/**
	 * Skapar MessageServer objekt och lägger till lyssnare
	 * 
	 * @param messageManager tar in MessageManager objekt
	 * @param port tar in port-nummer till server
	 * @throws IOException Exception thrown if failed or interrupted I/O operations has occurred.
	 */
	public MessageServer(MessageManager messageManager, int port) throws IOException {
		serverSocket = new ServerSocket(port);
		server.start();
		messageManager.addMessageListener(this);
	}
	
	/**
	 *  tar emot meddelande och lägger till i lista
	 */
	public void receive(Message message) {
		list.add(message);
	}
	
	/**
	 * försöker ansluta socket
	 * sedan startas en tråd
	 */
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new Worker(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Worker extends Thread {
		private Socket socket;
		
		/**
		 * @param socket tar in klientens socket
		 */
		public Worker(Socket socket) {
			this.socket = socket;
		}

		/**
		 * skriver meddelande till lyssnare
		 */
		public void run() {
			int i = 0;
			try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
				while (!Thread.interrupted()) {
					if (i < list.size()) {
						oos.writeObject(list.get(i++));
						oos.flush();
					}
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}