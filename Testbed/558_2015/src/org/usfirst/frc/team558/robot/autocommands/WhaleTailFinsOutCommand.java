package org.usfirst.frc.team558.robot.autocommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team558.robot.*;

/**
 *
 */
public class WhaleTailFinsOutCommand extends Command {

    public WhaleTailFinsOutCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gripper);
        setTimeout(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gripper.WhaleTailFinsOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
