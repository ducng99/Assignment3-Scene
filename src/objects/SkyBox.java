package objects;

import utils.Vector;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import App.Main;
import scene.TextureControl;

public class SkyBox {
	private GL2 gl;
	private GLU glu;
	private GLUquadric quadric;
	private double size;

	public SkyBox(GL2 gl) {
		this.gl = gl;
		this.glu = new GLU();
		quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		glu.gluQuadricTexture(quadric, true);
		
		init();
	}

	public void init()
	{
		gl.glNewList(Main.displayList + Main.Displays.Sky.ordinal(), GL2.GL_COMPILE);
		
		gl.glPushMatrix();
		gl.glDisable(GL2.GL_LIGHTING);	// Skybox is just an image, not illuminating
		
		gl.glColor3d(1, 1, 1);
		gl.glRotated(-90, 1, 0, 0);
		
		TextureControl.setupTexture(gl, "Sky");
		
		size = Main.camera.getViewDistance();
		
		glu.gluSphere(quadric, size / 2, 10, 15);
		TextureControl.disableTexture(gl, "Sky");
		
		//gl.glRotated(-90, 1, 0, 0);
		
		gl.glPopMatrix();
		
		gl.glEndList();
	}
	
	public void draw()
	{
		gl.glPushMatrix();
		
		// Follow the camera
		Vector pos = new Vector(Main.camera.getPosition());
		pos.y = -2;
		gl.glTranslated(pos.x, pos.y, pos.z);
		
		// If view distance is changed, update the display list
		if (Main.camera.getViewDistance() != size)
			init();
		
		gl.glCallList(Main.displayList + Main.Displays.Sky.ordinal());
		gl.glPopMatrix();
	}
}
