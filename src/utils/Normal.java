package utils;

import java.util.ArrayList;

public class Normal {

	/**
	 * Calculate normal from given points
	 * @param points
	 * @return
	 */
	public static Vector CalcPolygon(ArrayList<Vector> points)
	{
		Vector normal = new Vector();
		
		for (int i = 0; i < points.size(); i++)
		{
			Vector current = new Vector(points.get(i));
			Vector next = new Vector(points.get((i + 1) % points.size()));

			current.z *= -1;
			next.z *= -1;
			
			normal.x += (current.y - next.y) * (current.z + next.z);
			normal.y += (current.z - next.z) * (current.x + next.x);
			normal.z += (current.x - next.x) * (current.y + next.y);
		}
		
		return Normalize(normal);
	}
	
	public static Vector CalcTriangle(ArrayList<Vector> points)
	{
		if (points.size() == 3)
		{
			Vector normal = new Vector();
			
			Vector p0 = new Vector(points.get(0));
			Vector p1 = new Vector(points.get(1));
			Vector p2 = new Vector(points.get(2));
			
			p0.z *= -1;
			p1.z *= -1;
			p2.z *= -1;
			
			Vector u = new Vector(p1.x - p0.x, p1.y - p0.y, p1.z - p0.z);
			Vector v = new Vector(p2.x - p0.x, p2.y - p0.y, p2.z - p0.z);
			
			normal.x = (u.y * v.z) - (u.z * v.y);
			normal.y = (u.z * v.x) - (u.x * v.z);
			normal.z = (u.x * v.y) - (u.y * v.x);
			
			return Normalize(normal);
		}
		
		return Vector.Zero;
	}

	public static ArrayList<Vector> CalcPerVertex(ArrayList<Vector> points)
	{
		ArrayList<Vector> normals = new ArrayList<>();
		
		for (int i = 0; i < points.size(); i++)
		{
			Vector normal = new Vector();
			
			Vector current = new Vector(points.get(i));
			Vector prev = new Vector(points.get((i - 1) < 0 ? points.size() - 1 : (i - 1)));
			Vector next = new Vector(points.get((i + 1) % points.size()));
			
			current.z *= -1;
			prev.z *= -1;
			next.z *= -1;
			
			Vector u = new Vector(prev.x - current.x, prev.y - current.y, prev.z - current.z);
			Vector v = new Vector(next.x - current.x, next.y - current.y, next.z - current.z);
			
			normal.x = (u.y * v.z) - (u.z * v.y);
			normal.y = (u.z * v.x) - (u.x * v.z);
			normal.z = (u.x * v.y) - (u.y * v.x);
			
			normals.add(Normalize(normal));
		}

		return normals;
	}
	
	public static Vector Normalize(Vector normal)
	{
		double mag = Math.sqrt(Math.pow(normal.x, 2) + Math.pow(normal.y, 2) + Math.pow(normal.z, 2));
		
		return new Vector(normal.x / mag, normal.y / mag, normal.z / mag);
	}
}
