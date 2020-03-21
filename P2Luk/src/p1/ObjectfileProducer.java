package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;

public class ObjectfileProducer implements MessageProducer {
	private Message[] messages;
	private int delay = 0;
	private int times = 0;
	private int currentIndex = -1;

	private String filename;

	public ObjectfileProducer(String filename) throws ClassNotFoundException, IOException {
		this.filename = filename;
		test();
	}

	public int delay() {
		return delay;
	}

	public int times() {
		return times;
	}

	public int size() {
		return (messages==null) ? 0 : messages.length;
	}


	public void test () throws IOException, ClassNotFoundException {
		int i = 0;
		try {
			FileInputStream fileIn = new FileInputStream(this.filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			this.times = in.readInt();
			this.delay = in.readInt();
			this.messages = new Message[in.readInt()];
			
			for (i = 0; i < messages.length; i++) {
			messages[i] = (Message) in.readObject();
			}
			
			in.close();
			fileIn.close();
		} catch (IOException i1) {
			i1.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("förhoppningsvis händer inte detta!");
			c.printStackTrace();
			return;
		}
	}
	
	public Message nextMessage() {
		if(size()==0)
		    return null;
		currentIndex = (currentIndex+1) % messages.length;
		return messages[currentIndex];
	}

}
