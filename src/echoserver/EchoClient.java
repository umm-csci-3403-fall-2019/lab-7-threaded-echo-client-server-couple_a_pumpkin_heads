package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;

public class EchoClient implements Runnable{
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
		int byteTyped;

		while((byteTyped = System.in.read()) != -1){
			socketOutputStream.write(byteTyped);
			System.out.write(socketInputStream.read());
		}
	}

	@Override
	public void run() {
		System.out.println("Running");
	}
}

class KeyboardThreadFactory implements ThreadFactory {
	public Thread newThread(Runnable r) {
		return new Thread(r);
	}
}

class outputThreadFactory implements ThreadFactory {
	public Thread newThread(Runnable r) {
		return new Thread(r);
	}
}