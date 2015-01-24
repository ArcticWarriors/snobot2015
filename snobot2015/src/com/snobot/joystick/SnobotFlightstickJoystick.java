package com.snobot.joystick;

import edu.wpi.first.wpilibj.Joystick;


/**
 * Implements Driver Joystick Interface
 * Sets up Snobot Flight Stick for Driver
 * Ayush/Ammar
 *
 */
public class SnobotFlightstickJoystick implements IDriverJoystick{

	private Joystick mLeftFlightStick;
	private Joystick mRightFlightStick;
	private boolean mDriveMode;
	
	/**
	 * Constructor for Flight Stick 
	 * @param aLeftFlightStick Argument for Left Flight Stick
	 * @param aRightFlightStick Argument for Right Flight Stick
	 */
	public SnobotFlightstickJoystick (Joystick aLeftFlightStick, Joystick aRightFlightStick)
	{
		System.out.println("Creating flightstick joystick");
		mLeftFlightStick = aLeftFlightStick;
		mRightFlightStick = aRightFlightStick;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rereadPreferences() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getLeftY() {
		// TODO Auto-generated method stub
		return mLeftFlightStick.getRawAxis(1);
	}
	
	@Override
	public double getRightY() {
		// TODO Auto-generated method stub
		return mRightFlightStick.getRawAxis(1);
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return mLeftFlightStick.getRawAxis(1);
	}

	@Override
	public double getRotate() {
		// TODO Auto-generated method stub
		return mRightFlightStick.getRawAxis(0);
	}
	
	
	public boolean getDriveMode()
	{
		// TODO Auto-generated method stub
		
		if ( mRightFlightStick.getRawButton(4))
		{
			mDriveMode = true;
		}
		else if (mRightFlightStick.getRawButton(5))
		{
			mDriveMode = false;
		}
		
		return mDriveMode;
	}
}