package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer{
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		ExecutorService threadPool = Executors.newFixedThreadPool(25);

		while (true) {
			Socket socket = serverSocket.accept();
			HandleServerThreads threads = new HandleServerThreads(socket);
			threadPool.execute(threads);
		}
	}
}

class HandleServerThreads implements Runnable{
	Socket s;

	HandleServerThreads(Socket s){
		this.s = s;
	}

	public void run() {
		while (true) {
			try {
				InputStream i = s.getInputStream();
				OutputStream o = s.getOutputStream();
				int byteRead;
				while ((byteRead = i.read()) != -1) {
					o.write(byteRead);
				}
				s.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


