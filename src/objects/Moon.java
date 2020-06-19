package objects;

import com.jogamp.opengl.GL2;

import scene.TreeNode;
import utils.Vector;

public class Moon extends TreeNode {
	private Vector Position = Vector.Zero;

	public Moon() {
		// TODO Auto-generated constructor stub
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

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		
		
		gl.glPopMatrix();
	}

}
