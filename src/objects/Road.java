package objects;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Lighting;
import scene.Material;
import scene.TextureControl;
import scene.TreeNode;
import utils.Vector;

/**
 * A {@link TreeNode} object that draws a road on the terrain and add road lights and trees.
 * @author Duc Nguyen
 *
 */
public class Road extends TreeNode {
	private GL2 gl;
	private double laneWidth = 5;
	private Vector start;
	private Vector end;

	public Road(GL2 gl, Vector start, Vector end) {
		this.gl = gl;
		this.start = start;
		this.end = end;
		
		init();
	}

	public void init()
	{
		// Find radian of rotation of the line connect start and end point
		double distance = end.z - start.z;
		double distance1 = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(distance, 2));
		double rad = Math.acos(distance / distance1);
		
		// Calculate offsets needed to move (laneWidth) unit
    	double xOffset = laneWidth * Math.cos(rad);
    	double zOffset = laneWidth * Math.sin(rad);

		gl.glNewList(Main.displayList + Main.Displays.Road.ordinal(), GL2.GL_COMPILE);
		
		Material.road(gl);
		TextureControl.setupTexture(gl, "Road");
		
		gl.glBegin(GL2.GL_QUADS);
    	gl.glNormal3d(0, 1, 0);
    	
    	double textureLength = laneWidth * 1.5;
    	double loopTimes = distance1 / textureLength;
    	Vector loopLength = new Vector((end.x - start.x) / loopTimes, 0, (end.z - start.z) / loopTimes);
    	Vector tmp;
    	
    	for (int i = 0; i < loopTimes - 1; i++)
    	{
    		// Add 0.1 to height because we are not calculating every point of the road, some part of it might be under terrain
        	gl.glTexCoord2d(0, 1);
        	tmp = new Vector(start.x + xOffset + i * loopLength.x, 0, start.z + zOffset + i * loopLength.z);
        	tmp.y = Main.terrain.getHeightAt(tmp) + 0.1;
        	gl.glVertex3d(tmp.x, tmp.y, tmp.z);
        	
        	if (i % 5 == 0 && Math.abs((loopTimes / 2 - i)) < 10 && Lighting.lightCount <= 7)
        	{
        		StreetLight light = new StreetLight(Math.toDegrees(rad));
            	light.setPosition(new Vector(tmp.x, Main.terrain.getHeightAt(tmp) + 0.1, tmp.z));
            	light.setupLight(gl);
            	this.addChild(light);
        	}
        	
        	gl.glTexCoord2d(0, 0);
        	tmp = new Vector(start.x - xOffset + i * loopLength.x, 0, start.z - zOffset + i * loopLength.z);
        	tmp.y = Main.terrain.getHeightAt(tmp) + 0.1;
        	gl.glVertex3d(tmp.x, tmp.y, tmp.z);
        	
        	gl.glTexCoord2d(1, 0);
        	tmp = new Vector(start.x - xOffset + (i + 1) * loopLength.x, 0, start.z - zOffset + (i + 1) * loopLength.z);
        	tmp.y = Main.terrain.getHeightAt(tmp) + 0.1;
        	gl.glVertex3d(tmp.x, tmp.y, tmp.z);
        	
        	if (i % 5 == 2 && Math.abs((loopTimes / 2 - i)) < 10 && Lighting.lightCount <= 7)
        	{
        		StreetLight light = new StreetLight(Math.toDegrees(rad) + 180);
            	light.setPosition(new Vector(tmp.x, tmp.y, tmp.z));
            	light.setupLight(gl);
            	this.addChild(light);
        	}
        	
        	gl.glTexCoord2d(1, 1);
        	tmp = new Vector(start.x + xOffset + (i + 1) * loopLength.x, 0, start.z + zOffset + (i + 1) * loopLength.z);
        	tmp.y = Main.terrain.getHeightAt(tmp) + 0.1;
        	gl.glVertex3d(tmp.x, tmp.y, tmp.z);
    	}
		
		gl.glEnd();
		
		TextureControl.disableTexture(gl, "Road");
		gl.glEndList();
	}
	
	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		
		gl.glCallList(Main.displayList + Main.Displays.Road.ordinal());
		
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		// We use start and end vectors here
	}
}
