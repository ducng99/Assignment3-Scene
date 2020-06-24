package car;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TreeNode;
import scene.Vertex;
import utils.Normal;
import utils.Vector;

public class Body extends TreeNode {
	private GL2 gl;
	private Vector Position = new Vector();
	
	// Length of parts
	private double side = 1;
	private double hood = side * 2.2;
	private double trunk = side;
	private double height = side * 1.5;
	private double frontGlass = 0.3;
	private double backGlass = 0.2;
	
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<int[]> faces = new ArrayList<>();
	
	private int displayListID;

	public Body(GL2 gl) {
		this.gl = gl;
		
		// Top
		vertices.add(new Vertex(new Vector(side * 0.8, height, side * 0.8)));						//0
		vertices.add(new Vertex(new Vector(side * 0.8, height, -side * 0.8)));						//1
		vertices.add(new Vertex(new Vector(-side * 0.8, height, -side * 0.8)));						//2
		vertices.add(new Vertex(new Vector(-side * 0.8, height, side * 0.8)));						//3
		// Hood
		vertices.add(new Vertex(new Vector(side, height * 0.6, side + frontGlass + hood)));			//4
		vertices.add(new Vertex(new Vector(side, height * 0.6, side + frontGlass)));				//5
		vertices.add(new Vertex(new Vector(-side, height * 0.6, side + frontGlass)));				//6
		vertices.add(new Vertex(new Vector(-side, height * 0.6, side + frontGlass + hood)));		//7
		// Front
		vertices.add(new Vertex(new Vector(side, 0, side + frontGlass + hood)));					//8
		vertices.add(new Vertex(new Vector(-side, 0, side + frontGlass + hood)));					//9
		// Trunk
		vertices.add(new Vertex(new Vector(side, height * 0.6, -side - backGlass)));				//10
		vertices.add(new Vertex(new Vector(side, height * 0.6, -side - backGlass - trunk)));		//11
		vertices.add(new Vertex(new Vector(-side, height * 0.6, -side - backGlass - trunk)));		//12
		vertices.add(new Vertex(new Vector(-side, height * 0.6, -side - backGlass)));				//13
		// Back
		vertices.add(new Vertex(new Vector(side, 0, -side - backGlass - trunk)));					//14
		vertices.add(new Vertex(new Vector(-side, 0, -side - backGlass - trunk)));					//15
		
		// Top
		faces.add(new int[] {0, 1, 2, 3});
		// Hood
		faces.add(new int[] {4, 5, 6, 7});
		// Front
		faces.add(new int[] {8, 4, 7, 9});
		// Trunk
		faces.add(new int[] {10, 11, 12, 13});
		// Back
		faces.add(new int[] {14, 15, 12, 11});
		// Bottom
		faces.add(new int[] {8, 9, 15, 14});
		// Left side
		faces.add(new int[] {8, 14, 11, 4});
		// Right side
		faces.add(new int[] {9, 7, 12, 15});
		// Front glass
		faces.add(new int[] {0, 3, 6, 5});
		// Back glass
		faces.add(new int[] {1, 10, 13, 2});
		// Left window
		faces.add(new int[] {0, 5, 10, 1});
		// Right window
		faces.add(new int[] {3, 2, 13, 6});
		
		Normal.CalcPerVertex(vertices, faces);
		
		init();
	}
	
	private void init()
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		Material.metal(gl);
		
		// Do not draw window
		for (int i = 0; i < faces.size() - 4; i++)
		{
			gl.glBegin(GL2.GL_POLYGON);
			
			for (int vIndex : faces.get(i))
			{
				Vertex vertex = vertices.get(vIndex);
				Vector vN = vertex.getNormal();
				Vector vP = vertex.getPosition().Multiply(new Vector(0.99, 1, 0.99));
				
				gl.glNormal3dv(vN.ToArray(), 0);
				gl.glVertex3dv(vP.ToArray(), 0);
			}
			
			gl.glEnd();
		}
		
		for (int i = faces.size() - 4; i < faces.size(); i++)
		{
			Material.glass(gl);
			gl.glBegin(GL2.GL_POLYGON);
			
			for (int vIndex : faces.get(i))
			{
				Vertex vertex = vertices.get(vIndex);
				Vector vN = vertex.getNormal();
				Vector vP = vertex.getPosition();
				
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
	
	public void setPosition(Vector pos)
	{
		Position = pos;
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}

}
