package objects;

import com.jogamp.opengl.GL2;
import helicopter.HeliBody;
import helicopter.HeliBottom;
import helicopter.HeliTail;
import helicopter.HeliTop;
import utils.Vector;

/**
 * Helicopter main class
 * @author Duc Nguyen
 *
 */
public class Helicopter {
	private GL2 gl;
	
	private final HeliBody body;
	private final HeliTop top;
	private final HeliTail tail;
	private final HeliBottom bottom;
	
	public Vector Position;
	public double direction;
	
	public boolean isLeft = false, isRight = false, isForward = false, isBackward = false, isUp = false, isDown = false, isLookLeft = false, isLookRight = false;
	private long prevTick = System.currentTimeMillis();
    
    public Helicopter(GL2 gl) {
    	this.gl = gl;
        Position = new Vector();
        direction = 0.0;
        
        body = new HeliBody(gl);
        body.setPosition(new Vector(0, .42, 0));
        
    	top = new HeliTop();
    	top.setPosition(new Vector(0, body.height));
    	
    	tail = new HeliTail();
    	tail.setPosition(new Vector(0, body.height, body.back));
    	
    	bottom = new HeliBottom(gl);
    	bottom.setPosition(new Vector(0, body.bottom));

    	body.addChild(top);
    	body.addChild(tail);
    	body.addChild(bottom);
    }
    
    public void draw()
    {
        gl.glTranslated(Position.x, Position.y, Position.z);
        
        // -direction because I love clockwise rotation
        gl.glRotated(-direction, 0, 1, 0);
        
        // Update every 16.67sec (60fps)
    	if (System.currentTimeMillis() - prevTick > 1000 / 60.0)
    	{
    		if (isLeft)
        		strafeLeft();
        	if (isRight)
        		strafeRight();
        	if (isForward)
        		goForward();
        	if (isBackward)
        		goBackward();
        	if (isUp)
        		goUp();
        	if (isDown)
        		goDown();
        	if (isLookLeft)
        		lookLeft();
        	if (isLookRight)
        		lookRight();
        	
        	prevTick = System.currentTimeMillis();
    	}

        body.draw(gl);
    }

    /**
     * Calculate offsets on x-axis and z-axis based on direction and move left
     */
    private void strafeLeft()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
    		double rad = Math.toRadians(direction);

	    	double zOffset = 0.1 * Math.sin(rad);
	    	double xOffset = 0.1 * Math.cos(rad);
	    	
	    	Position = Position.Offset(xOffset, 0, zOffset);
    	}
    }

    /**
     * Calculate offsets on x-axis and z-axis based on direction and move right
     */
    private void strafeRight()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
	    	double rad = Math.toRadians(direction);
	
	    	double zOffset = -0.1 * Math.sin(rad);
	    	double xOffset = -0.1 * Math.cos(rad);
	    	
	    	Position = Position.Offset(xOffset, 0, zOffset);
    	}
    }
    
    /**
     * Calculate offsets on x-axis and z-axis based on direction and move forward
     */
    private void goForward()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
	    	double rad = Math.toRadians(direction);
	
	    	double zOffset = 0.1 * Math.cos(rad);
	    	double xOffset = -0.1 * Math.sin(rad);
	    	
	    	Position = Position.Offset(xOffset, 0, zOffset);
    	}
    }
    
    /**
     * Calculate offsets on x-axis and z-axis based on direction and move backward
     */
    private void goBackward()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
	    	double rad = Math.toRadians(direction);
	
	    	double zOffset = -0.1 * Math.cos(rad);
	    	double xOffset = 0.1 * Math.sin(rad);
	    	
	    	Position = Position.Offset(xOffset, 0, zOffset);
    	}
    }
    
    /**
     * Check whether blades spin speed have reached maxed and take off
     */
    private void goUp()
    {
    	if (!top.rotor.engine.engineStarted())
    	{
    		top.rotor.engine.startEngine();
    	}
    	
    	if (!tail.rotor.engine.engineStarted())
    	{
    		tail.rotor.engine.startEngine();
    	}
    	
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
    		Position = Position.Offset(0, 0.1);
    	}
    }
    
    private void goDown()
    {
    	if (Position.y <= 0)
    	{
    		if (!top.rotor.engine.engineStopped())
    			top.rotor.engine.stopEngine();
    		
    		if (!tail.rotor.engine.engineStopped())
    			tail.rotor.engine.stopEngine();
    	}
    	
    	if (Position.y > 0)
    	{
    		Position = Position.Offset(0, -0.1);
    	}
    }
    
    private void lookLeft()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
	    	direction--;
	    	
	    	if (direction < 0)
	    		direction += 360;
    	}
    }
    
    private void lookRight()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
	    	direction++;
	    	
	    	if (direction >= 360)
	    		direction -= 360;
    	}
    }
}
