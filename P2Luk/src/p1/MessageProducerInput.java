package p1;

public class MessageProducerInput {
	
	private Buffer<MessageProducer> buffer;
	
	public MessageProducerInput(Buffer<MessageProducer> buffer) {
		this.buffer = buffer;
	}
	
	public void addMessageProducer (MessageProducer m) {
		buffer.put(m);
	}

}
