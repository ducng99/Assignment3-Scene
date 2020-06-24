package car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import App.Main;
import scene.Material;
import scene.TreeNode;
import utils.Vector;

public class Wheel extends TreeNode {
	private GL2 gl;
	private GLU glu = new GLU();
	private GLUquadric quadric = glu.gluNewQuadric();
	private Vector Position;
	public final double radius = 0.5;

	private int displayListID;
	
	public Wheel(GL2 gl) {
		this.gl = gl;
		
		init();
	}

	public void init()
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);

		Material.matte(gl);
		
		// Left
		gl.glPushMatrix();
		gl.glTranslated(radius * 0.25, 0, 0);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		
		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);

		for (double deg = 0.0; deg <= 360.0; deg += 360.0 / 20)
		{
			double rad = Math.toRadians(deg);
			double tmpZ = Math.cos(rad) * radius;
		    double tmpY = Math.sin(rad) * radius;
		    gl.glVertex3d(0, tmpY, tmpZ);
		}
		
		gl.glEnd();
		gl.glPopMatrix();
		
		// Middle
		gl.glPushMatrix();
		gl.glTranslated(-radius * 0.25, 0, 0);
		gl.glRotated(90, 0, 1, 0);
		glu.gluCylinder(quadric, radius, radius, radius * 0.5, 20, 2);
		gl.glPopMatrix();
		
		// Right
		gl.glPushMatrix();
		gl.glTranslated(-radius * 0.25, 0, 0);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		
		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d(0, 0, 0);

		for (double deg = 0.0; deg <= 360.0; deg += 360.0 / 20)
		{
			double rad = Math.toRadians(deg);
			double tmpZ = Math.cos(rad) * radius;
		    double tmpY = Math.sin(rad) * radius;
		    gl.glVertex3d(0, tmpY, tmpZ);
		}
		
		gl.glEnd();
		gl.glPopMatrix();
		
		gl.glEndList();
	}
	
	public void setPosition(Vector position) {
		Position = position;
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}
}
