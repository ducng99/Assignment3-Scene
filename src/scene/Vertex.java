package scene;

import utils.Vector;

public class Vertex {
	private Vector Position;
	private Vector normal;

	public Vertex(Vector Position) {
		this.Position = Position;
	}

	public Vector getPosition() {
		return Position;
	}

	public Vector getNormal() {
		return normal;
	}

	public void setNormal(Vector normal)
	{
		this.normal = normal;
	}
}
