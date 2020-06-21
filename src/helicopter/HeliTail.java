package helicopter;

import com.jogamp.opengl.GL2;

import scene.Material;
import utils.Vector;

/**
 * This class contains draw function for the tail and tail blades of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliTail extends HeliPart {
	public final Rotor rotor;
	
	public HeliTail()
	{
		rotor = new Rotor(1, 0.8, 90, Vector.backward);
		rotor.setPosition(Position.Offset(.35, -.5, -6));
		this.addChild(rotor);
	}

	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		
		Material.metal(gl);

		gl.glTranslated(0, -.5, 0);
		
		// Tail
		gl.glRotated(180, 0, 1, 0);
		glu.gluCylinder(quadric, .5, .2, 7, 20, 15);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		Material.matte(gl);
		
		// Blade holder thing
		gl.glTranslated(0.2, -.5, -6);
		gl.glRotated(90, 0, 1, 0);
		glu.gluCylinder(quadric, .15, .1, 0.3, 10, 15);
		
		gl.glPopMatrix();
	}
}
