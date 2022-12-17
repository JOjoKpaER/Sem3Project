package utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	public VectorData(DataInputStream _dis) {
		this.readData(_dis);
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

	public void writeData(DataOutputStream _dos) {
		vectors.forEach((DV) -> {
			try {
				_dos.writeDouble(DV.x);
				_dos.writeDouble(DV.y);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void readData(DataInputStream _dis) {
		while (true)
		try {
		vectors.add(new DoubleVector(
				_dis.readDouble(),
				_dis.readDouble()
				));
		} catch (IOException e) {
			break;
		}
	}
}

class DoubleVector{
	public double x,y;
	
	public DoubleVector(double _x, double _y) {x = _x; y = _y;}
}
