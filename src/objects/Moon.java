package objects;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import App.Main;
import scene.Material;
import scene.TextureControl;
import scene.TreeNode;
import utils.Vector;

public class Moon extends TreeNode {
	private Vector Position = Vector.Zero;
	private double size;
	private GL2 gl;
	private GLU glu;
	private GLUquadric quadric;

	public Moon(GL2 gl) {
		this(gl, 1);
	}
	
	public Moon(GL2 gl, double size)
	{
		if (size > 0)
			this.size = size;
		
		this.gl = gl;
		
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		glu.gluQuadricTexture(quadric, true);
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
		
		init();
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
    
    private void init()
    {
    	gl.glNewList(Main.displayList + Main.Displays.Moon.ordinal(), GL2.GL_COMPILE);
    	
		gl.glPushMatrix();
		Material.moon(gl);
		TextureControl.setupTexture(gl, "Moon");

		glu.gluSphere(quadric, size, 30, 15);
		
		TextureControl.disableTexture(gl, "Moon");
		
		gl.glPopMatrix();
		
		gl.glEndList();
    }

	@Override
	public void drawNode(GL2 gl) {
		gl.glCallList(Main.displayList + Main.Displays.Moon.ordinal());
	}

}
