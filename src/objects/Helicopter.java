package objects;

import com.jogamp.opengl.GL2;

import App.Main;
import helicopter.Body;
import helicopter.Bottom;
import helicopter.Tail;
import helicopter.Top;
import utils.Vector;

/**
 * Helicopter main class
 * @author Duc Nguyen
 *
 */
public class Helicopter {
	private GL2 gl;
	
	private final Body body;
	private final Top top;
	private final Tail tail;
	private final Bottom bottom;
	
	public Vector Position;
	public double direction;
	private double speed = 0.2;
	
	public boolean isLeft = false, isRight = false, isForward = false, isBackward = false, isUp = false, isDown = false, isLookLeft = false, isLookRight = false;
	private long prevTick = System.currentTimeMillis();
    
    public Helicopter(GL2 gl) {
    	this.gl = gl;
        Position = new Vector();
        direction = 0.0;
        
        body = new Body(gl);
        
    	top = new Top();
    	top.setPosition(new Vector(0, body.height));
    	
    	tail = new Tail();
    	tail.setPosition(new Vector(0, body.height, body.back));
    	
    	bottom = new Bottom(gl);
    	bottom.setPosition(new Vector());
    	
    	// Leave space for bottom
        body.setPosition(new Vector(0, bottom.height + bottom.thickness * 1.2, 0));
    	body.addChild(top);
    	body.addChild(tail);
    	body.addChild(bottom);
    }
    
    public void draw()
    {
    	gl.glPushMatrix();
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
        gl.glPopMatrix();
    }
    
    public void setPosition(Vector pos)
    {
    	this.Position = pos;
    }

    /**
     * Calculate offsets on x-axis and z-axis based on direction and move left
     */
    private void strafeLeft()
    {
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted())
    	{
    		double rad = Math.toRadians(direction);

	    	double zOffset = speed * Math.sin(rad);
	    	double xOffset = speed * Math.cos(rad);
	    	
	    	// Get current position height of terrain at the target point
	    	double terrainHeight = Main.terrain.getHeightAt(Position.Offset(xOffset, 0, zOffset));
	    	double buildingHeight = Building.getBuildingHeight(Position.Offset(xOffset, 0, zOffset));
	    	
	    	double minHeight = buildingHeight > terrainHeight ? buildingHeight : terrainHeight;
	    	
	    	if (Position.y > minHeight)
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
	
	    	double zOffset = -speed * Math.sin(rad);
	    	double xOffset = -speed * Math.cos(rad);
	    	
	    	// Get current position height of terrain at the target point
	    	double terrainHeight = Main.terrain.getHeightAt(Position.Offset(xOffset, 0, zOffset));
	    	double buildingHeight = Building.getBuildingHeight(Position.Offset(xOffset, 0, zOffset));
	    	
	    	double minHeight = buildingHeight > terrainHeight ? buildingHeight : terrainHeight;
	    	
	    	if (Position.y > minHeight)
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
	
	    	double zOffset = speed * Math.cos(rad);
	    	double xOffset = -speed * Math.sin(rad);
	    	
	    	// Get current position height of terrain at the target point
	    	double terrainHeight = Main.terrain.getHeightAt(Position.Offset(xOffset, 0, zOffset));
	    	double buildingHeight = Building.getBuildingHeight(Position.Offset(xOffset, 0, zOffset));
	    	
	    	double minHeight = buildingHeight > terrainHeight ? buildingHeight : terrainHeight;
	    	
	    	if (Position.y > minHeight)
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
	
	    	double zOffset = -speed * Math.cos(rad);
	    	double xOffset = speed * Math.sin(rad);
	    	
	    	// Get current position height of terrain at the target point
	    	double terrainHeight = Main.terrain.getHeightAt(Position.Offset(xOffset, 0, zOffset));
	    	double buildingHeight = Building.getBuildingHeight(Position.Offset(xOffset, 0, zOffset));
	    	
	    	double minHeight = buildingHeight > terrainHeight ? buildingHeight : terrainHeight;
	    	
	    	if (Position.y > minHeight)
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
    	
    	if (top.rotor.engine.engineStarted() && tail.rotor.engine.engineStarted() && Position.y < 40)
    	{
    		Position = Position.Offset(0, speed);
    	}
    }
    
    private void goDown()
    {
    	// Get current position height of terrain
    	double terrainHeight = Main.terrain.getHeightAt(Position.Offset(0, -speed));
    	double buildingHeight = Building.getBuildingHeight(Position.Offset(0, -speed));
    	
    	double minHeight = buildingHeight > terrainHeight ? buildingHeight : terrainHeight;

    	if (Position.y <= minHeight)
    	{
    		if (!top.rotor.engine.engineStopped())
    			top.rotor.engine.stopEngine();
    		
    		if (!tail.rotor.engine.engineStopped())
    			tail.rotor.engine.stopEngine();
    	}
    	else if (Position.y > minHeight)
    	{
    		Position = Position.Offset(0, -speed);
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
