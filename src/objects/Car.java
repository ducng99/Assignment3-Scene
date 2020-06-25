package objects;

import com.jogamp.opengl.GL2;

import App.Main;
import car.*;
import scene.TreeNode;
import utils.Vector;

public class Car extends TreeNode{
	private Vector Position;
	private double rad;
	private double pitch;
	
	private Vector start;
	private double distance;
	
	private Body body;
	private Wheel frontLeftWheel;
	private Wheel frontRightWheel;
	private Wheel backLeftWheel;
	private Wheel backRightWheel;
	
	private long prevTick = System.currentTimeMillis();

	public Car(GL2 gl, Vector start, double distance, double rad) {
		this.rad = rad;
		this.start = start;
		
		body = new Body(gl);
		this.distance = distance - (body.side + body.frontGlass + body.hood + body.side + body.backGlass + body.trunk);
		
		frontLeftWheel = new Wheel(gl);
		frontLeftWheel.setPosition(new Vector(body.side, 0, body.side + body.frontGlass + body.hood * 0.6));
		
		frontRightWheel = new Wheel(gl);
		frontRightWheel.setPosition(new Vector(-body.side, 0, body.side + body.frontGlass + body.hood * 0.6));
		
		backLeftWheel = new Wheel(gl);
		backLeftWheel.setPosition(new Vector(body.side, 0, -body.side - body.backGlass));
		
		backRightWheel = new Wheel(gl);
		backRightWheel.setPosition(new Vector(-body.side, 0, -body.side - body.backGlass));

		body.setPosition(new Vector(0, frontLeftWheel.radius));
		body.addChild(frontLeftWheel);
		body.addChild(frontRightWheel);
		body.addChild(backLeftWheel);
		body.addChild(backRightWheel);
	}
	
	/**
	 * Move the car with specified direction, return to start point when reaches end point
	 */
	private void move()
	{
		// Update every frame
		if (System.currentTimeMillis() - prevTick > 1000 / 60.0)
		{
	    	double frontHeight;
	    	double backHeight;
	    	
			// Move forward
			if (start.distanceTo(Position) >= distance)
			{
				Position = new Vector(start);
			}
	    	
	    	if (rad / Math.PI > 1)
	    	{
	    		frontHeight = Main.terrain.getHeightAt(Position.Offset(0, 0, body.side + body.frontGlass + body.hood));
	    		backHeight = Main.terrain.getHeightAt(Position.Offset(0, 0, -body.side - body.backGlass - body.trunk));
	    	}
	    	else
	    	{
	    		frontHeight = Main.terrain.getHeightAt(Position.Offset(0, 0, -body.side - body.frontGlass - body.hood));
	    		backHeight = Main.terrain.getHeightAt(Position.Offset(0, 0, body.side + body.backGlass + body.trunk));
	    	}
			
	    	double xOffset = 0.15 * Math.sin(rad);
	    	double zOffset = 0.15 * Math.cos(rad);
	    	
	    	Vector target = Position.Offset(xOffset, 0, zOffset);
	    	target.y = backHeight;
	    	Position = target;
	    	
	    	// Update pitch
	    	pitch = Math.toDegrees(Math.asin((frontHeight - backHeight) / (body.side + body.frontGlass + body.hood + body.side + body.backGlass + body.trunk)));
	    	
	    	prevTick = System.currentTimeMillis();
		}
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
		
		gl.glRotated(Math.toDegrees(rad), 0, 1, 0);
		gl.glRotated(pitch, 1, 0, 0);
		move();
		
		body.draw(gl);
		
		gl.glPopMatrix();
	}

	@Override
	public void transformNode(GL2 gl) {
		gl.glTranslated(Position.x, Position.y, Position.z);
	}

}
