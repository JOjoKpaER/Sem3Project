package server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IGraphServer {

	void response(ObjectInputStream _in, ObjectOutputStream _out);

}
