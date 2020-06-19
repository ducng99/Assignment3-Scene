package utils;

import java.util.ArrayList;

public class Normal {

	/**
	 * Calculate normal from given points
	 * @param points
	 * @return
	 */
	public static Vector Calc(ArrayList<Vector> points)
	{
		Vector normal = new Vector();
		
		for (int i = 0; i < points.size(); i++)
		{
			Vector current = points.get(i);
			Vector next = points.get((i + 1) % points.size());
			
			normal.x += (current.y - next.y) * (current.z + next.z);
			normal.y += (current.z - next.z) * (current.x + next.x);
			normal.z += (current.x - next.x) * (current.y + next.y);
		}
		
		normal.x = Math.abs(normal.x);
		normal.y = Math.abs(normal.y);
		normal.z = Math.abs(normal.z);
		
		return normal;
	}

}
