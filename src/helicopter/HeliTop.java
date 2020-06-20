package helicopter;

import com.jogamp.opengl.GL2;

import scene.Material;

/**
 * This class contains draw function for the top blades of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliTop extends HeliPart {
	public final Motor motor;
	
	public HeliTop()
	{
		motor = new Motor(2, 7);
		motor.setPosition(this.Position.Offset(0, 0.3));
		this.addChild(motor);
	}

	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		Material.chrome(gl);
		
		//gl.glColor3d(0.1, 0.1, 0.1);
		gl.glRotated(-90, 1, 0, 0);
		
		// Blades holder thingy
		glu.gluCylinder(quadric, .4, .3, .6, 10, 15);
		
		gl.glPopMatrix();
	}
}
