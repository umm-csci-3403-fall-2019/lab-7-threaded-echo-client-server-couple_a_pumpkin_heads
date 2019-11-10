package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		while(true) {
			Socket socket = new Socket("localhost", PORT_NUMBER);
			ExecutorService threadPool = Executors.newFixedThreadPool(25);

			HandleKeyThread KeyboardReader= new HandleKeyThread(socket);
			threadPool.execute(KeyboardReader);

			HandleOutputThread ServerReader= new HandleOutputThread(socket);
			threadPool.execute(ServerReader);


		}
	}
}

class HandleKeyThread implements Runnable {
	Socket s;

	public HandleKeyThread(Socket s){
		this.s = s;
	}

	@Override
	public void run() {
		try {
			OutputStream o = s.getOutputStream();
			int byteTyped;
			if ((byteTyped = System.in.read()) != -1) {
				o.write(byteTyped);
			}
			s.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class HandleOutputThread implements Runnable {
	Socket s;

	public HandleOutputThread(Socket s){
		this.s = s;
	}

	@Override
	public void run() {
		try {
			InputStream i = s.getInputStream();
			if (i.read() != -1) {
				System.out.write(i.read());
			}
			System.out.flush();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}