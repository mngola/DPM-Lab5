package launcher;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class Lab5 {
	// Static Resources:
	// Left motor connected to output A
	// Right motor connected to output D
	// Ultrasonic sensor port connected to input S1
	// Touch sensor port connected to input S2
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	private static final EV3LargeRegulatedMotor launchMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	private static final Port usPort = LocalEV3.get().getPort("S1");		
	private static final Port touchPort = LocalEV3.get().getPort("S2");
	private static TextLCD LCD = LocalEV3.get().getTextLCD();

	public static void main(String[] args)
	{
		//Setup ultrasonic sensor
		// 1. Create a port object attached to a physical port (done above)
		// 2. Create a sensor instance and attach to port
		// 3. Create a sample provider instance for the above and initialize operating mode
		// 4. Create a buffer for the sensor data
		@SuppressWarnings("resource")							    	// Because we don't bother to close this resource
		SensorModes usSensor = new EV3UltrasonicSensor(usPort);
		SampleProvider usValue = usSensor.getMode("Distance");			// colorValue provides samples from this instance
		float[] usData = new float[usValue.sampleSize()];				// colorData is the buffer in which data are returned

		// setup the odometer and navigator
		Odometer odo = new Odometer(leftMotor, rightMotor, 30, true);
		Navigation navigator = new Navigation(odo);


		int buttonChoice;
		do
		{
			do
			{
				LCD.clear();
				LCD.drawString("        ^^         ", 0, 0);
				LCD.drawString("< Left  Up  Right >", 0, 1);

				buttonChoice = Button.waitForAnyPress();
			} while (buttonChoice != Button.ID_LEFT 
					&& buttonChoice != Button.ID_RIGHT
					&& buttonChoice != Button.ID_UP);

			if (buttonChoice == Button.ID_LEFT)
			{ 
				navigator.turnTo(108.43, true);
				LCD.drawString("< Left >", 0, 2);
			} 
			else if(buttonChoice == Button.ID_RIGHT)
			{
				navigator.turnTo(71.57, true);
				LCD.drawString("< Right >", 0, 2);
			}
			else if(buttonChoice == Button.ID_UP)
			{
				navigator.turnTo(90.0, true);
				LCD.drawString("< Up >", 0, 2);
			}
			
			launchMotor.setAcceleration(8000);
			launchMotor.setSpeed(700);
			launchMotor.rotate(120);
			launchMotor.rotate(-120);
		} while (buttonChoice != Button.ID_ESCAPE);
		System.exit(0);
	}
}