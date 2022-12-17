package client.model;

public interface IAuth extends IModel {
	
	public void register(String _name, String _password, AuthUpdatable _updatable);
	public void login(String _name, String _password, AuthUpdatable _updatable);

}
