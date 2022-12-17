package client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.AuthData;
import utility.AuthDataEnum;

public class Auth extends Model implements IAuth {

	private String serverAddress = "127.0.0.1";
	private int port = 8801;
	
	@Override
	public void register(String _name, String _password, AuthUpdatable _updatable) {
		
		ServerRequestAuth SR = new ServerRequestAuth(serverAddress, port, _updatable);
		SR.requestRegister(_name, _password);
	}

	@Override
	public void login(String _name, String _password, AuthUpdatable _updatable) {

		ServerRequestAuth SR = new ServerRequestAuth(serverAddress, port, _updatable);
		SR.requestLogin(_name,_password);
	}

}

class ServerRequestAuth extends Thread{
	
	private Socket client;
	private AuthData Answer;
	private AuthUpdatable updatable;
	
	public ServerRequestAuth(String _server, int _port, AuthUpdatable _updatable) {
		try {
			client = new Socket(_server, _port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatable = _updatable;
	}
	
	public void requestRegister(String _name, String _password) {
		try {
			new AuthData(_name, _password, AuthDataEnum.registration).writeData(new DataOutputStream(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void requestLogin(String _name, String _password) {
		try {
			new AuthData(_name, _password, AuthDataEnum.login).writeData(new DataOutputStream(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try {
			Answer.readData(new DataInputStream(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (Answer) {
			updatable.update(Answer);
		}
	}
	
}