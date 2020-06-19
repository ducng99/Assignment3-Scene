package helicopter;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import scene.TreeNode;
import utils.Normal;
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
		
		Vector normal;
		ArrayList<Vector> points = new ArrayList<>();
		
		gl.glColor3d(0.2, 0.5, 0);

		gl.glBegin(GL2.GL_QUADS);
		
		// Top
		points.add(new Vector(-side, height, back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(side, height, -back));
		points.add(new Vector(side, height, back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();

		// Front
		gl.glColor4d(1, 1, 1, 1);

		points.add(new Vector(side, height, -back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();

		// Head
		gl.glColor4d(0.2, 0.5, 0, 1);
		
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, 0, front));
		points.add(new Vector(-side / 2.0, 0, front));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();

		// Right-front side
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(-side / 2.0, 0, front));
		points.add(new Vector(-side, 0, -back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(-normal.x, normal.y, normal.z);	// Revert x-axis because it's the right side (0 to -infinity)
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// Right-back side
		points.add(new Vector(-side, height, back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side, 0, -back));
		points.add(new Vector(-side, 0, back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(-normal.x, normal.y, normal.z);	// Revert x-axis because it's the right side (0 to -infinity)
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// Back side
		points.add(new Vector(-side, height, back));
		points.add(new Vector(side, height, back));
		points.add(new Vector(side, 0, back));
		points.add(new Vector(-side, 0, back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, -normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// Left-front side
		points.add(new Vector(side, height, -back));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, 0, front));
		points.add(new Vector(side, 0, -back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// Left-back side
		points.add(new Vector(side, height, back));
		points.add(new Vector(side, height, -back));
		points.add(new Vector(side, 0, -back));
		points.add(new Vector(side, 0, back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		gl.glEnd();

		//Bottom side
		gl.glBegin(GL2.GL_POLYGON);

		points.add(new Vector(-side, 0, back));
		points.add(new Vector(-side, 0, -back));
		points.add(new Vector(-side / 2.0, 0, front));
		points.add(new Vector(side / 2.0, 0, front));
		points.add(new Vector(side, 0, -back));
		points.add(new Vector(side, 0, back));
		
		normal = Normal.Calc(points);
		gl.glNormal3d(normal.x, -normal.y, normal.z);	// revert y-axis because this is the bottom of the plane
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		gl.glVertex3dv(points.get(4).ToArray(), 0);
		gl.glVertex3dv(points.get(5).ToArray(), 0);
		points.clear();
		
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
