package p1;

import java.util.List;

import javax.swing.ImageIcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class TextfileProducer implements MessageProducer, Serializable {
	private Message[] messages;
	
	private String filename;
	private int delay = 0;
	private int times = 0;
	private int currentIndex = -1;

	public TextfileProducer(String filename) throws IOException {
		this.filename = filename;
		test();
	}

	@Override
	public int delay() {
		return delay;
	}

	@Override
	public int times() {
		return times;
	}

	@Override
	public int size() {
		return (messages==null) ? 0 : messages.length;
	}


	public void test () throws IOException   {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader in = new BufferedReader(isr);
   	 	int i = 0;
		
		 String line1;
		 this.times = Integer.parseInt(in.readLine());
		 this.delay = Integer.parseInt(in.readLine());
		 this.messages = new Message[Integer.parseInt(in.readLine())];
		 
         while ((line1 = in.readLine()) != null) {
			this.messages[i] = new Message(line1, new ImageIcon(in.readLine()));
        	 i++;
         }
	}
	@Override
	public Message nextMessage() {
		if(size()==0)
		    return null;
		currentIndex = (currentIndex+1) % messages.length;
		return messages[currentIndex];
	}

	public static void main(String[] args) throws IOException {
		TextfileProducer prog = new TextfileProducer("files/new.txt");
	}
}
