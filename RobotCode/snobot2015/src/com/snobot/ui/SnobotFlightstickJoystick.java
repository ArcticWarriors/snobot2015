package com.snobot.ui;

import com.snobot.Properties2015;
import com.snobot.logger.Logger;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Implements Driver Joystick Interface Sets up Snobot Flight Stick for Driver Ayush/Ammar
 *
 */
public class SnobotFlightstickJoystick implements IDriverJoystick
{

    private Joystick mLeftFlightStick;
    private Joystick mRightFlightStick;
    private DriveMode mDriveMode;
    private Logger mLogger;

    private double mTankLeftYAxis;
    private double mTankRightYAxis;
    private double mArcadeLeftSpeed;
    private double mArcadeRightRotation;

    /**
     * Constructor for Flight Stick
     * 
     * @param aLeftFlightStick
     *            Argument for Left Flight Stick
     * @param aRightFlightStick
     *            Argument for Right Flight Stick
     */
    public SnobotFlightstickJoystick(Joystick aLeftFlightStick, Joystick aRightFlightStick, Logger aLogger)
    {
        System.out.println("Creating flightstick joystick");
        mLeftFlightStick = aLeftFlightStick;
        mRightFlightStick = aRightFlightStick;
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("Tank Mode: Left Y Axis");
        mLogger.addHeader("Tank Mode: Right Y Axis");
        // mLogger.addHeader("Arcade Mode: Speed (1 to -1)");
        // mLogger.addHeader("Arcade Mode: Right X Axis");
        mLogger.addHeader("Drive Mode");
        mDriveMode = DriveMode.Tank;
    }

    @Override
    public void update()
    {
        mTankLeftYAxis = mLeftFlightStick.getRawAxis(Properties2015.sFLIGHTSTICKS_Y_AXIS.getValue());
        mTankRightYAxis = mRightFlightStick.getRawAxis(Properties2015.sFLIGHTSTICKS_Y_AXIS.getValue());

        mArcadeLeftSpeed = mLeftFlightStick.getRawAxis(Properties2015.sFLIGHTSTICKS_Y_AXIS.getValue());
        mArcadeRightRotation = mRightFlightStick.getRawAxis(Properties2015.sFLIGHTSTICKS_X_AXIS.getValue());
    }

    @Override
    public void control()
    {
    }

    @Override
    public void rereadPreferences()
    {
    }

    @Override
    public void updateSmartDashboard()
    {
    }

    @Override
    public void updateLog()
    {
        // Left Y Axis
        mLogger.updateLogger(mTankLeftYAxis);

        // Right Y Axis
        mLogger.updateLogger(mTankRightYAxis);

        // Speed
        // mLogger.updateLogger(mArcadeLeftSpeed);

        // Angle of the Joy stick (for arcade drive)
        // mLogger.updateLogger(mArcadeRightRotation);

        mLogger.updateLogger(getDriveMode().toString());
    }

    @Override
    public void stop()
    {
    }

    @Override
    public double getLeftY()
    {
        return mTankLeftYAxis;
    }

    @Override
    public double getRightY()
    {
        return mTankRightYAxis;
    }

    @Override
    public double getSpeed()
    {
        return mArcadeLeftSpeed;
    }

    @Override
    public double getRotate()
    {
        return mArcadeRightRotation;
    }

    @Override
    public DriveMode getDriveMode()
    {
        return mDriveMode;
    }

    @Override
    public boolean getDriveForward()
    {
        return false;
    }

    @Override
    public boolean getDriveBackward()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReducedSpeedMode()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDriveMode(DriveMode aDriveMode)
    {
        // TODO Auto-generated method stub

    }

}
