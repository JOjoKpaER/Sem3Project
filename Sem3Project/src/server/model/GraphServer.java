package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import utility.NearestNeighbour_method;
import utility.VectorData;

public class GraphServer implements IGraphServer {

	@Override
	public void response(DataInputStream _in, DataOutputStream _out) {
		VectorData VD = new VectorData(_in);
		NearestNeighbour_method.path(VD.getList()).writeData(_out); 
	}
	
}
