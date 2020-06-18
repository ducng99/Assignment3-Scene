package helicopter;

import com.jogamp.opengl.GL2;

import scene.TreeNode;
import utils.Vector;

/**
 * This class contains draw function for the body of the helicopter
 * @author Duc Nguyen
 *
 */
public class HeliBody extends TreeNode {
	private Vector Position;
	public double height = 2.0;
	public double back = -2.0;
	public double bottom = 0;
	public double side = 2.0;
	public double front = 5.0;

	public HeliBody() {
	}
	
	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glColor3d(0.2, 0.5, 0);

		gl.glBegin(GL2.GL_QUADS);
		
		// Top
		gl.glVertex3d(-side, height, back);
		gl.glVertex3d(-side, height, -back);
		gl.glVertex3d(side, height, -back);
		gl.glVertex3d(side, height, back);

		// Front
		gl.glColor4d(1, 1, 1, 1);
		
		gl.glVertex3d(side, height, -back);
		gl.glVertex3d(-side, height, -back);
		gl.glVertex3d(-side / 2.0, height / 2.5, front / 1.11);
		gl.glVertex3d(side / 2.0, height / 2.5, front / 1.11);

		gl.glColor4d(0.2, 0.5, 0, 1);
		// Head
		gl.glVertex3d(-side / 2.0, height / 2.5, front / 1.11);
		gl.glVertex3d(side / 2.0, height / 2.5, front / 1.11);
		gl.glVertex3d(side / 2.0, 0, front);
		gl.glVertex3d(-side / 2.0, 0, front);

		// Right-front side
		gl.glVertex3d(-side, height, -back);
		gl.glVertex3d(-side / 2.0, height / 2.5, front / 1.11);
		gl.glVertex3d(-side / 2.0, 0, front);
		gl.glVertex3d(-side, 0, -back);
		
		// Right-back side
		gl.glVertex3d(-side, height, back);
		gl.glVertex3d(-side, height, -back);
		gl.glVertex3d(-side, 0, -back);
		gl.glVertex3d(-side, 0, back);
		
		// Back side
		gl.glVertex3d(-side, height, back);
		gl.glVertex3d(side, height, back);
		gl.glVertex3d(side, 0, back);
		gl.glVertex3d(-side, 0, back);
		
		// Left-front side
		gl.glVertex3d(side, height, -back);
		gl.glVertex3d(side / 2.0, height / 2.5, front / 1.11);
		gl.glVertex3d(side / 2.0, 0, front);
		gl.glVertex3d(side, 0, -back);
		
		// Left-back side
		gl.glVertex3d(side, height, back);
		gl.glVertex3d(side, height, -back);
		gl.glVertex3d(side, 0, -back);
		gl.glVertex3d(side, 0, back);
		
		gl.glEnd();

		//Bottom side
		gl.glBegin(GL2.GL_POLYGON);
		
		gl.glVertex3d(-side, 0, back);
		gl.glVertex3d(-side, 0, -back);
		gl.glVertex3d(-side / 2.0, 0, front);
		gl.glVertex3d(side / 2.0, 0, front);
		gl.glVertex3d(side, 0, -back);
		gl.glVertex3d(side, 0, back);
		
		gl.glEnd();

        gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}

    public void setPosition(Vector target)
    {
    	Position = target;
    }
    
    public Vector getPosition()
    {
    	return Position;
    }
}
