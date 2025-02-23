package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TextureControl;
import scene.TreeNode;
import utils.Normal;
import utils.Utils;
import utils.Vector;

/**
 * A {@link TreeNode} object that draws a road on the terrain and add road lights and trees.
 * @author Duc Nguyen
 *
 */
public class Road extends TreeNode {
	private GL2 gl;
	private int displayListID;
	private double laneWidth = 5;
	private Vector start;
	private Vector end;
	
	private ArrayList<Tree> trees = new ArrayList<>();
	private ArrayList<StreetLight> lights = new ArrayList<>();

	public Road(GL2 gl, Vector start, Vector end) {
		this.gl = gl;
		this.start = start;
		this.end = end;
		
		init();
	}

	public void init()
	{
		// Find radian of rotation of the line connect start and end point (discard height -> 2D calc)
		double distance = end.z - start.z;
		double distance1 = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(distance, 2));
		double rad = Math.acos(distance / distance1);
		
		// Calculate offsets needed to move (laneWidth) unit
    	double xOffset = laneWidth * Math.cos(rad);
    	double zOffset = laneWidth * Math.sin(rad);
    	
    	displayListID = Main.genDisplayList(gl);

		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		Material.road(gl);
		TextureControl.setupTexture(gl, "Road");
		
		gl.glBegin(GL2.GL_QUADS);
    	
    	double textureLength = laneWidth * 1.5;
    	double loopTimes = (distance1 / textureLength) * 10;
    	Vector loopLength = new Vector((end.x - start.x) / loopTimes, 0, (end.z - start.z) / loopTimes);
    	
    	for (int i = 0; i < loopTimes - 1; i++)
    	{
    		ArrayList<Vector> points = new ArrayList<>();
    		points.add(new Vector(start.x + xOffset + i * loopLength.x, 0, start.z + zOffset + i * loopLength.z));
    		points.add(new Vector(start.x - xOffset + i * loopLength.x, 0, start.z - zOffset + i * loopLength.z));
    		points.add(new Vector(start.x - xOffset + (i + 1) * loopLength.x, 0, start.z - zOffset + (i + 1) * loopLength.z));
    		points.add(new Vector(start.x + xOffset + (i + 1) * loopLength.x, 0, start.z + zOffset + (i + 1) * loopLength.z));
        	points.get(0).y = Main.terrain.getHeightAt(points.get(0));
        	points.get(1).y = Main.terrain.getHeightAt(points.get(1));
        	points.get(2).y = Main.terrain.getHeightAt(points.get(2));
        	points.get(3).y = Main.terrain.getHeightAt(points.get(3));
        	
        	Vector normal = Normal.CalcPolygon(points);
        	gl.glNormal3dv(normal.ToArray(), 0);
    		
        	gl.glTexCoord2d(i / 10.0, 1);
        	gl.glVertex3dv(points.get(0).ToArray(), 0);
        	
        	if (i % 20 == 0)
        	{
        		setupLight(points.get(0), Math.toDegrees(rad));
        	}
        	else if (i % 10 == 0)
        	{
        		setupTree(points.get(0));
        	}
        	
        	gl.glTexCoord2d(i / 10.0, 0);
        	gl.glVertex3dv(points.get(1).ToArray(), 0);
        	
        	if (i % 20 == 10)
        	{
        		setupLight(points.get(1), Math.toDegrees(rad) + 180);
        	}
        	else if (i % 10 == 0)
        	{
        		setupTree(points.get(1));
        	}
        	
        	gl.glTexCoord2d((i + 1) / 10.0, 0);
        	gl.glVertex3dv(points.get(2).ToArray(), 0);
        	
        	gl.glTexCoord2d((i + 1) / 10.0, 1);
        	gl.glVertex3dv(points.get(3).ToArray(), 0);
    	}
		
		gl.glEnd();
		
		TextureControl.disableTexture(gl, "Road");
		
		gl.glEndList();
		
		for (StreetLight light : lights)
		{
			light.init(gl);
		}
		
		for (Tree tree : trees)
		{
			tree.init();
		}
		
		setupBuildings(rad);
		setupCars(rad);
	}
	
	private void setupLight(Vector tmp, double deg)
	{
		StreetLight light = new StreetLight(deg);
    	light.setPosition(tmp);
    	light.setupLight(gl);
    	this.addChild(light);
    	lights.add(light);
	}
	
	private void setupTree(Vector tmp)
	{
		Tree tree = new Tree(gl, Tree.TreeType.values()[Utils.genRand(Tree.TreeType.values().length - 1, 0)]);
		tree.setPosition(tmp);
		this.addChild(tree);
		trees.add(tree);
	}
	
	private void setupCars(double rad)
	{
		for (int i = 0; i < 20; i++)
		{
			int direction = Utils.genRand(1, 0);	// 0 = go forward, 1 = go back
			Car car;
			
	    	double xOffset = laneWidth * 0.33 * Math.cos(rad);
	    	double zOffset = laneWidth * 0.33 * Math.sin(rad);
	    	
	    	int distanceShift = Utils.genRand(5, -5);
	    	double xOffsetF = (i * start.distanceTo(end) / 20.0 + distanceShift) * Math.sin(rad);
	    	double zOffsetF = (i * start.distanceTo(end) / 20.0 + distanceShift) * Math.cos(rad);
			
			if (direction == 0)
			{
				Vector startPoint = start.Offset(xOffset, 0, zOffset);
				Vector endPoint = end.Offset(xOffset, 0, zOffset);
				
		    	Vector startTmp = startPoint.Offset(xOffsetF, 0, zOffsetF);
		    	
				car = new Car(gl, startPoint, startPoint.distanceTo(endPoint), rad);
				car.setPosition(startTmp);
			}
			else
			{
				Vector startPoint = start.Offset(-xOffset, 0, -zOffset);
				Vector endPoint = end.Offset(-xOffset, 0, -zOffset);
				
		    	Vector endTmp = endPoint.Offset(-xOffsetF, 0, -zOffsetF);
		    	
				car = new Car(gl, endPoint, endPoint.distanceTo(startPoint), rad + Math.PI);
				car.setPosition(endTmp);
			}
			
			this.addChild(car);
		}
	}
	
	private void setupBuildings(double rad)
	{
		double startPoint = 0;
		double distance = start.distanceTo(end);
		
		double direction = Math.toDegrees(rad);
    	double xOffset = (laneWidth + 2) * Math.cos(rad);
    	double zOffset = (laneWidth + 2) * Math.sin(rad);
		
		do
		{
			Building building = new Building(gl, direction);
			double wide = building.getWide();
			
			if (startPoint + wide < distance)
			{
				double xOffset2 = xOffset + Math.sin(rad) * startPoint;
				double zOffset2 = zOffset + Math.cos(rad) * startPoint;
				building.setPosition(start.Offset(xOffset2, 0, zOffset2));
				building.init();
				this.addChild(building);
				
				startPoint += wide + 2;		// Leave some space between buildings
			}
			else
				break;
		}
		while (startPoint < distance);
	}
	
	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		// We use start and end vectors here
	}
}
