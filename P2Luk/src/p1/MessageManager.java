package p1;

import java.util.LinkedList;


public class MessageManager {
	
	private Thread thread;

	private Buffer<Message> messageBuffer;
	private LinkedList<MessageListener> list = new LinkedList<>();

	public MessageManager(Buffer<Message> messageBuffer) {
		this.messageBuffer = messageBuffer;
	}
	
	public void addMessageListener(MessageListener listener) {
		if (listener != null) {
			list.add(listener);
		}
	}

	public void start() {
		if(thread==null) {
			thread = new Worker1();
			thread.start();
		}
	}
	
	private class Worker1 extends Thread {
		public void run() {
			while(!Thread.interrupted()) {
				 try {
					 Message message = messageBuffer.get();
					 for(MessageListener l : list) { 
						 l.receive(message);
					 }

   
	                    } catch (InterruptedException e) {
					System.out.println("exception");
					break;
			}
		}
	}
		
}

}