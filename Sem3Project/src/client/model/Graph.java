package client.model;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.VectorData;

public class Graph extends Model implements IGraph  {

	private VectorData VD;
	
	private String serverAddress = "127.0.0.1";
	private int port = 8800;
	
	public Graph() {
		VD = new VectorData();
	}
	
	@Override
	public void addNode(double _x, double _y) {
		VD.add(_x, _y);
	}

	@Override
	public void clearNodes() {
		VD = new VectorData();
	}

	@Override
	public void requestSolve(GraphUpdatable updatable) {
		if (VD == null) return;
		ServerRequest SR = new ServerRequest(serverAddress, port, updatable);
		SR.sendData(VD);		
	}

	@Override
	public void saveSolve(File _dir) {
		// TODO Auto-generated method stub
		
	}

}

class ServerRequest extends Thread {
	
	private Socket client;
	private VectorData Answer = new VectorData();
	private VectorData send;
	private GraphUpdatable updatable;
	
	public ServerRequest(String _server, int _port, GraphUpdatable _updatable) {
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
	
	public void sendData(VectorData _VD) {
		send = _VD;
		this.run();
	}
	
	public VectorData getData() {
		synchronized (Answer) {
			return Answer;
		}
	}
	
	@Override
	public void run(){
		try {
			send.writeData(new ObjectOutputStream(client.getOutputStream()));
			Answer.readData(new ObjectInputStream(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (Answer) {
			updatable.update(Answer);
		}
	}
	
}