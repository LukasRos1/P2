package p1;

public class P1Viewer extends Viewer implements MessageListener {

	private MessageManager messageManager;
	private int width;
	private int height;
	
	public P1Viewer(MessageManager messageManager, int width, int height) {
		super(width, height);
		this.messageManager = messageManager;
		messageManager.addMessageListener(this);
	}
	
	public void receive(Message message) {
		setMessage(message);
	}
}
