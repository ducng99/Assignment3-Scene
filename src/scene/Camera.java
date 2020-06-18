package scene;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import utils.Vector;

/**
 * Basic Camera class
 * 
 * @author Duc Nguyen
 *
 */
public class Camera {

	private static final double FOV = 68;
	
	private Vector Position = new Vector(10, 5, 7);
	private Vector lookAtPos = new Vector();

	double windowWidth = 4;
	double windowHeight = 3;

	public void draw(GL2 gl) {
		// set up projection first
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = new GLU();
		glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 100);
		// set up the camera position and orientation
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(Position.x, Position.y, Position.z, // eye
				lookAtPos.x, lookAtPos.y, lookAtPos.z, // looking at
				0.0, 1.0, 0.0); // up
	}

	/**
	 * Sets up the lookAt point - could be a specified object's location
	 * @param lookAtPos
	 */
	public void setLookAt(Vector lookAtPos) {
		this.lookAtPos = lookAtPos;
	}
	
	/**
	 * Set eye position
	 * @param eyePos
	 */
	public void setEyePos(Vector eyePos)
	{
		Position = eyePos;
	}

	/**
	 * Passes a new window size to the camera. This method should be called from the
	 * <code>reshape()</code> method of the main program.
	 *
	 * @param width  the new window width in pixels
	 * @param height the new window height in pixels
	 */
	public void newWindowSize(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}

}
