package objects;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TextureControl;
import utils.Vector;

public class Road {
	private GL2 gl;
	private double laneWidth = 3;
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
		double distance = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.z - start.z, 2));
		
		double offset = Math.cosh(laneWidth / distance);
		
		double rad = Math.toRadians(offset);
		
    	double zOffset = laneWidth * Math.sin(rad);
    	double xOffset = laneWidth * Math.cos(rad);

		gl.glNewList(Main.displayList + Main.Displays.Road.ordinal(), GL2.GL_COMPILE);
		
		Material.road(gl);
		TextureControl.setupTexture(gl, "Road");
		
		gl.glBegin(GL2.GL_QUADS);
    	gl.glNormal3d(0, 1, 0);
    	
    	gl.glTexCoord2d(0, 1);
    	gl.glVertex3d(start.x + xOffset, start.y, start.z + zOffset);
    	gl.glTexCoord2d(0, 0);
    	gl.glVertex3d(start.x - xOffset, start.y, start.z - zOffset);
    	gl.glTexCoord2d(1, 0);
    	gl.glVertex3d(end.x - xOffset, end.y, end.z - zOffset);
    	gl.glTexCoord2d(1, 1);
		gl.glVertex3d(end.x + xOffset, end.y, end.z + zOffset);
		
		gl.glEnd();
		
		TextureControl.disableTexture(gl, "Road");
		gl.glEndList();
	}
	
	public void draw()
	{
		gl.glPushMatrix();
		
		gl.glCallList(Main.displayList + Main.Displays.Road.ordinal());
		
		gl.glPopMatrix();
	}
}
