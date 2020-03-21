package p2;

import java.io.*;
import java.net.*;
import p1.ArrayProducer;
import p1.Message;
import p1.TextfileProducer;

/**
 * 
 * @author Lukas Rosberg
 * Datum: 14/03-2019
 */

public class MessageProducerClient {
	private String ip;
	private int port;
	private Thread thread;
	
	public MessageProducerClient(String ip, int port) throws UnknownHostException, IOException, ClassNotFoundException {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * @param textfileProducer tar emot ett textfileProducer objekt och 
	 * startar en tråd med detta
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void send(TextfileProducer textfileProducer) throws IOException, ClassNotFoundException {
		Object obj = textfileProducer;
		try (Socket socket = new Socket(ip, port);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) 
		{
			oos.writeObject(obj);
			oos.flush();
			ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void send(ArrayProducer arrayProducer) throws IOException, ClassNotFoundException {
		Object obj = arrayProducer;
		try (Socket socket = new Socket(ip, port);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) 
		{
			oos.writeObject(obj);
			oos.flush();
			ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public void send(ShowGubbe showGubbe) {
		Object obj = showGubbe;
		try (Socket socket = new Socket(ip, port);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) 
		{
			oos.writeObject(obj);
			oos.flush();
			ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

	
	/**
	 * 
	 * @author Lukas Rosberg
	 * klass för att starta och hantera tråd
	 * för att prata med server
	 */
//	private class Connection extends Thread {
//		private Object obj;
//
//		public Connection(Object obj) {
//			this.obj = obj;
//		}

//		public void run() {
//			try (Socket socket = new Socket(ip, port);
//					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) 
//			{
//				oos.writeObject(obj);
//				oos.flush();
//				ois.readObject();
//			} catch (IOException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
