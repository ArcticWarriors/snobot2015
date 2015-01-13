package com.snobot.simulator;

import net.java.games.input.Component.Identifier;

public class XboxJoystick extends GamepadJoystick {

	public XboxJoystick() 
	{
		super( 
				"Controller (XBOX 360 For Windows)",

				new Identifier[]{
					Identifier.Axis.X,   //Left x
					Identifier.Axis.Y,   //Left Y 
					Identifier.Axis.Z,  //Right Trigger
					Identifier.Axis.Z,  //Left Trigger
					Identifier.Axis.Z,   //Right x 
					Identifier.Axis.Z,  //Right x 
					Identifier.Axis.Z,  //Right x 
					Identifier.Axis.Z,  //Right x 
					Identifier.Axis.Z,  //Right x 
					Identifier.Axis.Z,  //Right x 
					Identifier.Axis.Z,  //Right x 
				}, 
				
				new Identifier[]{
					Identifier.Button._0,  //Square
					Identifier.Button._1,  //X
					Identifier.Button._2,  //Circle
					Identifier.Button._3,  //Triangle
					Identifier.Button._4,  //L1
					Identifier.Button._5,  //R1
					Identifier.Button._6,  //L2 (half pressed or more)
					Identifier.Button._7,  //R2 (half pressed or more)
					Identifier.Button._8,  //Share
					Identifier.Button._9,  //Options
					Identifier.Button._10, //L3
					Identifier.Button._11, //R3
					Identifier.Button._12, //ps4 button
					Identifier.Button._13, //Motion pad
				});
	}
}
