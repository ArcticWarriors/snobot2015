
package com.snobot;

import com.snobot.drivetrain.SnobotDriveTrain;
import com.snobot.intake.SnobotIntake;
import com.snobot.shooter.SnobotShooter;
import com.snobot.ui.DriverJoystick;
import com.snobot.ui.OperatorJoystick;
import com.snobot.xlib.ASnobot;
import com.snobot.xlib.PropertyManager;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot2012 extends ASnobot
{

	//Shooter
	private SpeedController mShooterMotor;
	private Solenoid mShooterSolenoid; 
	private SnobotShooter mShooter;
	
	//Drive Train
	private SpeedController mLeftMotor;
	private SpeedController mRightMotor;
	private SnobotDriveTrain mDriveTrain;
	
    // Intake
    private SpeedController mIntakeMotor;
    private SnobotIntake mIntake;

    // UI
    private Joystick mShooterJoystick;
    private Joystick mDriveJoystick;
    private DriverJoystick mDriverController;
    private OperatorJoystick mOperatorController;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        // UI
        mShooterJoystick = new Joystick(PortMap.sOPERATOR_JOYSTICK_PORT);
        mDriveJoystick = new Joystick(PortMap.sDRIVER_JOYSTICK_PORT);
        mDriverController = new DriverJoystick(mDriveJoystick);
        mOperatorController = new OperatorJoystick(mShooterJoystick);

    	//Shooter
        mShooterMotor = new Talon(PortMap.sSHOOTER_MOTOR);
        mShooterSolenoid = new Solenoid(PortMap.sSHOOTER_PISTON);
        mShooter = new SnobotShooter(mShooterMotor, mShooterSolenoid, mOperatorController);
    	
    	//Drive Train
        mLeftMotor = new Talon(PortMap.sLEFT_DRIVE_MOTOR);
        mRightMotor = new Talon(PortMap.sRIGHT_DRIVE_MOTOR);
        mDriveTrain = new SnobotDriveTrain(mLeftMotor, mRightMotor, mDriverController);
    	
        // Intake
        mIntakeMotor = new Talon(PortMap.sINTAKE_MOTOR);
        mIntake = new SnobotIntake(mIntakeMotor, mOperatorController);
    	
    	mSubsystems.add(mDriveTrain);
        mSubsystems.add(mShooter);
        mSubsystems.add(mIntake);
    	
        init();
        rereadPreferences();

        // Now all the preferences should be loaded, make sure they are all
        // saved
        PropertyManager.saveIfUpdated();
    }

    @Override
    public void updateLog()
    {
        // Nothing to do....
    }
    
}
