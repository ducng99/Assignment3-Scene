package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import scene.Vertex;

public class ObjFile {
	private static Scanner scanner;
	public static HashMap<String,ObjFile> objectsMap = new HashMap<>();
	
	private String path;
	public ArrayList<Vertex> vertices = new ArrayList<>();
	public ArrayList<int[]> faces = new ArrayList<>();
	private ArrayList<Vector> normals = new ArrayList<>();

	public ObjFile(String path)
	{
		this.path = path;
		
		try {
			readObjFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Import objects into a hashmap
	 */
	public static void importObjects()
	{
		objectsMap.put("PalmTree", new ObjFile("./others/obj/Palm_Tree.obj"));
		objectsMap.put("FirTree", new ObjFile("./others/obj/Fir_Tree.obj"));
	}

	/**
	 * Method that process .obj file and output usable details
	 * @param path - file path to .obj file
	 * @throws Exception
	 */
	public void readObjFile() throws IOException
	{
		File file = new File(path);
		
		scanner = new Scanner(file);
		ArrayList<String> lines = new ArrayList<>();
		while (scanner.hasNextLine())
		{
			lines.add(scanner.nextLine());
		}
		
		// First loop reads vertices and normals. Not reading faces because it includes index of vertices and normals.
		for (String line : lines)
		{			
			if (line.startsWith("v "))
			{
				String[] data = line.split(" ");
				
				vertices.add(new Vertex(new Vector(Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]))));
			}
			else if (line.startsWith("vn "))
			{
				String[] data = line.split(" ");
				
				normals.add(new Vector(Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3])));
			}
		}
		
		// Read faces after we know all indexes of vertices and normals
		for (String line : lines)
		{
			if (line.startsWith("f "))
			{
				String[] points = line.split(" ");
				int[] facePoints = new int[points.length - 1];
				
				// Skip character "f"
				for (int i = 1; i < points.length; i++)
				{
					String[] data = points[i].split("/");
					Vertex v = vertices.get(Integer.valueOf(data[0]) - 1);	// Indexes in .obj files start from 1, not 0
					v.setNormal(normals.get(Integer.valueOf(data[2]) - 1));	// ^
					
					facePoints[i - 1] = Integer.valueOf(data[0]) - 1;		// ^
				}
				
				faces.add(facePoints);
			}
		}
		
		scanner.close();
	}
}
