package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.model.ModelF;

public class GraphController {

	public GraphController() {
		new GraphServer().run();
	}
	
}

class GraphServer extends Thread{
	
	private ServerSocket serverSokcet;
	private int port = 8800;
	
	public GraphServer() {
		try {
			serverSokcet = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(true) {
			try {
				Socket server = serverSokcet.accept();
				ModelF.createGraphModel().response(new ObjectInputStream(server.getInputStream()),
									new ObjectOutputStream(server.getOutputStream()));
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}