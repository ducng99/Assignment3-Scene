package helicopter;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import scene.Material;
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
	
	private ArrayList<Vector> verticies = new ArrayList<>();
	private ArrayList<short[]> faces = new ArrayList<>();

	public HeliBody() {
		verticies.add(new Vector(-side, height, back));							//0
		verticies.add(new Vector(-side, height, -back));						//1
		verticies.add(new Vector(side, height, -back));							//2
		verticies.add(new Vector(side, height, back));							//3
		verticies.add(new Vector(side, 0, back));								//4
		verticies.add(new Vector(-side, 0, back));								//5
		verticies.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));		//6
		verticies.add(new Vector(-side / 2.0, 0, front));						//7
		verticies.add(new Vector(-side, 0, -back));								//8
		verticies.add(new Vector(side / 2.0, height / 2.5, front / 1.11));		//9
		verticies.add(new Vector(side / 2.0, 0, front));						//10
		verticies.add(new Vector(side, 0, -back));								//11
		
		// Top
		faces.add(new short[] {0, 1, 2, 3});
		// Head
		faces.add(new short[] {6, 9, 10, 7});
		// Right-front side
		faces.add(new short[] {1, 6, 7, 8});
		// Right-back side
		faces.add(new short[] {0, 1, 8, 5});
		// Back side
		faces.add(new short[] {0, 3, 4, 5});
		// Left-front side
		faces.add(new short[] {2, 9, 10, 11});
		// Left-back side
		faces.add(new short[] {3, 2, 11, 4});
	}
	
	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		ArrayList<Vector> normals;
		ArrayList<Vector> points = new ArrayList<>();
		
		Material.metal(gl);

		gl.glBegin(GL2.GL_QUADS);
		
		//// Top
		/*points.add(new Vector(-side, height, back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(side, height, -back));
		points.add(new Vector(side, height, back));*/
		
		normals = Normal.CalcPerVertex(points);
		gl.glNormal3d(normals.get(0).x, normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();

		//// Head
		Material.metal(gl);
		
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, 0, front));
		points.add(new Vector(-side / 2.0, 0, front));

		normals = Normal.CalcPerVertex(points);
		gl.glNormal3d(normals.get(0).x, -normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, -normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, -normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, -normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();

		//// Right-front side
		/*points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(-side / 2.0, 0, front));
		points.add(new Vector(-side, 0, -back));*/

		normals = Normal.CalcPerVertex(points);

		gl.glNormal3d(-normals.get(0).x, normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(-normals.get(1).x, normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(-normals.get(2).x, normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(-normals.get(3).x, normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		//// Right-back side
		/*points.add(new Vector(-side, height, back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side, 0, -back));
		points.add(new Vector(-side, 0, back));*/

		normals = Normal.CalcPerVertex(points);

		gl.glNormal3d(-normals.get(0).x, normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(-normals.get(1).x, normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(-normals.get(2).x, normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(-normals.get(3).x, normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		//// Back side
		/*points.add(new Vector(-side, height, back));
		points.add(new Vector(side, height, back));
		points.add(new Vector(side, 0, back));
		points.add(new Vector(-side, 0, back));*/

		normals = Normal.CalcPerVertex(points);

		gl.glNormal3d(normals.get(0).x, normals.get(0).y, -normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, normals.get(1).y, -normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, normals.get(2).y, -normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, normals.get(3).y, -normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		//// Left-front side
		/*points.add(new Vector(side, height, -back));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, 0, front));
		points.add(new Vector(side, 0, -back));*/

		normals = Normal.CalcPerVertex(points);

		gl.glNormal3d(normals.get(0).x, normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// Left-back side
		points.add(new Vector(side, height, back));
		points.add(new Vector(side, height, -back));
		points.add(new Vector(side, 0, -back));
		points.add(new Vector(side, 0, back));

		normals = Normal.CalcPerVertex(points);

		gl.glNormal3d(normals.get(0).x, normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, normals.get(3).y, normals.get(3).z);
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

		normals = Normal.CalcPerVertex(points);
		// revert y-axis because this is the bottom of the plane

		gl.glNormal3d(normals.get(0).x, -normals.get(0).y, normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, -normals.get(1).y, normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, -normals.get(2).y, normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, -normals.get(3).y, normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		gl.glNormal3d(normals.get(4).x, -normals.get(4).y, normals.get(4).z);
		gl.glVertex3dv(points.get(4).ToArray(), 0);
		gl.glNormal3d(normals.get(5).x, -normals.get(5).y, normals.get(5).z);
		gl.glVertex3dv(points.get(5).ToArray(), 0);
		points.clear();
		
		gl.glEnd();

		// Front
		Material.glass(gl);
		
		gl.glBegin(GL2.GL_QUADS);

		points.add(new Vector(side, height, -back));
		points.add(new Vector(-side, height, -back));
		points.add(new Vector(-side / 2.0, height / 2.5, front / 1.11));
		points.add(new Vector(side / 2.0, height / 2.5, front / 1.11));

		normals = Normal.CalcPerVertex(points);
		gl.glNormal3d(normals.get(0).x, normals.get(0).y, -normals.get(0).z);
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glNormal3d(normals.get(1).x, normals.get(1).y, -normals.get(1).z);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glNormal3d(normals.get(2).x, normals.get(2).y, -normals.get(2).z);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glNormal3d(normals.get(3).x, normals.get(3).y, -normals.get(3).z);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
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
