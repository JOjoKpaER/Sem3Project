package utility;
import java.util.ArrayList;

public class NearestNeighbour_method {
	
	
	public int[] path(Vectors[] array, int start)
	{
		int[] path = FindPath(array, array.length, start);
		return path;
	}
	
	public int[] FindPath (Vectors[] array, int n, int start)
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
	
	public double Distance (Vectors a, Vectors b)
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
