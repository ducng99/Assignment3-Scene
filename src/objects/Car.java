package objects;

import com.jogamp.opengl.GL2;

import car.*;
import utils.Vector;

public class Car {
	private GL2 gl;
	private Vector Position;
	private double direction;
	
	private Body body;

	public Car(GL2 gl) {
		this.gl = gl;
		
		body = new Body(gl);
		body.setPosition(new Vector());
	}
	
	public void draw()
	{
		gl.glPushMatrix();
		
		gl.glTranslated(Position.x, Position.y, Position.z);
		gl.glRotated(-direction, 0, 1, 0);
		
		body.draw(gl);
		
		gl.glPopMatrix();
	}

	public Vector getPosition() {
		return Position;
	}

	public void setPosition(Vector position) {
		Position = position;
	}

}
