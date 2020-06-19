package objects;

import com.jogamp.opengl.GL2;

public class Ground {
	private boolean isFilled = true;
	
	private double width = 50;
	private double height = 50;

	public Ground() {
		
	}

	public void draw(GL2 gl)
	{
		
		if (isFilled)
		{
			gl.glColor3d(0, 0.2, 0);
		}
		else
		{
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl.glColor3d(0, 0, 0);
		}
		
		gl.glBegin(GL2.GL_QUADS);
		
		for (double iHeight = -height; iHeight < height; iHeight++)
		{
			for (double iWidth = -width; iWidth < width; iWidth++)
			{
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(iWidth, 0, iHeight);
				gl.glVertex3d(iWidth, 0, iHeight + 1);
				gl.glVertex3d(iWidth + 1, 0, iHeight + 1);
				gl.glVertex3d(iWidth + 1, 0, iHeight);
			}
		}
		
		gl.glEnd();
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
	}
	
	/**
	 * Toggle between filled or wire-frame draw mode
	 */
	public void toggleDrawMode()
	{
		isFilled = !isFilled;
	}
}
