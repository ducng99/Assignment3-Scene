package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TextureControl;
import utils.Normal;
import utils.Vector;

/**
 * Base grid/water layer
 * @author Duc Nguyen
 *
 */
public class FlatBase {
	private double width = 50;
	private double height = 50;
	
	private GL2 gl;
	private int displayListID;

	public FlatBase(GL2 gl, double width, double height) {
		this.gl = gl;
		this.setHeight(height);
		this.setWidth(width);
		init();
	}
	
	private void init()
	{
		displayListID = Main.genDisplayList(gl);
		
		gl.glNewList(displayListID, GL2.GL_COMPILE);
		
		gl.glTranslated(0, 0.01, 0);	// Avoid having the same height with some part of the terrain, causes flickering
		
		Material.water(gl);
		TextureControl.setupTexture(gl, "Water");
		
		gl.glBegin(GL2.GL_QUADS);
		
		Vector normal;
		ArrayList<Vector> points = new ArrayList<>();
		
		for (double iHeight = -height; iHeight < height - 1; iHeight++)
		{
			for (double iWidth = -width; iWidth < width - 1; iWidth++)
			{
				points.add(new Vector(iWidth, 0, iHeight));
				points.add(new Vector(iWidth, 0, iHeight + 1));
				points.add(new Vector(iWidth + 1, 0, iHeight + 1));
				points.add(new Vector(iWidth + 1, 0, iHeight));
				
				normal = Normal.CalcPolygon(points);
				gl.glNormal3d(normal.x, normal.y, normal.z);

				gl.glTexCoord2d(0, 0);
				gl.glVertex3dv(points.get(0).ToArray(), 0);
				
				gl.glTexCoord2d(0, 1);
				gl.glVertex3dv(points.get(1).ToArray(), 0);
				
				gl.glTexCoord2d(1, 1);
				gl.glVertex3dv(points.get(2).ToArray(), 0);
				
				gl.glTexCoord2d(1, 0);
				gl.glVertex3dv(points.get(3).ToArray(), 0);
				
				points.clear();
			}
		}
		
		gl.glEnd();
		
		TextureControl.disableTexture(gl, "Water");
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

		gl.glEndList();
	}

	public void draw()
	{
		gl.glPushMatrix();
		gl.glCallList(displayListID);
		gl.glPopMatrix();
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	
}
