package utils;

import java.util.Random;

/**
 * Class contains common utilities 
 * @author Duc Nguyen
 *
 */
public class Utils {
	private static Random rand = new Random();
	
	/**
	 * Generate a random double between max (inclusive) and min (inclusive)
	 * @param max - a double
	 * @param min - a double
	 * @return a random double number
	 */
	public static double genRand(double max, double min)
	{
		return rand.nextDouble() * (max - min + 1) + min;
	}

	/**
	 * Generate a random integer between max (inclusive) and min (inclusive)
	 * @param max - an integer
	 * @param min - an integer
	 * @return a random integer number
	 */
	public static int genRand(int max, int min)
	{
		return rand.nextInt(max - min + 1) + min;
	}

	/**
	 * Generate a random float between max (inclusive) and min (inclusive)
	 * @param max - an float
	 * @param min - an float
	 * @return a random float number
	 */
	public static float genRand(float max, float min)
	{
		return rand.nextFloat() * (max - min + 1) + min;
	}
	
	/**
	 * Calculate the area of a triangle from 3 points
	 * @param a - {@link Vector} object
	 * @param b - {@link Vector} object
	 * @param c - {@link Vector} object
	 * @return the area as a double
	 */
	public static double areaTri(Vector a, Vector b, Vector c)
	{
		return Math.abs((a.x * (b.z - c.z) + b.x * (c.z - a.z) + c.x * (a.z - b.z)) / 2.0);
	}
	
	public static double areaQuad(Vector a, Vector b, Vector c, Vector d)
	{
		return areaTri(a, b, c) + areaTri(b, c, d);
	}
}
