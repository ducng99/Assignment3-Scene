package helicopter;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import scene.Material;
import scene.Vertex;
import utils.Normal;
import utils.Vector;

/**
 * Control helicopter's rotors. Draw rotors and their rotation
 * @author Tom
 *
 */
public class Rotor extends HeliPart {
	public final Engine engine = new Engine();
	private int numBlades = 2;
	private double bladeLength = 7;
	private double rotateAngle = 0;
	private Vector rotateAxis = Vector.Zero;
	
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<int[]> faces = new ArrayList<>();
	
	public Rotor(int numBlades, double length)
	{
		if (numBlades == 1 || numBlades == 2)
			this.numBlades = numBlades;
		
		if (length > 0)
			bladeLength = length;
		
		init();
	}
	
	public Rotor(int numBlades, double length, double rotateAngle, Vector rotateAxis)
	{
		this(numBlades, length);
		this.rotateAngle = rotateAngle;
		this.rotateAxis = rotateAxis;
	}
	
	public void init()
	{
		vertices.add(new Vertex(new Vector(bladeLength, 0, bladeLength / 35.0)));						//0
		vertices.add(new Vertex(new Vector(-bladeLength, 0, bladeLength / 35.0)));						//1
		vertices.add(new Vertex(new Vector(-bladeLength, 0, -bladeLength / 35.0)));						//2
		vertices.add(new Vertex(new Vector(bladeLength, 0, -bladeLength / 35.0)));						//3
		vertices.add(new Vertex(new Vector(bladeLength, bladeLength / 70.0, bladeLength / 35.0)));		//4
		vertices.add(new Vertex(new Vector(-bladeLength, bladeLength / 70.0, bladeLength / 35.0)));		//5
		vertices.add(new Vertex(new Vector(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0)));	//6
		vertices.add(new Vertex(new Vector(bladeLength, bladeLength / 70.0, -bladeLength / 35.0)));		//7
		
		vertices.add(new Vertex(new Vector(bladeLength / 35.0, 0, bladeLength)));						//8
		vertices.add(new Vertex(new Vector(-bladeLength / 35.0, 0, bladeLength)));						//9
		vertices.add(new Vertex(new Vector(-bladeLength / 35.0, 0, -bladeLength)));						//10
		vertices.add(new Vertex(new Vector(bladeLength / 35.0, 0, -bladeLength)));						//11
		vertices.add(new Vertex(new Vector(bladeLength / 35.0, bladeLength / 70.0, bladeLength)));		//12
		vertices.add(new Vertex(new Vector(-bladeLength / 35.0, bladeLength / 70.0, bladeLength)));		//13
		vertices.add(new Vertex(new Vector(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength)));	//14
		vertices.add(new Vertex(new Vector(bladeLength / 35.0, bladeLength / 70.0, -bladeLength)));		//15
		
		// 1st blade - bottom
		faces.add(new int[] {0, 1, 2, 3});
		// 1st blade - top
		faces.add(new int[] {4, 7, 6, 5});
		// 1st blade - long side 1
		faces.add(new int[] {0, 4, 5, 1});
		// 1st blade - long side 2
		faces.add(new int[] {3, 2, 6, 7});
		// 1st blade - short side 1
		faces.add(new int[] {0, 3, 7, 4});
		// 1st blade - short side 2
		faces.add(new int[] {1, 5, 6, 2});

		// 2nd blade - bottom
		faces.add(new int[] {8, 9, 10, 11});
		// 2nd blade - top
		faces.add(new int[] {12, 15, 14, 13});
		// 2nd blade - long side 1
		faces.add(new int[] {8, 11, 15, 12});
		// 2nd blade - long side 2
		faces.add(new int[] {9, 13, 14, 10});
		// 2nd blade - short side 1
		faces.add(new int[] {8, 12, 13, 9});
		// 2nd blade - short side 2
		faces.add(new int[] {11, 10, 14, 15});
		
		Normal.CalcPerVertex(vertices, faces);
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glRotated(rotateAngle, rotateAxis.x, rotateAxis.y, rotateAxis.z);
		
		// Blades
		engine.spinBlades(gl);
		
		Material.matte(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		
		for (int i = 0; i < (numBlades == 2 ? faces.size() : faces.size() / 2); i++)
		{			
			for (int vertexNo : faces.get(i))
			{
				Vertex v = vertices.get(vertexNo);
				Vector vN = v.getNormal();
				Vector vP = v.getPosition();
				
				gl.glNormal3d(vN.x, vN.y, vN.z);
				gl.glVertex3d(vP.x, vP.y, vP.z);
			}
		}
		
		gl.glEnd();
		
		gl.glPopMatrix();
	}

}
