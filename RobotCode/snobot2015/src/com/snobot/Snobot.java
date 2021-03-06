package com.snobot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.snobot.claw.IClaw;
import com.snobot.claw.SnobotClaw;
import com.snobot.commands.CommandParser;
import com.snobot.drivetrain.IDriveTrain;
import com.snobot.drivetrain.SnobotDriveTrain;
import com.snobot.logger.Logger;
import com.snobot.position.IPositioner;
import com.snobot.position.SnobotPosition;
import com.snobot.rake.IRake;
import com.snobot.rake.SnobotRake;
import com.snobot.stacker.IStacker;
import com.snobot.stacker.SnobotStacker;
import com.snobot.ui.DynamicDriverJoystick;
import com.snobot.ui.IDriverJoystick;
import com.snobot.ui.IOperatorJoystick;
import com.snobot.ui.SnobotOperatorJoystick;
import com.snobot.xlib.ASnobot;
import com.snobot.xlib.ISubsystem;
import com.snobot.xlib.PropertyManager;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the IterativeRobot documentation. If you change the
 * name of this class or the package after creating this project, you must also update the manifest file in the resource directory.
 */
public class Snobot extends ASnobot
{

    // IO
    private Joystick mRawOperatorJoystick;
    private Joystick mRawDriverJoystickPrimary;
    private Joystick mRawDriverJoystickSecondary;

    private IOperatorJoystick mOperatorJoystick;
    private IDriverJoystick mDriverJoystick;

    // Modules
    private IStacker mStacker;
    private IClaw mClaw;
    private IDriveTrain mDriveTrain;
    private IRake mRake;
    private Logger mLogger;
    private SnobotPosition mPositioner;

    // Solenoids
    private Solenoid mClawHandSolenoid;
    private Solenoid mClawArmSolenoid;

    // Motors
    private SpeedController mDriveLeft1;
    private SpeedController mDriveRight1;
    private SpeedController mStackerMotor;
    private SpeedController mRakeMotor;

    // Digital Inputs
    private DigitalInput mUpperLimitSwitch;
    private DigitalInput mLowerLimitSwitch;
    private Encoder mEncoderLeft;
    private Encoder mEncoderRight;
    private DigitalInput mRakeLimitSwitch;

    // Analog Inputs
    private Gyro mGyroSensor;
    private AnalogInput mTransducer;
    private AnalogInput mStackerPot;

    private SimpleDateFormat mLogDateFormat;
    private Command mAutonCommand;
    private SendableChooser mAutonChooser;
    private PowerDistributionPanel mPowerDistributionPanel;
    private CommandParser mParser;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        if (Properties2015.sUSE_CAMERA.getValue() == 1)
        {
            USBCamera camera = new USBCamera("cam0");
            camera.setFPS(5);
            camera.setBrightness(10);
            camera.setExposureManual(50);

            CameraServer server = CameraServer.getInstance();
            server.setQuality(10);
            server.setSize(2);
            server.startAutomaticCapture(camera);
        }

        // Joysticks
        int operator_joystick_port = Properties2015.sOPERATOR_JOYSTICK_PORT.getValue();
        int driver_joystick_1_port = Properties2015.sDRIVER_FLIGHTSTICK_1_PORT.getValue();
        int driver_joystick_2_port = Properties2015.sDRIVER_FLIGHTSTICK_2_PORT.getValue();

        // PWM
        int left_drive_motor_port = Properties2015.sDRIVE_MOTOR_LEFT_1.getValue();
        int right_drive_motor_port = Properties2015.sDRIVE_MOTOR_RIGHT_1.getValue();
        int stacker_motor_port = Properties2015.sSTACKER_MOTOR.getValue();
        int rake_motor_port = Properties2015.sRAKE_MOTOR.getValue();

        // Digital IO
        int left_drive_enc_a = Properties2015.sLEFT_DRIVE_ENC_A.getValue();
        int left_drive_enc_b = Properties2015.sLEFT_DRIVE_ENC_B.getValue();
        int right_drive_enc_a = Properties2015.sRIGHT_DRIVE_ENC_A.getValue();
        int right_drive_enc_b = Properties2015.sRIGHT_DRIVE_ENC_B.getValue();
        int stacker_upper_limit_sw = Properties2015.sSTACKER_UPPER_LIMIT_SWITCH.getValue();
        int stacker_lower_limit_sw = Properties2015.sSTACKER_LOWER_LIMIT_SWITCH.getValue();
        int rake_limit_switch_port = Properties2015.sRAKE_MOTOR_LS.getValue();

        // Analog
        int transducer_port = Properties2015.sTRANSDUCER.getValue();
        int stacker_pot_port = Properties2015.sSTACKER_POT.getValue();
        int gyro_port = Properties2015.sGYRO_SENSOR.getValue();

        // Solenoid
        int claw_solenoid_port = Properties2015.sCLAW_HAND_SOLENOID.getValue();
        int arm_solenoid_port = Properties2015.sCLAW_ARM_SOLENOID.getValue();

        // Save these port mappings before you try to start the robot in case
        // there are conflicts
        PropertyManager.saveIfUpdated();

        mPowerDistributionPanel = new PowerDistributionPanel();

        mLogDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        String headerDate = mLogDateFormat.format(new Date());
        mLogger = new Logger(headerDate);

        // //////////////////////////////////////
        // User Interface
        // //////////////////////////////////////
        mRawOperatorJoystick = new Joystick(operator_joystick_port);
        mOperatorJoystick = new SnobotOperatorJoystick(mRawOperatorJoystick);

        mRawDriverJoystickPrimary = new Joystick(driver_joystick_1_port);
        mRawDriverJoystickSecondary = new Joystick(driver_joystick_2_port);
        mDriverJoystick = new DynamicDriverJoystick(mRawDriverJoystickPrimary, mRawDriverJoystickSecondary, mLogger);

        // Rake
        mRakeMotor = new Talon(rake_motor_port);
        mRakeLimitSwitch = new DigitalInput(rake_limit_switch_port);
        mRake = new SnobotRake(mRakeMotor, mOperatorJoystick, mRakeLimitSwitch, mLogger);

        // //////////////////////////////////////
        // Drivetrain
        // //////////////////////////////////////
        mDriveLeft1 = new Talon(left_drive_motor_port);
        mDriveRight1 = new Talon(right_drive_motor_port);
        mEncoderLeft = new Encoder(left_drive_enc_a, left_drive_enc_b);
        mEncoderRight = new Encoder(right_drive_enc_a, right_drive_enc_b);
        mDriveTrain = new SnobotDriveTrain(mDriveLeft1, mDriveRight1, mDriverJoystick, mEncoderLeft, mEncoderRight, mLogger);

        // //////////////////////////////////////
        // Stacker
        // //////////////////////////////////////
        mUpperLimitSwitch = new DigitalInput(stacker_upper_limit_sw);
        mLowerLimitSwitch = new DigitalInput(stacker_lower_limit_sw);
        mStackerMotor = new VictorSP(stacker_motor_port);
        mStackerPot = new AnalogInput(stacker_pot_port);
        mStacker = new SnobotStacker(mOperatorJoystick, mStackerMotor, mUpperLimitSwitch, mLowerLimitSwitch, mLogger, mStackerPot);

        // //////////////////////////////////////
        // Claw
        // //////////////////////////////////////
        mClawHandSolenoid = new Solenoid(claw_solenoid_port);
        mClawArmSolenoid = new Solenoid(arm_solenoid_port);
        mTransducer = new AnalogInput(transducer_port);
        mClaw = new SnobotClaw(mOperatorJoystick, mLogger, mTransducer, mClawHandSolenoid, mClawArmSolenoid);

        // //////////////////////////////////////
        // Positioning
        // //////////////////////////////////////
        mGyroSensor = new Gyro(gyro_port);
        mPositioner = new SnobotPosition(mGyroSensor, mDriveTrain, mLogger);

        // //////////////////////////////////////
        // Auton Parser
        // //////////////////////////////////////
        mParser = new CommandParser(this);
        readAutoFiles();

        // Finish setup
        mSubsystems = new ArrayList<ISubsystem>();
        mSubsystems.add(mOperatorJoystick);
        mSubsystems.add(mDriverJoystick);
        mSubsystems.add(mStacker);
        mSubsystems.add(mClaw);
        mSubsystems.add(mDriveTrain);
        mSubsystems.add(mPositioner);
        mSubsystems.add(mRake);

        init();
        rereadPreferences();

        // Now all the preferences should be loaded, make sure they are all
        // saved
        PropertyManager.saveIfUpdated();

        readFile();

        addSmartDashboardListeners();
    }

    @Override
    public void init()
    {
        mLogger.init();
        mLogger.addHeader("Voltage");
        mLogger.addHeader("TotalCurrent");
        super.init();
        mLogger.endHeader();
    }

    @Override
    public void autonomousInit()
    {
        readFile();

        if (mAutonCommand != null)
        {
            mAutonCommand.start();
        }
        else
        {
            System.out.println("Couldn't start auton command because it was null");
        }
    }

    @Override
    public void teleopInit()
    {
        if (mAutonCommand != null)
        {
            mAutonCommand.cancel();
        }
    }

    void readFile()
    {
        if (mAutonChooser.getSelected() != null)
        {
            mAutonCommand = mParser.readFile(mAutonChooser.getSelected().toString());
        }
        else
        {
            mAutonCommand = null;
        }
    }

    private void addSmartDashboardListeners()
    {
        mAutonChooser.getTable().addTableListener(new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                readFile();
            }
        });

        ITableListener saveModeListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {

                if (SmartDashboard.getBoolean(SmartDashboardNames.sSAVE_AUTON))
                {
                    mParser.saveAutonMode();
                    readFile();
                }
            }
        };
        NetworkTable.getTable("SmartDashboard").addTableListener(SmartDashboardNames.sSAVE_AUTON, saveModeListener, true);
    }

    public IDriveTrain getDriveTrain()
    {
        return this.mDriveTrain;
    }

    public IPositioner getPositioner()
    {
        return this.mPositioner;
    }

    public IStacker getSnobotStacker()
    {
        return this.mStacker;
    }

    public IClaw getSnobotClaw()
    {
        return this.mClaw;
    }

    public IRake getRake()
    {
        return this.mRake;
    }

    private void readAutoFiles()
    {
        mAutonChooser = mParser.loadAutonFiles(Properties2015.sAUTON_DIR.getValue(), Properties2015.sAUTON_IGNORE_STRING.getValue());
        SmartDashboard.putData("mAutonChooser", mAutonChooser);
    }

    @Override
    public void updateLog()
    {
        String logDate = mLogDateFormat.format(new Date());
        if (mLogger.logNow())
        {
            mLogger.startLogEntry(logDate);

            mLogger.updateLogger(mPowerDistributionPanel.getVoltage());
            mLogger.updateLogger(mPowerDistributionPanel.getTotalCurrent());

            for (ISubsystem iSubsystem : mSubsystems)
            {
                iSubsystem.updateLog();
            }

            mLogger.endLogger();
        }
    }

}
