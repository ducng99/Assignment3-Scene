package objects;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import App.Main;
import scene.LightSource;
import scene.Material;
import scene.TreeNode;
import utils.Vector;

public class StreetLight extends TreeNode {
	private int displayListID;
	
	private Vector Position = new Vector();
	private GLU glu;
	private GLUquadric quadric;
	private double direction = 0;
	private LightSource light;
	
	public StreetLight(double direction)
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		this.direction = direction;
	}
	
	/**
	 * This function was made after 4 hours forgetting to set {@link Position} and still, trying to debug 
	 * @param gl
	 */
	public void setupLight(GL2 gl)
	{
		// Setup lights
		double rad = Math.toRadians(direction);
    	double xOffset = -1.2 * Math.cos(rad);
		double zOffset = -1.2 * Math.sin(rad);
		
		light = new LightSource(gl, LightSource.LightType.Spotlight);
		light.setPosition(Position.Offset(xOffset, 5.5, zOffset));
		light.setParameters(new float[] { 0.4f, 0.4f, 0.4f, 1f }, new float[] {0.8f, 0.8f, 0.8f, 1f}, new float[] {0.9f, 0.9f, 0.9f, 1f}, false);
		light.setSpotlightParameters(new Vector(0, -1, 0), 40f);
	}
	
	public void init(GL2 gl)
	{
		displayListID = Main.genDisplayList(gl);
		gl.glNewList(displayListID, GL2.GL_COMPILE);

		Material.matte(gl);
		
		// Stand
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, .06, .06, 6, 5, 2);
		
		gl.glRotated(90, 1, 0, 0);
		
		// Light hanger
		gl.glTranslated(0, 5.5, 0);
		gl.glRotated(direction - 90, 0, 1, 0);
		glu.gluCylinder(quadric, .06, .06, 1.5, 5, 2);
		
		gl.glRotated(-direction + 90, 0, 1, 0);
		
		// Bulb cover
		double rad = Math.toRadians(direction);
    	double xOffset = -1.2 * Math.cos(rad);
		double zOffset = -1.2 * Math.sin(rad);
		gl.glTranslated(xOffset, 0, zOffset);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, 0.06, 0.2, 0.2, 6, 4);
		
		gl.glEndList();
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}
	
	public Vector getPosition() {
		return Position;
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}
}
