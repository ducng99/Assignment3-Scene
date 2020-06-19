package utils;

/**
 * Custom Vector class contains x, y, z variables for easy access
 * @author Duc Nguyen
 *
 */
public class Vector {
	public double x;
	public double y;
	public double z;
	
	/**
	 * Return new Vector with 0 on all axis
	 */
	public final static Vector Zero = new Vector();
	/**
	 *  Return new Vector with 1 on z axis, 0 on other axis
	 */
	public final static Vector forward = new Vector(0, 0, 1);
	/**
	 *  Return new Vector with -1 on z axis, 0 on other axis
	 */
	public final static Vector backward = new Vector(0, 0, -1);
	/**
	 * Return new Vector with 1 on x axis, 0 on other axis
	 */
	public final static Vector left = new Vector(1, 0, 0);
	/**
	 *  Return new Vector with -1 on z axis, 0 on other axis
	 */
	public final static Vector right = new Vector(-1, 0, 0);
	/**
	 *  Return new Vector with 1 on y axis, 0 on other axis
	 */
	public final static Vector up = new Vector(0, 1, 0);
	/**
	 *  Return new Vector with -1 on y axis, 0 on other axis
	 */
	public final static Vector down = new Vector(0, -1, 0);
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Vector(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Add this vector with vector a on all axis
	 * @param a
	 * @return a new {@link Vector} with added variables
	 */
	public Vector Offset(Vector a)
	{
		return new Vector(this.x + a.x, this.y + a.y, this.z + a.z);
	}
	
	/**
	 * Add x axis with a
	 * @param a
	 * @return a new {@link Vector} with added variable
	 */
	public Vector Offset(double a)
	{
		return new Vector(this.x + a, this.y, this.z);
	}
	
	/**
	 * Add x axis with a, y axis with b
	 * @param a
	 * @param b
	 * @return a new {@link Vector} with added variables
	 */
	public Vector Offset(double a, double b)
	{
		return new Vector(this.x + a, this.y + b, this.z);
	}
	
	/**
	 * Add x axis with a, y axis with b, z axis with c
	 * @param a
	 * @param b
	 * @param c
	 * @return a new {@link Vector} with added variables
	 */
	public Vector Offset(double a, double b, double c)
	{
		return new Vector(this.x + a, this.y + b, this.z + c);
	}
	
	/**
	 * Calculate cross of 2 vectors
	 * @param a
	 * @return a new {@link Vector}
	 */
	public Vector Cross(Vector a)
	{
		return new Vector(this.y * a.z - this.z * a.y, this.z * a.x - this.x * a.z, this.x * a.y - this.y * a.x);
	}
	
	/**
	 * Convert to a double array
	 * @return double array
	 */
	public double[] ToArray()
	{
		return new double[] {this.x, this.y, this.z};
	}
}
