package launcher;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Launcher {
	private static final double GRAVITY = 9.81;
	private static double v0, range, r;
	private EV3LargeRegulatedMotor launchMotor;
	
	public Launcher(EV3LargeRegulatedMotor lMotor, double distance) 
	{
		range = distance;
		launchMotor = lMotor;
	}
	
	public double compVelocity(double h)
	{
		v0 = Math.sqrt(  (h*2.0*GRAVITY) / r  );
		return v0;
	}

}
