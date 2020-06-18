package helicopter;

import com.jogamp.opengl.GL2;

public class Engine {
	private int speed;
	private int bladeOffset;
	private long prevTick = System.currentTimeMillis();

	public Engine() {
		speed = 0;
		bladeOffset = 0;
	}
	
	/**
	 * Check whether the blades are spinning
	 * @return speed > 0
	 */
	public boolean engineStarted()
	{
		return speed == 59;
	}
	
	public boolean engineStopped()
	{
		return speed == 0;
	}
	
	/**
	 * Start ramping up rotation speed
	 */
	public void startEngine()
	{		
		speed++;
	}
	
	/**
	 * Slowly decreasing rotation speed
	 */
	public void stopEngine()
	{		
		speed--;
	}

	/**
	 * Calculate offsets for rotating blades
	 * @param gl
	 */
	public void spinBlades(GL2 gl)
	{
		// Update every 16.67ms because we are running at 60fps
		if (System.currentTimeMillis() - prevTick > 1000 / 60.0)
		{
			// Calculate blade offset 
			bladeOffset = bladeOffset + speed;
			
			// -180 degrees when offset is over 180 degrees, this will make no visual differences
			if (bladeOffset > 180)
				bladeOffset -= 180;
			
			prevTick = System.currentTimeMillis();
		}
		
		gl.glRotated(bladeOffset, 0, 1, 0);
	}
}
