package utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AuthData {

	public String name;
	public String password;
	public AuthDataEnum param;
	
	public AuthData(String _name, String _password, AuthDataEnum _param) {
		name = _name;		password = _password;	param = _param;
	}
	//copy
	public AuthData(AuthData _AD) {
		this.name = _AD.name; this.password = _AD.password; this.param = _AD.param;
	}
	//create from DataInputStream
	public AuthData(DataInputStream _dis) {
		this.readData(_dis);
	}
	
	public void writeData(DataOutputStream _dos) {
		try {
			_dos.writeUTF(name);
			_dos.writeUTF(password);
			_dos.writeInt(param.getValue());
			_dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readData(DataInputStream _dis) {
		try {
			name = _dis.readUTF();
			password = _dis.readUTF();
			param = AuthDataEnum.values()[_dis.readInt()];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
