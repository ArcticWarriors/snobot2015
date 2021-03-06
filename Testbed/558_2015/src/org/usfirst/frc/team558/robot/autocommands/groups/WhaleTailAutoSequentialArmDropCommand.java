package org.usfirst.frc.team558.robot.autocommands.groups;

import org.usfirst.frc.team558.robot.Robot;
import org.usfirst.frc.team558.robot.autocommands.DoNothingCommand;
import org.usfirst.frc.team558.robot.autocommands.TestEncoderCommand;
import org.usfirst.frc.team558.robot.autocommands.WhaleTailInCommand;
import org.usfirst.frc.team558.robot.autocommands.WhaleTailOutCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WhaleTailAutoSequentialArmDropCommand extends CommandGroup
{

    public WhaleTailAutoSequentialArmDropCommand()
    {

        requires(Robot.gripper);
        requires(Robot.drivetrain);
        requires(Robot.elevator);

        // Test Improvement sequential breakup of driving and dropping arm
        // addSequential(new TestEncoderCommand(4.5 , .68, 3));
        addSequential(new WhaleTailOutCommand(.025));
        addSequential(new TestEncoderCommand(8.9, .71, 3));
        addSequential(new DoNothingCommand(.66));
        addSequential(new TestEncoderCommand(50, -1.0, 1));
        addSequential(new WhaleTailInCommand(1.5));
        addSequential(new TestEncoderCommand(22, .57, 1));
        addSequential(new DoNothingCommand(1));

        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
