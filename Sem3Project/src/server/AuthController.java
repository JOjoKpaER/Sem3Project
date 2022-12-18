package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.model.ModelF;

public class AuthController {
	
	public AuthController() {
		new AuthServer().run();
	}

}

class AuthServer extends Thread{
	
	private ServerSocket serverSokcet;
	private int port = 8801;
		
	public AuthServer() {
		try {
			serverSokcet = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Socket server = serverSokcet.accept();
				ModelF.createAuthModel().response(new DataInputStream(server.getInputStream()),
								   new DataOutputStream(server.getOutputStream()));
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}