package utility;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class NearestNeighbour_method {
	
	
	public static VectorData path(List<Double> _list)
	{
		ListIterator<Double> i = _list.listIterator();
		if (i == null || !i.hasNext())  return null;
		List<Vectors> VectorsList = new ArrayList<Vectors>();
		while (i.hasNext()) {
			try {
				VectorsList.add(new Vectors(i.next(), i.next()));
			}catch (NoSuchElementException e) {
				break;
			}
		}
		Vectors[] array = VectorsList.toArray(new Vectors[0]);
		int[] path = FindPath(array, array.length, 0);
		VectorData answer = new VectorData();
		for (int j = 0; j < path.length; j++) {
			answer.add(_list.get(path[j]), _list.get(path[j] + 1));
		}
		return answer;
	}
	
	public static int[] FindPath (Vectors[] array, int n, int start)
	{
		array[start].isCovered = true;
		ArrayList<Integer> path = new ArrayList<Integer>(n);
		int i = 0;
		path.add(start);
			while (true)
			{
				if (i >= n) i = 0;
				double min = Double.MAX_VALUE;
				int mini = 0;
				for (int j = 0; j < n; j++)
				{
					if (j == i) continue;
					double dist = Distance(array[i], array[j]);
					if (dist < min && !array[j].isCovered)
					{
						min = dist;
						mini = j;
					}
				}
				path.add(mini);
				array[mini].isCovered = true;
				i = mini;
				boolean isCompleted = true;
				for (Vectors v : array)
					if (!v.isCovered)
						isCompleted = false;
					if (isCompleted)
						break;
			}
		int[] ret = new int[n];
		int j = 0;
		for (int m : path)
			ret[j++] = m;
		return ret;
	}
	
	public static double Distance (Vectors a, Vectors b)
	{
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
}

class Vectors{
	
	public double x;
	public double y;
	public boolean isCovered = false;

	public Vectors (double X, double Y) { x = X; y = Y;}
}
