package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TreeNode;
import scene.Vertex;
import utils.ObjFile;
import utils.Utils;
import utils.Vector;

public class Tree extends TreeNode {
	private GL2 gl;
	private int displayListID;
	private Vector Position = new Vector();
	
	private ArrayList<Vertex> vertices;
	private ArrayList<int[]> faces;
	
	private double rotation = Utils.genRand(360, 0);
	
	public static enum TreeType {
		Palm("PalmTree"),
		Fir("FirTree");
		
		TreeType(String name)
		{
			this.name = name;
		}
		
		private String name;
		
		public String getName()
		{
			return name;
		}
	}

	public Tree(GL2 gl, TreeType type) {
		this.gl = gl;
		
		ObjFile obj = ObjFile.objectsMap.get(type.getName());
		vertices = obj.vertices;
		faces = obj.faces;
	}
	
	public void init()
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		// Randomly rotate the tree
		gl.glRotated(rotation, 0, 1, 0);
		
		Material.tree(gl);
		
		for (int[] face : faces)
		{
			int drawtype = (faces.size() == 3 ? GL2.GL_TRIANGLES : GL2.GL_QUADS);
			gl.glBegin(drawtype);
			
			for (int vIndex : face)
			{
				Vertex v = vertices.get(vIndex);
				Vector vN = v.getNormal();
				Vector vP = v.getPosition();
				gl.glNormal3d(vN.x, vN.y, vN.z);
				gl.glVertex3d(vP.x, vP.y, vP.z);
			}
			
			gl.glEnd();
		}
		
		gl.glEndList();
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}

	public Vector getPosition() {
		return Position;
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}

}
