package server.model;

public class ModelF {

	public static IGraphServer createGraphModel() {
		return new GraphServer();
	}
	
	public static IAuthServer createAuthModel() {
		return new AuthServer();
	}
	
}
