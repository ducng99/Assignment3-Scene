package objects;

import com.jogamp.opengl.GL2;

import scene.TreeNode;
import utils.Utils;
import utils.Vector;

public class Building extends TreeNode {
	private GL2 gl;
	private Vector Position;
	private int levels = Utils.genRand(2, 7);
	private double wide = Utils.genRand(10, 5);
	
	public Building(GL2 gl) {
		this.gl = gl;
		
		
	}

	public Vector getPosition() {
		return Position;
	}

	public void setPosition(Vector position) {
		Position = position;
	}

	@Override
	public void drawNode(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glBegin(GL2.GL_QUADS);
		
		
		
		gl.glEnd();
		
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}
}
