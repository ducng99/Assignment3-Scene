package helicopter;

import com.jogamp.opengl.GL2;

import utils.Vector;

public class Motor extends HeliPart {
	public final Engine engine = new Engine();
	private int numBlades = 2;
	private double bladeLength = 7;
	private double rotateAngle = 0;
	private Vector rotateAxis = Vector.Zero;
	
	public Motor(int numBlades, double length)
	{
		if (numBlades == 1 || numBlades == 2)
			this.numBlades = numBlades;
		
		if (length > 0)
			bladeLength = length;
	}
	
	public Motor(int numBlades, double length, double rotateAngle, Vector rotateAxis)
	{
		this(numBlades, length);
		this.rotateAngle = rotateAngle;
		this.rotateAxis = rotateAxis;
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glRotated(rotateAngle, rotateAxis.x, rotateAxis.y, rotateAxis.z);
		
		// Blades
		engine.spinBlades(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		
		// 1st blade - bottom
		gl.glVertex3d(bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, 0, -bladeLength / 35.0);
		gl.glVertex3d(bladeLength, 0, -bladeLength / 35.0);
		
		// 1st blade - top
		gl.glVertex3d(bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		gl.glVertex3d(bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		
		// 1st blade - long side 1
		gl.glVertex3d(bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		gl.glVertex3d(bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		
		// 1st blade - long side 2
		gl.glVertex3d(bladeLength, 0, -bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, 0, -bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		gl.glVertex3d(bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		
		// 1st blade - short side 1
		gl.glVertex3d(bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		gl.glVertex3d(bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		gl.glVertex3d(bladeLength, 0, -bladeLength / 35.0);
		
		// 1st blade - short side 2
		gl.glVertex3d(-bladeLength, 0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, bladeLength / 70.0, -bladeLength / 35.0);
		gl.glVertex3d(-bladeLength, 0, -bladeLength / 35.0);
		
		if (numBlades == 2)
		{
			// 2nd blade - bottom
			gl.glVertex3d(bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, 0, -bladeLength);
			gl.glVertex3d(bladeLength / 35.0, 0, -bladeLength);

			// 2nd blade - top
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
			
			// 2nd blade - long side 1
			gl.glVertex3d(bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
			gl.glVertex3d(bladeLength / 35.0, 0, -bladeLength);
			
			// 2nd blade - long side 2
			gl.glVertex3d(-bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, 0, -bladeLength);
			
			// 2nd blade - short side 1
			gl.glVertex3d(bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, 0, bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, bladeLength);
			
			// 2nd blade - short side 2
			gl.glVertex3d(bladeLength / 35.0, 0, -bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, 0, -bladeLength);
			gl.glVertex3d(-bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
			gl.glVertex3d(bladeLength / 35.0, bladeLength / 70.0, -bladeLength);
		}
		
		gl.glEnd();
		
		gl.glPopMatrix();
	}

}
