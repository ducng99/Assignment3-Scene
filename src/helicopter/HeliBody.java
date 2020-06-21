package helicopter;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TreeNode;
import scene.Vertex;
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
	
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<int[]> faces = new ArrayList<>();

	public HeliBody(GL2 gl) {
		vertices.add(new Vertex(new Vector(-side, height, back)));							//0
		vertices.add(new Vertex(new Vector(-side, height, -back)));							//1
		vertices.add(new Vertex(new Vector(side, height, -back)));							//2
		vertices.add(new Vertex(new Vector(side, height, back)));							//3
		vertices.add(new Vertex(new Vector(side, 0, back)));								//4
		vertices.add(new Vertex(new Vector(-side, 0, back)));								//5
		vertices.add(new Vertex(new Vector(-side / 2.0, height / 2.5, front / 1.11)));		//6
		vertices.add(new Vertex(new Vector(-side / 2.0, 0, front)));						//7
		vertices.add(new Vertex(new Vector(-side, 0, -back)));								//8
		vertices.add(new Vertex(new Vector(side / 2.0, height / 2.5, front / 1.11)));		//9
		vertices.add(new Vertex(new Vector(side / 2.0, 0, front)));							//10
		vertices.add(new Vertex(new Vector(side, 0, -back)));								//11
		
		// Sort order: to the right
		
		// Top
		faces.add(new int[] {3, 0, 1, 2});
		// Right-back side
		faces.add(new int[] {0, 5, 8, 1});
		// Right-front side
		faces.add(new int[] {8, 7, 6, 1});
		// Head
		faces.add(new int[] {7, 10, 9, 6});
		// Left-front side
		faces.add(new int[] {10, 11, 2, 9});
		// Left-back side
		faces.add(new int[] {11, 4, 3, 2});
		// Back side
		faces.add(new int[] {5, 0, 3, 4});
		// Bottom
		faces.add(new int[] {5, 4, 11, 10, 7, 8});
		// Front
		faces.add(new int[] {2, 1, 6, 9});
		
		Normal.CalcPerVertex(vertices, faces);
		
		init(gl);
	}
	
	public void init(GL2 gl)
	{
		gl.glNewList(Main.displayList + Main.Displays.HeliBody.ordinal(), GL2.GL_COMPILE);
		
		gl.glPushMatrix();
		
		Material.metal(gl);
		
		// -1 to remove drawing front. We need it to be drawn as glass
		for (int i = 0; i < faces.size() - 1; i++)
		{
			int type = faces.get(i).length == 4 ? GL2.GL_QUADS : GL2.GL_POLYGON;
			gl.glBegin(type);
			
			for (int vertexNo : faces.get(i))
			{
				Vertex v = vertices.get(vertexNo);
				Vector vN = v.getNormal();
				Vector vP = v.getPosition();
				
				gl.glNormal3d(vN.x, vN.y, vN.z);
				gl.glVertex3d(vP.x, vP.y, vP.z);
				
			}
			
			gl.glEnd();
		}
		
		// Draw front glass
		Material.glass(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		
		int[] front = faces.get(faces.size() - 1);
		for (int i : front)
		{
			Vertex v = vertices.get(i);
			Vector vN = v.getNormal();
			Vector vP = v.getPosition();
			
			gl.glNormal3d(vN.x, vN.y, vN.z);
			gl.glVertex3d(vP.x, vP.y, vP.z);
		}
		
		gl.glEnd();

        gl.glPopMatrix();
        
        gl.glEndList();
	}
	
	@Override
	public void drawNode(GL2 gl) {
		gl.glCallList(Main.displayList + Main.Displays.HeliBody.ordinal());
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
