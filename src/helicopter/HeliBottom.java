package helicopter;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;

/**
 * This class contains draw function for the bottom of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliBottom extends HeliPart {
	private int displayListID;
	
	public double height = 0.8;
	public double thickness = 0.1;
	
	public HeliBottom(GL2 gl)
	{
		init(gl);
	}
	
	public void init(GL2 gl)
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		Material.matte(gl);
		
		gl.glTranslated(-1.5, 0, 3);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, thickness, thickness, height, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(-1.5, 0, -1);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, thickness, thickness, height, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, 0, 3);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, thickness, thickness, height, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, 0, -1);
		gl.glRotated(90, 1, 0, 0);
		glu.gluCylinder(quadric, thickness, thickness, height, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(1.5, -height, -2.5);
		glu.gluCylinder(quadric, thickness * 1.2, thickness * 1.2, 7, 10, 5);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslated(-1.5, -height, -2.5);
		glu.gluCylinder(quadric, thickness * 1.2, thickness * 1.2, 7, 10, 5);

		gl.glEndList();
	}
	
	@Override
	public void drawNode(GL2 gl)
	{
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}
}
