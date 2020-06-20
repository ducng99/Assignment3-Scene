package App;
import scene.Camera;
import scene.Lighting;
import scene.TextureControl;
import utils.Vector;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.LinkedBlockingQueue;

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
	
	private final static double WIDTH = 100;
	private final static double HEIGHT = 100;
	
	public static Camera camera;
	public static Lighting lighting;
	private FlatBase flatBase;
	private Terrain terrain;
	private Origin origin;
	private Moon moon;
	private Helicopter helicopter;
	private boolean lockCamera = true;
	
	public static int displayList;
	
	// Manage display list easier
	public static enum Displays {
		Moon, FlatBase, Terrain
	}

	public static void main(String[] args) {
		Frame frame = new Frame("Assignment 3 - Scene");
		GLCanvas canvas = new GLCanvas();
		Main app = new Main();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);

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
				+ "K: Toggle camera follow helicopter\n");
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		TextureControl.gl = gl;
		TextureControl.importTextures();
		
		// Enable VSync
		gl.setSwapInterval(1);
		// Setup the drawing area and shading mode
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glClearColor(0.05f, 0.05f, 0.05f, 1.f);
		
		float ambientLight[] = { 0.2f, 0.2f, 0.2f, 1f }; // no ambient
		float diffuseLight[] = { 0.6f, 0.6f, 0.6f, 1f }; // white light for diffuse
		float specularLight[] = { 0.2f, 0.2f, 0.2f, 1f }; // white light for specular
		
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
        
        float globalAmbientLight[] = { 0.4f, 0.4f, 0.4f, 1 };

		// set the global ambient light level
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);
        
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        
        displayList = gl.glGenLists(Displays.values().length);

		camera = new Camera();
		terrain = new Terrain(gl, WIDTH, HEIGHT);
		flatBase = new FlatBase(gl, WIDTH, HEIGHT);
		origin = new Origin();
		moon = new Moon(gl);
		moon.setPosition(new Vector(lighting.getLightPos()));
		helicopter = new Helicopter();
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
        
        if (lockCamera)
    	{
        	camera.setEyePos(getCameraPos());
        	camera.setLookAt(helicopter.Position);
    	}

		camera.draw(gl);

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lighting.getLightPos(), 0);
		
		flatBase.draw();
		terrain.draw();
		origin.draw(gl);
		moon.draw(gl);
		helicopter.draw(gl);

		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}
    
    /**
     * Calculate camera position
     * @return {@link Vector} position
     */
    private Vector getCameraPos()
    {
    	double rad = Math.toRadians(helicopter.direction);

    	double zOffset = -20 * Math.cos(rad);
    	double xOffset = 20 * Math.sin(rad);
    	
    	return helicopter.Position.Offset(xOffset, 8, zOffset);
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
					lockCamera = !lockCamera;
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
