package objects;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import scene.Lighting;
import scene.Material;
import scene.TreeNode;
import utils.Vector;

public class StreetLight extends TreeNode {
	private Vector Position = new Vector();
	private GLU glu;
	private GLUquadric quadric;
	private double direction = 0;
	private Lighting light;
	
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
    	double xOffset = 0.8 * Math.cos(rad);
		double zOffset = 0.8 * Math.sin(rad);
		
		light = new Lighting(gl, Lighting.LightType.Spotlight);
		light.setPosition(Position.Offset(xOffset, 3.3, zOffset));
		light.setParameters(new float[] { 0.f, 0.f, 0.f, 1f }, new float[] {0.7f, 0.7f, 0.7f, 1f}, new float[] {0.9f, 0.9f, 0.9f, 1f}, false);
		light.setSpotlightParameters(new Vector(0, -1, 0), 40f);
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		Material.matte(gl);
		
		// Stand
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, .06, .06, 4, 5, 2);
		
		gl.glRotated(90, 1, 0, 0);
		
		// Light hanger
		gl.glTranslated(0, 3.3, 0);
		gl.glRotated(direction - 90, 0, 1, 0);
		glu.gluCylinder(quadric, .06, .06, 1, 5, 2);
		
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
