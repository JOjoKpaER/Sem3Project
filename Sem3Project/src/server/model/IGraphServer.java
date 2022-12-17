package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IGraphServer {

	void response(DataInputStream _in, DataOutputStream _out);

}
