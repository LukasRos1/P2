package p1;

public class Producer {

	private Buffer<MessageProducer> prodBuffer;
	private Buffer<Message> messageBuffer;
	private Thread thread;

	public Producer(Buffer<MessageProducer> prodBuffer, Buffer<Message> messageBuffer) {

		this.prodBuffer = prodBuffer;
		this.messageBuffer = messageBuffer;
	}

	public void start() throws InterruptedException {
		if(thread==null) {
			thread = new Worker();
			thread.start();
		}
	}
	
	private class Worker extends Thread {
		public void run() {
			while(!Thread.interrupted()) {
				 try {
	                    MessageProducer mp;
	                    Message message;
	                    mp = prodBuffer.get();
	                    for(int times = 0; times < mp.times(); times++) {
	                        for (int i = 0; i < mp.size(); i++) {
	                            message = mp.nextMessage();
	                            messageBuffer.put(message);
	                            Thread.sleep(mp.delay());
	                        }
	                    }
				} catch (InterruptedException e) {
					System.out.println("lol");
					break;
				}
			}
		}
	}
}
