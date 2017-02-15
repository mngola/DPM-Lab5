package launcher;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;


public class Lab5 {
	//Constants
	private static final double RIGHT_TARGET_ANGLE = 71.57;
	private static final double LEFT_TARGET_ANGLE = 108.43;
	private static final int LAUNCH_ROTATION = 90;
	private static final int LAUNCH_SPEED = 875;
	private static final int LAUNCH_ACCELERATION = 8000;
	// Static Resources:
	// Left motor connected to output A
	// Right motor connected to output D
	// Launch motor connected to output B
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	private static final EV3LargeRegulatedMotor launchMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	private static TextLCD LCD = LocalEV3.get().getTextLCD();

	public static void main(String[] args)
	{
		// setup the odometer and navigator
		Odometer odo = new Odometer(leftMotor, rightMotor, 30, true);
		Navigation navigator = new Navigation(odo);


		int buttonChoice;
		//Loop the program until the exit button is pressed
		do
		{
			//Loop the display until either left, right or up is pressed
			do
			{
				LCD.clear();
				LCD.drawString("        ^^         ", 0, 0);
				LCD.drawString("< Left  Up  Right >", 0, 1);

				buttonChoice = Button.waitForAnyPress();
			} while (buttonChoice != Button.ID_LEFT 
					&& buttonChoice != Button.ID_RIGHT
					&& buttonChoice != Button.ID_UP);

			/*
			 * Based on the direction given, the robot should to face that direction 
			 * and draw on the screen which way it is facing  
			 */
			if (buttonChoice == Button.ID_LEFT)
			{ 
				navigator.turnTo(LEFT_TARGET_ANGLE, true);
				LCD.drawString("< Left >", 0, 2);
			} 
			else if(buttonChoice == Button.ID_RIGHT)
			{
				navigator.turnTo(RIGHT_TARGET_ANGLE, true);
				LCD.drawString("< Right >", 0, 2);
			}
			else if(buttonChoice == Button.ID_UP)
			{
				navigator.turnTo(90.0, true);
				LCD.drawString("< Up >", 0, 2);
			}
			/*
			 * Set the acceleration of the motor 
			 * Set the speed of the motor
			 * Rotate the forward 90 degrees to launch the ball
			 */
			launchMotor.setAcceleration(LAUNCH_ACCELERATION);
			launchMotor.setSpeed(LAUNCH_SPEED);
			launchMotor.rotate(LAUNCH_ROTATION);
			launchMotor.rotate(-LAUNCH_ROTATION);
		} while (buttonChoice != Button.ID_ESCAPE);
		System.exit(0);
	}
}
