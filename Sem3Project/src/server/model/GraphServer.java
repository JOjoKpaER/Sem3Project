package server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utility.NearestNeighbour_method;
import utility.VectorData;

public class GraphServer implements IGraphServer {

	@Override
	public void response(ObjectInputStream _in, ObjectOutputStream _out) {
		VectorData VD = new VectorData(_in);
		NearestNeighbour_method.path(VD.getList()).writeData(_out); 
	}
	
}
