package client.model;

import java.io.File;

public interface IGraph extends IModel {

	public void addNode(double _x, double _y);
	public void clearNodes();
	public void requestSolve(GraphUpdatable update);
	public void saveSolve(File _dir);
}
