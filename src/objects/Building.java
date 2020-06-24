package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TreeNode;
import utils.Normal;
import utils.Utils;
import utils.Vector;

/**
 * This class handles drawing of a building. It will generate a building with random levels and width
 * @author Duc Nguyen
 *
 */
public class Building extends TreeNode {
	private GL2 gl;
	private int displayListID;
	
	private Vector Position;
	private double direction;
	
	public static ArrayList<Building> buildings = new ArrayList<>();
	private double buildingHeight = 0;
	public static Building highestBuilding = null;
	
	private int levels = Utils.genRand(6, 1);
	private double wide = Utils.genRand(20, 5);
	private ArrayList<Vector> vertices = new ArrayList<>();
	private ArrayList<int[]> faces = new ArrayList<>();
	
	public Building(GL2 gl, double direction) {
		this.gl = gl;
		this.direction = direction;
	}
	
	public void init()
	{
		double floorHighest = 0;
		// Floor
		Vector p;
		vertices.add(new Vector(0, 0, 0));						//0
		p = Position;
		vertices.get(0).y = Main.terrain.getHeightAt(p);
		if (floorHighest < vertices.get(0).y) floorHighest = vertices.get(0).y;
		
		vertices.add(new Vector(wide, 0, 0));					//1
		p = Position.Offset(wide);
		vertices.get(1).y = Main.terrain.getHeightAt(p);
		if (floorHighest < vertices.get(1).y) floorHighest = vertices.get(1).y;
		
		vertices.add(new Vector(wide, 0, wide));				//2
		p = Position.Offset(wide, 0, wide);
		vertices.get(2).y = Main.terrain.getHeightAt(p);
		if (floorHighest < vertices.get(2).y) floorHighest = vertices.get(2).y;
		
		vertices.add(new Vector(0, 0, wide));					//3
		p = Position.Offset(0, 0, wide);
		vertices.get(3).y = Main.terrain.getHeightAt(p);
		if (floorHighest < vertices.get(3).y) floorHighest = vertices.get(3).y;
		
		buildingHeight = levels * 5 + floorHighest;
		
		if (highestBuilding == null || highestBuilding.buildingHeight < this.buildingHeight)
			highestBuilding = this;
		
		// Top
		vertices.add(new Vector(0, buildingHeight, 0));			//4
		vertices.add(new Vector(wide, buildingHeight, 0));		//5
		vertices.add(new Vector(wide, buildingHeight, wide));	//6
		vertices.add(new Vector(0, buildingHeight, wide));		//7
		
		// Bottom
		faces.add(new int[] {0, 1, 2, 3});
		// Left side
		faces.add(new int[] {0, 4, 5, 1});
		// Back side
		faces.add(new int[] {1, 5, 6, 2});
		// Right side
		faces.add(new int[] {2, 6, 7, 3});
		// Top
		faces.add(new int[] {4, 7, 6, 5});
		// Front
		faces.add(new int[] {0, 3, 7, 4});
		
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		gl.glRotated(direction, 0, 1, 0);	// Same direction with road
		
		Material.building(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		
		for (int[] face : faces)
		{
			ArrayList<Vector> points = new ArrayList<>();
			points.add(vertices.get(face[0]));
			points.add(vertices.get(face[1]));
			points.add(vertices.get(face[2]));
			points.add(vertices.get(face[3]));
			
			Vector normal = Normal.CalcPolygon(points);
			gl.glNormal3dv(normal.ToArray(), 0);
			
			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
		}
		
		gl.glEnd();
		
		gl.glEndList();
		
		buildings.add(this);
	}

	public Vector getPosition() {
		return Position;
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	public double getWide() {
		return wide;
	}
	
	public Vector getMiddlePoint()
	{
		double rad = Math.toRadians(direction);
    	double xOffset = (wide / 2) * Math.cos(rad) + Math.sin(rad) * (wide / 2);
    	double zOffset = (wide / 2) * Math.sin(rad) + Math.cos(rad) * (wide / 2);
		
		return Position.Offset(xOffset, buildingHeight, zOffset);
	}
	
	/**
	 * Check whether {@link Vector a} is inside any building zone and return the height of that building
	 * @return the height of a building as double
	 */
	public double getHeightAt(Vector p)
	{
		double height = 0;
		
		Vector p0 = new Vector(Position);
		Vector p1 = Position.Offset(wide);
		Vector p2 = Position.Offset(wide, 0, wide);
		Vector p3 = Position.Offset(0, 0, wide);
		
		double area = Utils.areaQuad(p0, p1, p2, p3);
		double area1 = Utils.areaTri(p, p0, p1);
		double area2 = Utils.areaTri(p, p1, p2);
		double area3 = Utils.areaTri(p, p2, p3);
		double area4 = Utils.areaTri(p, p3, p0);
		
		// Multiply and floor because of double-precision error
		if (Math.floor(area * 100000) == Math.floor((area1 + area2 + area3 + area4) * 100000))
		{
			height += buildingHeight;
		}
		
		return height;
	}
	
	/**
	 * Loop through all buildings and get the height if point p is inside a building
	 * @param p
	 * @return the height of a building as double
	 */
	public static double getBuildingHeight(Vector p)
	{		
		for (Building b : buildings)
		{
			double tmp = b.getHeightAt(p);
			if (tmp > 0)
				return tmp;
		}
		
		return 0;
	}
	
	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}
}
