package scene;

import java.util.LinkedList;

import com.jogamp.opengl.GL2;

public abstract class TreeNode {
	private LinkedList<TreeNode> children = new LinkedList<>();
	
	public void addChild(TreeNode child)
	{
		children.add(child);
	}
	
	public void draw(GL2 gl)
	{
		gl.glPushMatrix();
		
		transformNode(gl);
		
		drawNode(gl);
		
		for (TreeNode child : children)
		{
			child.draw(gl);
		}
		
		gl.glPopMatrix();
	}
	
	public abstract void drawNode(GL2 gl);
	
	public abstract void transformNode(GL2 gl);
}
