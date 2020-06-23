package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;

import App.Main;
import scene.Material;
import scene.TextureControl;
import scene.Vertex;
import utils.Normal;
import utils.Utils;
import utils.Vector;

/**
 * Handle terrain of the scene. Reads heightmap from file then draw it on the scene
 * @author Duc Nguyen
 *
 */
public class Terrain {
	private GL2 gl;
	private int displayListID;
	private boolean isFilled = true;

	private double[][] heightMap = null;
    private double width;
    private double height;
    
    private int imgWidth;
    private int imgHeight;
    
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<int[]> faces = new ArrayList<>();
    
    private final static double heightOffset = -13;

    public Terrain(GL2 gl, double width, double height) 
    {
    	this.gl = gl;
    	this.width = width;
    	this.height = height;
    	
        try 
        {
            BufferedImage img = ImageIO.read(new File("./others/heightmap/hm.png"));
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            
            heightMap = new double[imgWidth][imgHeight];
            
            for (int y = 0; y < imgHeight; y++)
            {
                for (int x = 0; x < imgWidth; x++)
                {
                    int rgb = img.getRGB(x, y);
                    int grey = rgb & 255;
                    heightMap[y][x] = grey / 10.0;
                }
            }
        }
        catch (IOException e) {
        	System.out.println("Cannot load heightmap image!");
        	throw new RuntimeException(e);
        }
        
        // Adding all points to array of vertices
        for (int y = 0; y < heightMap.length; y++)
    	{
    		for (int x = 0; x < heightMap[y].length; x++)
    		{
    			vertices.add(new Vertex(new Vector((double)x / imgWidth * (width * 2) - width, heightMap[y][x], (double)y / imgHeight * (height * 2) - height)));
    		}
    	}
        
        // Adding faces
        for (int y = 0; y < heightMap.length; y++)
    	{
    		for (int x = 0; x < heightMap[y].length; x++)
    		{
    			if (x < heightMap[y].length - 1 && y < heightMap.length - 1)
    			{
    				int[] face = new int[] { heightMap.length * y + x, heightMap.length * (y + 1) + x, heightMap.length * y + x + 1};
    				faces.add(face);
    			}
    			
    			if (x > 0 && y > 0)
    			{
    				int[] face = new int[] { heightMap.length * y + x, heightMap.length * (y - 1) + x, heightMap.length * y + x - 1 };
    				faces.add(face);
    			}
    		}
    	}
        
        Normal.CalcPerVertex(vertices, faces);
        
        init();
    }

    public void init()
    {
    	displayListID = Main.genDisplayList(gl);
    	
    	gl.glNewList(displayListID, GL2.GL_COMPILE);
    	
    	gl.glTranslated(0, heightOffset, 0);	// Move it down so we can have space for ocean

    	if (!isFilled)
		{
    		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		}

    	Material.ground(gl);
    	TextureControl.setupTexture(gl, "FlatBase");
    	
    	gl.glBegin(GL2.GL_TRIANGLES);
    	
    	for (int[] face : faces)
    	{
    		for (int vertexNo : face)
    		{
    			Vertex v = vertices.get(vertexNo);
    			Vector vN = v.getNormal();
    			Vector vP = v.getPosition();
    			
    			gl.glNormal3d(vN.x, vN.y, vN.z);
    			gl.glTexCoord2d(vP.x + width, height * 2 - vP.z);
    			gl.glVertex3d(vP.x, vP.y, vP.z);
    		}
    	}
    	
    	gl.glEnd();
    	
    	TextureControl.disableTexture(gl, "FlatBase");
    	
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

    	gl.glEndList();
    }
    
    public void draw()
    {
    	gl.glPushMatrix();
    	gl.glCallList(displayListID);
		gl.glPopMatrix();
    }
	
	/**
	 * Toggle between filled or wire-frame draw mode
	 */
	public void toggleDrawMode()
	{
		isFilled = !isFilled;
		init();
	}
	
	/**
	 * Get the height at given position
	 * @param point - a {@link Vector} object
	 * @return height as a double
	 */
	public double getHeightAt(Vector p)
	{
		double height = 0.1;	// Offset so other object won't overlap
		
		for (int[] face : faces)
		{
			Vector p0 = vertices.get(face[0]).getPosition();
			Vector p1 = vertices.get(face[1]).getPosition();
			Vector p2 = vertices.get(face[2]).getPosition();
			
			double area = Utils.areaTri(p0, p1, p2);
			double area1 = Utils.areaTri(p, p0, p1);
			double area2 = Utils.areaTri(p, p0, p2);
			double area3 = Utils.areaTri(p, p1, p2);
			
			if (Math.floor(area * 100000) == Math.floor((area1 + area2 + area3) * 100000))
			{
				height += (p0.y + p1.y + p2.y) / 3 + heightOffset;
				return height;
			}
		}
		
		return height;
	}
}