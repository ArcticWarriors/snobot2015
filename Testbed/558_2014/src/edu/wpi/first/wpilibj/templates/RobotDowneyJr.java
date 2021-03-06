/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.DriveCommand;
import edu.wpi.first.wpilibj.templates.commands.ShootandDriveForward;
import edu.wpi.first.wpilibj.templates.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.templates.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.templates.subsystems.Positioning;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Compressor;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotDowneyJr extends IterativeRobot {

    private Command autonomousCommand;
    private Positioning mPositioning;
    private SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
    	
    	chooser = new SendableChooser();
    	chooser.addDefault("Alans Mode", new ShootandDriveForward());
    	chooser.addObject("Drive Only", new DriveCommand());
        SmartDashboard.putData("Auto Modes", chooser);
        
        // Initialize all subsystems
        CommandBase.init();
        
        mPositioning = new Positioning(CommandBase.drivetrain, new Gyro(RobotMap.gyroPort));
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	mPositioning.reset();

        autonomousCommand = (Command) chooser.getSelected();
        autonomousCommand.start();
//        compressor.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        mPositioning.update();
        updateSmartDashboard();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
//        compressor.start();
    	if(autonomousCommand != null)
    	{
    		autonomousCommand.cancel();
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        mPositioning.update();
        updateSmartDashboard();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void updateSmartDashboard()
    {
    	
//        CommandBase.drivetrain
        CommandBase.intake.updateSmartDashboard();
        CommandBase.shooter.updateSmartDashboard();
        CommandBase.drivetrain.updateSmartDashboard();
        mPositioning.updateSmartDashboard();
    }
}
