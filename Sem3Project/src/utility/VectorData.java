package utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VectorData {
	
	private List<DoubleVector> vectors;
	
	public VectorData() {
		vectors = new ArrayList<DoubleVector>();
	}
	//create a copy
	public VectorData(VectorData _VD) {
		vectors = _VD.vectors;
	}
	//create from DataInputStream
	public VectorData(ObjectInputStream _ois) {
		vectors = new ArrayList<DoubleVector>();
		this.readData(_ois);
	}
	
	public List<Double> getList(){
		List<Double> ret = new ArrayList<Double>();
		vectors.forEach(DV -> {
			ret.add(DV.x);
			ret.add(DV.y);
		});
		return ret;
	}
	
	public void add(double _x, double _y) {
		add(new DoubleVector(_x, _y));
	}
	
	public void add(DoubleVector _DV) {
		vectors.add(_DV);
	}
	
	public void add(ArrayList<DoubleVector> _DV) {
		vectors.addAll(_DV);
	}

	public void writeData(ObjectOutputStream _oos) {
		try {
			_oos.writeObject(vectors);
			_oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readData(ObjectInputStream _ois) {
		try {
			vectors = (List<DoubleVector>) _ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class DoubleVector implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7870145026521589831L;
	public double x,y;
	
	public DoubleVector(double _x, double _y) {x = _x; y = _y;}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
		out.writeDouble(x);
		out.writeDouble(y);
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		x = in.readDouble();
		y = in.readDouble();
	}
	
}
