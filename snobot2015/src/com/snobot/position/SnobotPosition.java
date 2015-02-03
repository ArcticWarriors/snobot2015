package com.snobot.position;

import com.snobot.ConfigurationNames;
import com.snobot.ISubsystem;
import com.snobot.SmartDashboardNames;
import com.snobot.drivetrain.SnobotDriveTrain;
import com.snobot.logger.Logger;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Calculates Snobot's current position and orientation with the side of the
 * field opposite drivers defined as north
 * 
 * @author Alec
 *
 */
public class SnobotPosition implements ISubsystem
{

    /**
     * Snobot's X-position
     */
    private double mPositionX;

    /**
     * Snobot's Y-position
     */
    private double mPositionY;

    /**
     * Snobot's rotation in radians
     */
    private double mRadianRotation;

    /**
     * Distance traveled by Snobot since last update
     */
    private double mDistanceTraveled;

    /**
     * Gyro-sensor that SnobotPosition uses to calculate direction
     */
    private Gyro mGyroSensor;

    /**
     * Snobot's drive-train that SnobotPosition uses to get distance
     */
    private SnobotDriveTrain mDriveTrain;

    /**
     * Logger for recording data
     */
    private Logger mLogger;

    /**
     * Constructs a SnobotPosition object
     * 
     * @param aPositionX
     *            Snobot's initial position on the X-axis
     * @param aPositionY
     *            Snobot's initial position on the Y-axis
     * @param aRadianRotation
     *            Snobot's initial orientation
     * @param aGryroSensor
     *            Gyro object to calculate orientation
     * @param aDefaultMeasure
     *            Measure that is to be used by default
     */
    public SnobotPosition(Gyro aGyroSensor, SnobotDriveTrain aDriveTrain, Logger aLogger)
    {
        this.mPositionX = 0;
        this.mPositionY = 0;
        this.mRadianRotation = 0;
        this.mGyroSensor = aGyroSensor;
        this.mDriveTrain = aDriveTrain;
        this.mLogger = aLogger;
    }

    /**
     * Gives Logger headers for entries: X-position, Y-position, Degrees,
     * Distance
     */
    public void init()
    {
        this.mLogger.addHeader("Snobot X-position");
        this.mLogger.addHeader("Snobot's Y-position");
        this.mLogger.addHeader("Snobot's orientation in degrees");
        this.mLogger.addHeader("Distance traveled since last update");

    }
    
    /**
     * Gives Logger entries: X-position, Y-position, Degrees, Distance
     */
    public void updateLog()
    {
        this.mLogger.updateLogger(mPositionX);
        this.mLogger.updateLogger(mPositionY);
        this.mLogger.updateLogger(this.getSnobotDegrees());
        this.mLogger.updateLogger(this.mDistanceTraveled);
    }
    
    /**
     * Calculates Snobot's X-position with the opposite side of the field being
     * north
     * 
     * @return Snobot's X-position
     */
    private double calculateX()
    {
        return Math.sin(this.mRadianRotation) * this.mDistanceTraveled + this.mPositionX;
    }

    /**
     * Calculates Snobot's Y-position with the opposite side of the field being
     * north
     * 
     * @return Snobot's Y-position
     */
    private double calculateY()
    {

        return Math.cos(this.mRadianRotation) * this.mDistanceTraveled + this.mPositionY;
    }

    /**
     * Calculates the direction Snobot is facing
     * 
     * @return The direction Snobot is facing in radians between 2PI and -2PI
     */
    private double calculateDirection()
    {
        double gyroDegrees = mGyroSensor.getAngle();
        while (gyroDegrees > 360)
        {
            gyroDegrees = gyroDegrees - 360;
        }

        while (gyroDegrees < -360)
        {
            gyroDegrees = gyroDegrees + 360;
        }
        return Math.toRadians(gyroDegrees);
    }

    /**
     * Calculates the distance traveled by Snobot as the average of the
     * left/right distances
     * 
     * @return The distance traveled by Snobot since last update
     */
    private double calculateDistanceTraveled()
    {
        double distanceRight = this.mDriveTrain.calculateDistanceRight();
        double distanceLeft = this.mDriveTrain.calculateDistanceLeft();
        
        this.mDriveTrain.resetEncoders();
        
        // TODO Debuggers; remove later
        System.out.println(distanceRight);
        System.out.println(distanceLeft);

        return (distanceRight + distanceLeft) / 2;
    }

    /**
     * Updates member variables with numbers calculated via object methods
     */
    public void update()
    {
        this.mRadianRotation = this.calculateDirection();
        this.mDistanceTraveled = this.calculateDistanceTraveled();
        this.mPositionX = this.calculateX();
        this.mPositionY = this.calculateY();
    }

    /**
     * Calculates the value of mRadianRotation in degrees
     * 
     * @return mRadianRotation in human-friendly degrees
     */
    public double getSnobotDegrees()
    {
        return Math.toDegrees(this.mRadianRotation);
    }

    /**
     * Gets Snobot's X-position from mPositionX
     * 
     * @return Snobot's X-position
     */
    public double getSnobotX()
    {
        return this.mPositionX;
    }

    /**
     * Gets Snobot's Y-position from mPositionY
     * 
     * @return Snobot's Y-position
     */
    public double getSnobotY()
    {
        return this.mPositionY;
    }

    /**
     * Gets Snobot's radian rotation from mRadianRotation
     * 
     * @return Snobot's rotation in radians
     */
    public double getSnobotRadians()
    {
        return this.mRadianRotation;
    }

    /**
     * Gets the distance traveled from mDistanceTraveled
     * 
     * @return The distance traveled by Snobot since last update
     */
    public double getSnobotDistance()
    {
        return this.mDistanceTraveled;
    }

    /**
     * Sets Snobot's X-position
     * 
     * @param aXPosition
     *            Snobot's new X-position
     */
    public void setSnobotXPosition(double aXPosition)
    {
        this.mPositionX = aXPosition;
    }

    /**
     * Sets Snobot's Y-position
     * 
     * @param aYPosition
     *            Snobot's new Y-position
     */
    public void setSnobotYPosition(double aYPosition)
    {
        this.mPositionY = aYPosition;
    }

    /**
     * Sets Snobot's orientation
     * 
     * @param aDegrees
     *            Snobot's new orientation in degrees
     */
    public void setSnobotDegreeRotation(double aDegrees)
    {
        this.mRadianRotation = Math.toRadians(aDegrees);
    }

    public void updateSmartDashbaord()
    {
        SmartDashboard.putNumber("Gyro Angle", getSnobotDegrees());
        SmartDashboard.putNumber("Snobot X", mPositionX);
        SmartDashboard.putNumber("Snobot Y", mPositionY);
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
        // TODO Perhaps these could be changed to graphical representations
        SmartDashboard.putNumber(SmartDashboardNames.sSNOBOT_HEADING, this.getSnobotDegrees());
        SmartDashboard.putNumber(SmartDashboardNames.sSNOBOT_X_POSITION, this.mPositionX);
        SmartDashboard.putNumber(SmartDashboardNames.sSNOBOT_Y_POSITION, this.mPositionY);
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
