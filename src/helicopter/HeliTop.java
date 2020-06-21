package helicopter;

import com.jogamp.opengl.GL2;

import scene.Material;

/**
 * This class contains draw function for the top blades of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliTop extends HeliPart {
	public final Rotor rotor;
	
	public HeliTop()
	{
		rotor = new Rotor(2, 7);
		rotor.setPosition(this.Position.Offset(0, 0.3));
		this.addChild(rotor);
	}

	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		Material.matte(gl);
		
		gl.glRotated(-90, 1, 0, 0);
		
		// Blades holder thingy
		glu.gluCylinder(quadric, .4, .3, .6, 10, 15);
		
		gl.glPopMatrix();
	}
}
