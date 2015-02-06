package com.snobot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.snobot.claw.SnobotClaw;
import com.snobot.commands.CommandParser;
import com.snobot.drivetrain.SnobotDriveTrain;
import com.snobot.joystick.IDriverJoystick;
import com.snobot.joystick.IDriverJoystick.DriveMode;
import com.snobot.joystick.SnobotFlightstickJoystick;
import com.snobot.joystick.SnobotXBoxDriverJoystick;
import com.snobot.logger.Logger;
import com.snobot.operatorjoystick.SnobotOperatorJoystick;
import com.snobot.position.SnobotPosition;
import com.snobot.stacker.SnobotStacker;
import com.snobot.SmartDashboardNames;
import edu.wpi.first.wpilibj.AnalogChannel;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot extends IterativeRobot
{

    // IO
    private Joystick mRawOperatorJoystick;
    private Joystick mRawDriverJoystickPrimary;
    private Joystick mRawDriverJoystickSecondary;
    private DigitalInput mUpperLimitSwitch;
    private DigitalInput mLowerLimitSwitch;
    private Gyro mGyroSensor;

    private SnobotOperatorJoystick mOperatorJoystick;
    private IDriverJoystick mDriverJoystick;

    private DriveMode mDriveMode;
    private PowerDistributionPanel mPowerDistributionPanel;

    // Modules
    private SnobotStacker mStacker;
    private SnobotClaw mClaw;
    private SnobotDriveTrain mDriveTrain;
    private Logger mLogger;
    private SnobotPosition mPositioner;
    private String mAutonFilePath;

    private CommandParser mParser;

    // Solenoids
    private Solenoid mClawHandSolenoid;
    private Solenoid mClawArmSolenoid;

    // Motors
    private Talon mDriveLeft1;
    private Talon mDriveRight1;
    private Talon mStackerMotor;

    // Vector of iSubsystems for group actions
    private ArrayList mSubsystems;

    private AnalogInput mTransducer;
    private Encoder mEncoderLeft;
    private Encoder mEncoderRight;
    private AnalogChannel mStackerEncoder;

    private SimpleDateFormat sdf;
    private Command mAutonCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit()
    {
        mPowerDistributionPanel = new PowerDistributionPanel();
        mParser = new CommandParser(this);
        //TODO testing purposes only
        mAutonFilePath = new String("../../snobot2015/resources/autonoumous/TestAutonCommand.txt");

        sdf = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        String headerDate = sdf.format(new Date());
        mLogger = new Logger(headerDate);
        mLogger.init();
        mTransducer = new AnalogInput(1);

        mDriveLeft1 = new Talon(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sDRIVE_MOTOR_LEFT_1, 0));
        mDriveRight1 = new Talon(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sDRIVE_MOTOR_RIGHT_1, 1));
        mRawOperatorJoystick = new Joystick(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sOPERATOR_JOYSTICK_PORT, 1));

        mUpperLimitSwitch = new DigitalInput(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sSTACKER_UPPER_LIMIT_SWITCH_PORT_1, 1));
        mLowerLimitSwitch = new DigitalInput(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sSTACKER_LOWER_LIMIT_SWITCH_PORT_1, 2));
        mClawHandSolenoid = new Solenoid (ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sCLAW_HAND_SOLENOID, 1));
        mClawArmSolenoid = new Solenoid (ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sCLAW_ARM_SOLENOID, 2));
        
        mOperatorJoystick = new SnobotOperatorJoystick(mRawOperatorJoystick);
        mStackerMotor = new Talon(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sSTACKER_MOTOR, 2));
        mClaw = new SnobotClaw(mOperatorJoystick, mLogger, mTransducer, mClawHandSolenoid, mClawArmSolenoid );

        String joystickType = ConfigurationNames.getOrSetPropertyString(SmartDashboardNames.sJoystickMode, SmartDashboardNames.sJOYSTICK_MODE_XBOX);

        if (joystickType.equals(SmartDashboardNames.sJOYSTICK_MODE_XBOX))
        {
            mRawDriverJoystickPrimary = new Joystick(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sDRIVER_FLIGHTSTICK_1_PORT, 0));
            mDriverJoystick = new SnobotXBoxDriverJoystick(mRawDriverJoystickPrimary, mLogger, mDriveMode);
        }
        else
        {
            mRawDriverJoystickPrimary = new Joystick(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sDRIVER_FLIGHTSTICK_1_PORT, 0));
            mRawDriverJoystickSecondary = new Joystick(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sDRIVER_FLIGHTSTICK_2_PORT, 0));
            mDriverJoystick = new SnobotFlightstickJoystick(mRawDriverJoystickPrimary, mRawDriverJoystickSecondary, mLogger);
        }

        mEncoderLeft = new Encoder(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sLEFT_DRIVE_ENC_A, 7),
                ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sLEFT_DRIVE_ENC_B, 4));

        mEncoderRight = new Encoder(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sRIGHT_DRIVE_ENC_A, 5),
                ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sRIGHT_DRIVE_ENC_B, 6));

        mStackerEncoder = new AnalogChannel(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sSTACKER_ENCODER_A, 0));

        mStacker = new SnobotStacker(mOperatorJoystick, mStackerMotor, mUpperLimitSwitch, mLowerLimitSwitch, mLogger, mStackerEncoder);

        mDriveTrain = new SnobotDriveTrain(mDriveLeft1, mDriveRight1, mDriverJoystick, mDriveMode, mEncoderLeft, mEncoderRight, mLogger);

        mGyroSensor = new Gyro(ConfigurationNames.getOrSetPropertyInt(ConfigurationNames.sGYRO_SENSOR, 0));

        mPositioner = new SnobotPosition(mGyroSensor, mDriveTrain, mLogger);

        mSubsystems = new ArrayList();
        mSubsystems.add(mOperatorJoystick);
        mSubsystems.add(mDriverJoystick);
        mSubsystems.add(mStacker);
        mSubsystems.add(mClaw);
        mSubsystems.add(mDriveTrain);
        mSubsystems.add(mPositioner);

        for (int i = 0; i < mSubsystems.size(); ++i)
        {
           ISubsystem iSubsystem = (ISubsystem) mSubsystems.get(i);
            iSubsystem.init();
        }

        mLogger.endHeader();

        ConfigurationNames.saveIfUpdated();
    }

    
    public void autonomousInit()
    {
    	mAutonCommand = mParser.readFile(mAutonFilePath);
        mAutonCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();

        update();
        updateSmartDashboard();
        updateLog();
        
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopPeriodic()
    {
        update();
        control();
        updateSmartDashboard();
        updateLog();
    }
    
    private void update()
    {
        for (int i = 0; i < mSubsystems.size(); ++i)
        {
           ISubsystem iSubsystem = (ISubsystem) mSubsystems.get(i);
            iSubsystem.update();

        }
    }
    
    private void control()
    {
        for (int i = 0; i < mSubsystems.size(); ++i)
        {
           ISubsystem iSubsystem = (ISubsystem) mSubsystems.get(i);
            iSubsystem.control();
        }
    }
    
    private void updateSmartDashboard()
    {
        mPositioner.updateSmartDashbaord();
        for (int i = 0; i < mSubsystems.size(); ++i)
        {
           ISubsystem iSubsystem = (ISubsystem) mSubsystems.get(i);
            iSubsystem.updateSmartDashboard();
        }
    }
    
    private void updateLog()
    {
        String logDate = sdf.format(new Date());
        if (mLogger.logNow())
        {
            mLogger.startLogEntry(logDate);
            
            mLogger.updateLogger(mPowerDistributionPanel.getVoltage());
            mLogger.updateLogger(mPowerDistributionPanel.getTotalCurrent());
             
        for (int i = 0; i < mSubsystems.size(); ++i)
        {
           ISubsystem iSubsystem = (ISubsystem) mSubsystems.get(i);
                iSubsystem.updateLog();
            }

            mLogger.endLogger();
        }
    }

    /**
     * This function is called periodically during test mode
     */
    
    public void testPeriodic()
    {

    }

    
    public void teleopInit()
    {

    }

    public SnobotDriveTrain getDriveTrain()
    {
        return this.mDriveTrain;
    }

    public SnobotPosition getPositioner()
    {
        return this.mPositioner;
    }

    public SnobotStacker getSnobotStacker() 
    {
        return this.mStacker;
    }
    
    public SnobotClaw getSnobotClaw()
    {
        return this.mClaw;
    }

}