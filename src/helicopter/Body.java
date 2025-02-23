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
public class Body extends TreeNode {
	private int displayListID;
	private Vector Position;
	public double height = 2.5;
	public double back = -2.5;
	public double side = 1.8;
	public double front = 6.0;
	
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<int[]> faces = new ArrayList<>();

	public Body(GL2 gl) {
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
		// Left-back side
		faces.add(new int[] {11, 4, 3, 2});
		// Back side
		faces.add(new int[] {5, 0, 3, 4});
		// Bottom
		faces.add(new int[] {5, 4, 11, 10, 7, 8});
		// Front
		faces.add(new int[] {2, 1, 6, 9});
		// Right-front window
		faces.add(new int[] {8, 7, 6, 1});
		// Left-front window
		faces.add(new int[] {10, 11, 2, 9});
		// Head window
		faces.add(new int[] {7, 10, 9, 6});
		
		Normal.CalcPerVertex(vertices, faces);
		
		init(gl);
	}
	
	public void init(GL2 gl)
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		Material.metal(gl);
		
		// Remove drawing glasses
		for (int i = 0; i < faces.size() - 4; i++)
		{
			gl.glBegin(GL2.GL_POLYGON);
			
			for (int vertexNo : faces.get(i))
			{
				Vertex v = vertices.get(vertexNo);
				Vector vN = v.getNormal();
				Vector vP = v.getPosition();
				
				gl.glNormal3dv(vN.ToArray(), 0);
				gl.glVertex3dv(vP.ToArray(), 0);
				
			}
			
			gl.glEnd();
		}
		
		// Draw glasses
		
		for (int i = faces.size() - 4; i < faces.size(); i++)
		{
			Material.glass(gl);
			gl.glBegin(GL2.GL_POLYGON);
			
			for (int vIndex : faces.get(i))
			{
				Vertex v = vertices.get(vIndex);
				Vector vN = v.getNormal();
				Vector vP = v.getPosition().Multiply(new Vector(0.99, 1, 0.99));
				
				gl.glNormal3dv(vN.ToArray(), 0);
				gl.glVertex3dv(vP.ToArray(), 0);
			}
			
			gl.glEnd();

			Material.metal(gl);
			gl.glLineWidth(3);
			gl.glBegin(GL2.GL_LINE_LOOP);
			
			for (int vIndex : faces.get(i))
			{
				Vertex vertex = vertices.get(vIndex);
				Vector vN = vertex.getNormal();
				Vector vP = vertex.getPosition();
				
				gl.glNormal3dv(vN.ToArray(), 0);
				gl.glVertex3dv(vP.ToArray(), 0);
			}
			
			gl.glEnd();
			gl.glLineWidth(1);
		}

        gl.glEndList();
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

    public void setPosition(Vector target)
    {
    	Position = target;
    }
    
    public Vector getPosition()
    {
    	return Position;
    }
}
