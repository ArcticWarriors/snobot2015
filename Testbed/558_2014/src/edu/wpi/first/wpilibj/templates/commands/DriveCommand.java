/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author Matthew.Lythgoe
 */
public class DriveCommand extends CommandBase {
    
    public DriveCommand() {
        requires(drivetrain);
        this.setTimeout(3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Preferences.getInstance().getDouble("AutonSpeed", -.5);
        drivetrain.drive(speed, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
        
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        drivetrain.drive(0, 0);
    }
}
