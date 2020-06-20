package helicopter;

import com.jogamp.opengl.GL2;

import scene.Material;

/**
 * This class contains draw function for the bottom of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliBottom extends HeliPart {
	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		
		Material.chrome(gl);
		
		gl.glTranslated(-1.5, 0, 3);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, .1, .1, .3, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(-1.5, 0, -1);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, .1, .1, .3, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, 0, 3);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, .1, .1, .3, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, 0, -1);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, .1, .1, .3, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, -.3, -2.5);
		glu.gluCylinder(quadric, .12, .12, 7, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(-1.5, -.3, -2.5);
		glu.gluCylinder(quadric, .12, .12, 7, 10, 5);
		
		gl.glPopMatrix();
	}
}
