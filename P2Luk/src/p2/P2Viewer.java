package p2;

import p1.*;

public class P2Viewer extends Viewer implements MessageListener {
	
	public P2Viewer (MessageClient messageClient, int height, int width) {
		super(height, width);
		messageClient.addMessageListener(this);
	}
	
	public void receive(Message message) {
		setMessage(message);
	}
}



