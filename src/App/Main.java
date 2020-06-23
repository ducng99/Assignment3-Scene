package App;
import scene.Camera;
import scene.Fog;
import scene.LightSource;
import scene.TextureControl;
import utils.ObjFile;
import utils.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import objects.*;

/**
 * Main class for the helicopter application
 * 
 * @author Duc Nguyen
 *
 */
public class Main implements GLEventListener, KeyListener {
	// Key pressed events queue
	private final LinkedBlockingQueue<KeyEvent> keyPressedEventsQ = new LinkedBlockingQueue<>();
	// Key released events queue
	private final LinkedBlockingQueue<KeyEvent> keyReleasedEventsQ = new LinkedBlockingQueue<>();
	
	private final static double WIDTH = 200;
	private final static double HEIGHT = 200;
	
	public static Camera camera;
	public static LightSource skyLighting;
	public static SkyBox sky;
	private FlatBase flatBase;
	public static Terrain terrain;
	private Origin origin;
	private Helicopter helicopter;
	private Road road;
	private int cameraMode = 0;	// TPP = 0, Free look = 1, FPP = 2
	
	private JLabel loadingLabel = new JLabel("Loading...");
	private static JFrame frame;
	
	private static long tmpTime;

	public static void main(String[] args) {
		frame = new JFrame("Assignment 3 - Scene");
		GLCanvas canvas = new GLCanvas();
		Main app = new Main();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		
		app.loadingLabel.setFont(new Font("Courier New", Font.BOLD, 24));
		app.loadingLabel.setSize(200, 30);
		app.loadingLabel.setBackground(Color.white);
		app.loadingLabel.setOpaque(true);
		app.loadingLabel.setLocation(540, 400);
		frame.add(app.loadingLabel);

		frame.add(canvas);
		frame.setSize(1280, 960);
		final Animator animator = new Animator(canvas);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		animator.start();
        canvas.requestFocus();
		
		System.out.println("Key mapping:\n"
				+ "--------------------------------------------\n" 
				+ "UP/DOWN ARROWS: Increase or decrease altitude\n"
				+ "LEFT/RIGHT ARROWS: Turn left or right\n"
				+ "W/S: Move forward or backward\n"
				+ "A/D: Strafe left or right\n"
				+ "L: Change flatBase draw mode\n"
				+ "K: Toggle camera mode (0 = Third person, 1 = Free look, 2 = First person)\n");
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		tmpTime = System.currentTimeMillis();
		
		TextureControl.gl = gl;
		TextureControl.importTextures();
		ObjFile.importObjects();
		
		// Enable VSync
		gl.setSwapInterval(1);
		// Setup the drawing area and shading mode
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_LIGHTING);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glClearColor(0.05f, 0.05f, 0.05f, 1.f);

		skyLighting = new LightSource(gl, LightSource.LightType.Directional);
		skyLighting.setPosition(new Vector(0.8, 1, 1));	// Based on the texture
        
        Fog.setupFog(gl);

		camera = new Camera();
		sky = new SkyBox(gl);
		terrain = new Terrain(gl, WIDTH, HEIGHT);
		flatBase = new FlatBase(gl, WIDTH, HEIGHT);
		origin = new Origin();
		road = new Road(gl, new Vector(-20, 2, -HEIGHT), new Vector(-18, 2, HEIGHT - 1));
		helicopter = new Helicopter(gl);
        
        // Setup all available lights
		for (LightSource light : LightSource.lights)
    	{
			light.setupLighting();
        }
		
		System.out.println("Load time: " + (System.currentTimeMillis() - tmpTime) + "ms");
		frame.remove(loadingLabel);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		// Handle key pressed events in queue
		while (keyPressedEventsQ.size() > 0)
		{
			handlePressedKeyEvents();
		}
		
		// Handle key released events in queue
		while (keyReleasedEventsQ.size() > 0)
		{
			handleReleasedKeyEvents();
		}

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
		switch (cameraMode)
		{
			case 0:
	        	camera.setEyePos(getCameraPos(25, 10));
	        	camera.setLookAt(helicopter.Position);
	        	break;
			case 2:
				camera.setEyePos(getCameraPos(-0.8, 2.9));
				camera.setLookAt(getCameraPos(-5, 2));
				break;
			case 1:
			default:
				break;
		}

        gl.glPushMatrix();
		camera.draw(gl);

		for (LightSource light : LightSource.lights)
    	{
			light.draw();
        }

		sky.draw();
		terrain.draw();
		flatBase.draw();
		origin.draw(gl);
		road.draw(gl);
		
		// Draw last for FPP to work
		helicopter.draw();
		
		gl.glPopMatrix();

		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}
    
    /**
     * Calculate camera position
     * @return {@link Vector} position
     */
    private Vector getCameraPos(double distance, double height)
    {
    	double rad = Math.toRadians(helicopter.direction);

    	double xOffset = distance * Math.sin(rad);
    	double zOffset = -distance * Math.cos(rad);
    	
    	return helicopter.Position.Offset(xOffset, height, zOffset);
    }
    
    public static int genDisplayList(GL2 gl)
    {
    	return gl.glGenLists(1);
    }
	
	private void handlePressedKeyEvents()
	{
		try
		{
			KeyEvent event = keyPressedEventsQ.take();
			
			switch(event.getKeyCode())
			{
				case KeyEvent.VK_L:
					// Change flatBase draw mode
					flatBase.toggleDrawMode();
					// Change terrain draw mode
					terrain.toggleDrawMode();
					break;
				case KeyEvent.VK_W:
					helicopter.isForward = true;
					break;
				case KeyEvent.VK_S:
					helicopter.isBackward = true;
					break;
				case KeyEvent.VK_A:
					helicopter.isLeft = true;
					break;
				case KeyEvent.VK_D:
					helicopter.isRight = true;
					break;
				case KeyEvent.VK_UP:
					helicopter.isUp = true;
					break;
				case KeyEvent.VK_DOWN:
					helicopter.isDown = true;
					break;
				case KeyEvent.VK_LEFT:
					helicopter.isLookLeft = true;
					break;
				case KeyEvent.VK_RIGHT:
					helicopter.isLookRight = true;
					break;
				case KeyEvent.VK_K:
					cameraMode = ++cameraMode % 3;
					break;
				default:
					break;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void handleReleasedKeyEvents()
	{
		try
		{
			KeyEvent event = keyReleasedEventsQ.take();
			
			switch(event.getKeyCode())
			{
				case KeyEvent.VK_W:
					helicopter.isForward = false;
					break;
				case KeyEvent.VK_S:
					helicopter.isBackward = false;
					break;
				case KeyEvent.VK_A:
					helicopter.isLeft = false;
					break;
				case KeyEvent.VK_D:
					helicopter.isRight = false;
					break;
				case KeyEvent.VK_UP:
					helicopter.isUp = false;
					break;
				case KeyEvent.VK_DOWN:
					helicopter.isDown = false;
					break;
				case KeyEvent.VK_LEFT:
					helicopter.isLookLeft = false;
					break;
				case KeyEvent.VK_RIGHT:
					helicopter.isLookRight = false;
					break;
				default:
					break;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		try {
			keyPressedEventsQ.put(arg0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		try {
			keyReleasedEventsQ.put(arg0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
