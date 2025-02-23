package scene;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.jogamp.opengl.GL2;

import App.Main;
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
    private double distance = 0;
    
    public static LinkedList<LightSource> lights = new LinkedList<>();
    private static LightDistanceComparator comp = new LightDistanceComparator();
    private static class LightDistanceComparator implements Comparator<LightSource> {
		@Override
		public int compare(LightSource o1, LightSource o2) {			
			return (int)(o1.distance - o2.distance);
		}
    }
    
    public static enum LightType {
    	Directional, Spotlight
    }

	public LightSource(GL2 gl, LightType type) {
		this.gl = gl;
		this.type = type;
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
	
	public static void drawLight()
	{
		LinkedList<LightSource> tmp = new LinkedList<>();
		tmp.addAll(lights);
		
		Vector heliPos = Main.helicopter.getPosition();
		
		tmp.parallelStream().forEach((light) -> {
			if (light.type != LightType.Directional)
				light.distance = light.Position.distanceTo(heliPos);
		});
		
		Collections.sort(tmp, comp);
		for (int i = 0; i < 8 && i < tmp.size(); i++)
		{
			LightSource source = tmp.get(i);
			source.draw(i);
		}
	}
	
	/**
	 * Draw light. Set light position
	 */
	public void draw(int lightID)
	{
        gl.glEnable(GL2.GL_LIGHTING);
        
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0 + lightID, GL2.GL_SPECULAR, specularLight, 0);
		
		if (useGlobalAmbient)
			gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);
		
        gl.glEnable(GL2.GL_LIGHT0 + lightID);
        
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
