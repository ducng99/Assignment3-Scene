package helicopter;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import scene.TreeNode;
import utils.Vector;

public abstract class HeliPart extends TreeNode {
	protected Vector Position = Vector.Zero;
	protected GLU glu;
	protected GLUquadric quadric;
	
	public HeliPart()
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
	}

	@Override
	public void transformNode(GL2 gl)
	{
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
