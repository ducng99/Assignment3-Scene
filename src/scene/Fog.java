package scene;

import com.jogamp.opengl.GL2;

/**
 * Simple class that setup fog
 * @author Duc Nguyen
 *
 */
public class Fog {
	public static float fogColor[] = {0.1f, 0.2f, 0.3f, 1f};

	public static void setupFog(GL2 gl)
	{
		gl.glEnable(GL2.GL_FOG);
		
		gl.glFogf(GL2.GL_FOG_MODE, GL2.GL_EXP2);
		gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
		gl.glFogf(GL2.GL_FOG_DENSITY, 0.005f);	// This thing is crazy
	}
}
