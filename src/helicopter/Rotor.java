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
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
		faces.add(new int[] {});
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glRotated(rotateAngle, rotateAxis.x, rotateAxis.y, rotateAxis.z);
		
		// Blades
		engine.spinBlades(gl);
		
		Material.matte(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		
		Vector normal;
		ArrayList<Vector> points = new ArrayList<>();
		
		// 1st blade - bottom
		
		points.add(new Vector(bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, 0, -bladeLength / 35.0));
		points.add(new Vector(bladeLength, 0, -bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(normal.x, -normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// 1st blade - top
		points.add(new Vector(bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		points.add(new Vector(bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// 1st blade - long side 1
		points.add(new Vector(bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		points.add(new Vector(bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);

		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// 1st blade - long side 2
		points.add(new Vector(bladeLength, 0, -bladeLength / 35.0));
		points.add(new Vector(-bladeLength, 0, -bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		points.add(new Vector(bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(normal.x, normal.y, -normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// 1st blade - short side 1
		points.add(new Vector(bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		points.add(new Vector(bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		points.add(new Vector(bladeLength, 0, -bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(-normal.x, normal.y, normal.z);
		
		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		// 1st blade - short side 2
		points.add(new Vector(-bladeLength, 0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, bladeLength / 35.0));
		points.add(new Vector(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0));
		points.add(new Vector(-bladeLength, 0, -bladeLength / 35.0));
		
		normal = Normal.CalcPolygon(points);
		gl.glNormal3d(normal.x, normal.y, normal.z);

		gl.glVertex3dv(points.get(0).ToArray(), 0);
		gl.glVertex3dv(points.get(1).ToArray(), 0);
		gl.glVertex3dv(points.get(2).ToArray(), 0);
		gl.glVertex3dv(points.get(3).ToArray(), 0);
		points.clear();
		
		if (numBlades == 2)
		{
			// 2nd blade - bottom
			points.add(new Vector(bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, 0, -bladeLength));
			points.add(new Vector(bladeLength / 35.0, 0, -bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(normal.x, -normal.y, normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();

			// 2nd blade - top
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(normal.x, normal.y, normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();
			
			// 2nd blade - long side 1
			points.add(new Vector(bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			points.add(new Vector(bladeLength / 35.0, 0, -bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(-normal.x, normal.y, normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();
			
			// 2nd blade - long side 2
			points.add(new Vector(-bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			points.add(new Vector(-bladeLength / 35.0, 0, -bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(normal.x, normal.y, normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();
			
			// 2nd blade - short side 1
			points.add(new Vector(bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, 0, bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(normal.x, normal.y, normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();
			
			// 2nd blade - short side 2
			points.add(new Vector(bladeLength / 35.0, 0, -bladeLength));
			points.add(new Vector(-bladeLength / 35.0, 0, -bladeLength));
			points.add(new Vector(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			points.add(new Vector(bladeLength / 35.0, bladeLength / 70.0, -bladeLength));
			
			normal = Normal.CalcPolygon(points);
			gl.glNormal3d(normal.x, normal.y, -normal.z);

			gl.glVertex3dv(points.get(0).ToArray(), 0);
			gl.glVertex3dv(points.get(1).ToArray(), 0);
			gl.glVertex3dv(points.get(2).ToArray(), 0);
			gl.glVertex3dv(points.get(3).ToArray(), 0);
			points.clear();
		}
		
		gl.glEnd();
		
		gl.glPopMatrix();
	}

}
