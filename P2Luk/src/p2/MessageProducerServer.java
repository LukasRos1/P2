package p2;

import java.io.*;
import java.net.*;

import p1.ArrayProducer;
import p1.MessageProducerInput;
import p1.TextfileProducer;

/**
 * 
 * addMessageProducer / MessageProducerInput
 * @author Lukas Rosberg
 * Datum: 14/3-2019
 */
public class MessageProducerServer extends Thread {
	private MessageProducerInput mpInput;
	private int port;
	
	/**
	 * @param mpInput tar emot en MessageProducerInput
	 * @param port tar emot en port
	 */
	
	public MessageProducerServer(MessageProducerInput mpInput, int port) {
		this.mpInput = mpInput;
		this.port = port;
	}
	
	/**
	 * startar server
	 */
	public void startServer() {
		start();
	}
	
	/**
	 * läser in objekt från socket och sorterar de
	 * skickar sedan vidare de till klienter
	 * 
	 */
	public void run() {
		Object obj;
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				try (Socket socket = serverSocket.accept();
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
					obj = ois.readObject();
					if (obj instanceof ArrayProducer) {
						mpInput.addMessageProducer(((ArrayProducer) obj));
					}
					if (obj instanceof ShowGubbe) {
						mpInput.addMessageProducer(((ShowGubbe) obj));
					}
					if (obj instanceof TextfileProducer) {
						mpInput.addMessageProducer(((TextfileProducer) obj));
					}
					oos.writeObject(obj);
					oos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}