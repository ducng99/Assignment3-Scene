package scene;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import utils.Vector;

/**
 * Handles light source
 * @author Duc Nguyen
 *
 */
public class LightSource {
	private GL2 gl;
	
    private Vector Position = new Vector();
    
	private float ambientLight[] = { 0.4f, 0.4f, 0.4f, 1f };
	private float diffuseLight[] = { 0.4f, 0.4f, 0.4f, 1f };
	private float specularLight[] = { 0.2f, 0.2f, 0.2f, 1f };
    private float globalAmbientLight[] = { 0.2f, 0.2f, 0.2f, 1f };
    
    private Vector spotlightDirection = Vector.down;
    private float spotlightCutoff = 45f;
    
    private LightType type;
    private boolean useGlobalAmbient = false;
    
    public static ArrayList<LightSource> lights = new ArrayList<>();
    
    public static enum LightType {
    	Directional, Spotlight
    }
    
    private int lightID;
    public static int lightCount = 0;

	public LightSource(GL2 gl, LightType type) {
		this.gl = gl;
		this.type = type;
		
		if (lightCount <= 7)
		{
			lightID = lightCount++;
			
			lights.add(this);
		}
		else
			throw new RuntimeException("You have used all light sources available! Consider reusing them.");	// OpenGL only allows 8 sources
	}
	
	/**
	 * Enable lighting and set parameters for light
	 */
	public void setupLighting()
	{
        gl.glEnable(GL2.GL_LIGHTING);
        
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_SPECULAR, specularLight, 0);
		
		if (useGlobalAmbient)
			gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);
		
        gl.glEnable(GL2.GL_LIGHT0 + lightID);
	}
	
	public void setParameters(float[] ambient, float[] diffuse, float[] specular, boolean useGlobalAmbient)
	{
		ambientLight = ambient;
		diffuseLight = diffuse;
		specularLight = specular;
		this.useGlobalAmbient = useGlobalAmbient;
	}
	
	public void setSpotlightParameters(Vector direction, float cutoff)
	{
		this.spotlightDirection = direction;
		this.spotlightCutoff = cutoff;
	}
	
	/**
	 * Draw light. Set light position
	 */
	public void draw()
	{
		float[] lightPosition = new float[] {(float)Position.x, (float)Position.y, (float)Position.z, type.ordinal()};
		
		if (type == LightType.Spotlight)
		{
			gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_SPOT_DIRECTION, spotlightDirection.ToFArray(), 0);
			gl.glLightf(GL2.GL_LIGHT0 + lightID, GL2.GL_SPOT_CUTOFF, spotlightCutoff);
		}
		
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_POSITION, lightPosition, 0);
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	public Vector getLightPos()
	{
		return Position;
	}
}
