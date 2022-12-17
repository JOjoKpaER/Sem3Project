package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import db.User;
import utility.AuthData;
import utility.AuthDataEnum;

public class AuthServer implements IAuthServer {
	
	@Override
	public void response(DataInputStream _in, DataOutputStream _out) {
		AuthData AD = new AuthData(_in);
		switch (AD.param) {
		case login: {
			User user = new User(AD.name, AD.password);
			if (user.Check()) {
				AuthData answer = new AuthData(AD);
				answer.param = AuthDataEnum.allow;
				answer.writeData(_out);
			}else {
				AuthData answer = new AuthData(AD);
				answer.param = AuthDataEnum.deny;
				answer.writeData(_out);
			}
		}
		case registration: {
			User user = new User(AD.name, AD.password);
			if (!user.Check()) {
				try {
					user.AddUser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AuthData answer = new AuthData(AD);
				answer.param = AuthDataEnum.allow;
				answer.writeData(_out);
			}else {
				AuthData answer = new AuthData(AD);
				answer.param = AuthDataEnum.deny;
				answer.writeData(_out);
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + AD.param);
		}
	}

}
