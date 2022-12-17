package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IAuthServer {

	public void response(DataInputStream _in, DataOutputStream _out);
	
}
