package com.snobot2012.intake;

import com.snobot.xlib.ISubsystem;
import com.snobot2012.SmartDashboardNames;
import com.snobot2012.ui.OperatorJoystick;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotIntake implements ISubsystem
{

    private SpeedController mIntakeMotorUpper;
    private SpeedController mIntakeMotorLower;
    private OperatorJoystick mIntakeJoystick;

    public SnobotIntake(SpeedController aIntakeLower, SpeedController aIntakeUpper, OperatorJoystick aJoystick)
    {
        mIntakeMotorLower = aIntakeLower;
        mIntakeMotorUpper = aIntakeUpper;
        mIntakeJoystick = aJoystick;
    }

    public void init()
    {

    }

    @Override
    public void update()
    {
    }

    @Override
    public void control()
    {
        mIntakeMotorLower.set(mIntakeJoystick.useIntakeLower());
        mIntakeMotorUpper.set(mIntakeJoystick.useIntakeUpper());
    }

    @Override
    public void rereadPreferences()
    {
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashboardNames.INTAKE_SPEED_LOWER, mIntakeMotorLower.get());
        SmartDashboard.putNumber(SmartDashboardNames.INTAKE_SPEED_UPPER, mIntakeMotorUpper.get());

    }

    @Override
    public void updateLog()
    {
    }

    @Override
    public void stop()
    {
    }

}
