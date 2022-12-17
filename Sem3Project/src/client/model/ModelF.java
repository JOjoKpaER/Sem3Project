package client.model;

public class ModelF {

	public static IModel createSuperModel() {
		return new Model();
	}
	
	public static IGraph createGraphModel() {
		return new Graph();
	}
	
	public static IAuth createAuthModel() {
		return new Auth();
	}
	
}
