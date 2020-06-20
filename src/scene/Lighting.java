package scene;

import com.jogamp.opengl.GL2;

import utils.Vector;

public class Lighting {
	private GL2 gl;
	
    private Vector Position = new Vector();
    
	private float ambientLight[] = { 0.2f, 0.2f, 0.2f, 1f }; // ambient
	private float diffuseLight[] = { 0.3f, 0.3f, 0.3f, 1f }; // diffuse
	private float specularLight[] = { 0.2f, 0.2f, 0.2f, 1f }; // specular
    private float globalAmbientLight[] = { 0.3f, 0.3f, 0.3f, 1 };
    
    public static enum LightType {
    	Directional, Spotlight
    }

	public Lighting(GL2 gl, LightType type) {
		this.gl = gl;
	}
	
	/**
	 * Enable skyLighting and set parameters for light
	 */
	public void setupLighting()
	{
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);
        
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
	}
	
	/**
	 * Draw light. Set light position
	 */
	public void draw()
	{
		float[] lightPosition = {(float)Position.x, (float)Position.y, (float)Position.z};
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPOT_DIRECTION, new float[] {1f, 1f, 1f},0);
		//gl.glLightf(GL2.GL_LIGHT0, GL2.GL_SPOT_CUTOFF, 45f);
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	public Vector getLightPos()
	{
		return Position;
	}
}
