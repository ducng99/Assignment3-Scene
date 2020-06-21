package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scene.Vertex;

public class Normal {

	/**
	 * Calculate normal from given points of a polygon
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
			
			normal.x += (current.y - next.y) * (current.z + next.z);
			normal.y += (current.z - next.z) * (current.x + next.x);
			normal.z += (current.x - next.x) * (current.y + next.y);
		}
		
		return Normalize(normal);
	}
	
	/**
	 * Calculate normal from given points of a triangle
	 * @param points
	 * @return
	 */
	public static Vector CalcTriangle(ArrayList<Vector> points)
	{
		if (points.size() == 3)
		{
			Vector normal = new Vector();
			
			Vector p0 = new Vector(points.get(0));
			Vector p1 = new Vector(points.get(1));
			Vector p2 = new Vector(points.get(2));
			
			Vector u = new Vector(p1.x - p0.x, p1.y - p0.y, p1.z - p0.z);
			Vector v = new Vector(p2.x - p0.x, p2.y - p0.y, p2.z - p0.z);
			
			normal.x = (u.y * v.z) - (u.z * v.y);
			normal.y = (u.z * v.x) - (u.x * v.z);
			normal.z = (u.x * v.y) - (u.y * v.x);
			
			return Normalize(normal);
		}
		
		return Vector.Zero;
	}

	public static void CalcPerVertex(ArrayList<Vertex> vertices, ArrayList<int[]> faces)
	{
		// Running in parallel reduces loading time by around 2.2s (more than half)
		vertices.parallelStream().forEach((vertex) ->
		{
			int i = vertices.indexOf(vertex);
			List<Vector> normals = Collections.synchronizedList(new ArrayList<>());
			
			// Back up i value because parallel stream says so
			int tmpI = i;
			
			faces.parallelStream().forEach((face) ->
			{
				for (int vIndex = 0; vIndex < face.length; vIndex++)
				{
					if (face[vIndex] == tmpI)
					{
						ArrayList<Vector> points = new ArrayList<>();
						
						if (face.length == 3)
						{
							points.add(vertices.get(face[vIndex]).getPosition());
							points.add(vertices.get(face[(vIndex + 1) % face.length]).getPosition());
							points.add(vertices.get(face[(vIndex - 1) < 0 ? face.length - 1 : vIndex - 1]).getPosition());
							
							normals.add(CalcTriangle(points));
						}
						else if (face.length > 3)
						{
							for (int n = 0; n < face.length; n++)
							{
								points.add(vertices.get(face[(vIndex + n) % face.length]).getPosition());
							}
							
							normals.add(CalcPolygon(points));
						}
					}
				}
			});
			
			vertices.get(i).setNormal(Average(normals));
		});
	}
	
	public static Vector Normalize(Vector normal)
	{
		double mag = Math.sqrt(Math.pow(normal.x, 2) + Math.pow(normal.y, 2) + Math.pow(normal.z, 2));
		
		return new Vector(normal.x / mag, normal.y / mag, normal.z / mag);
	}
	
	public static Vector Average(List<Vector> vectors)
	{
		int total = vectors.size();
		
		Vector avg = new Vector();
		
		for (Vector v : vectors)
		{
			avg.x += v.x;
			avg.y += v.y;
			avg.z += v.z;
		}
		
		avg.x /= total;
		avg.y /= total;
		avg.z /= total;
		
		return avg;
	}
}
