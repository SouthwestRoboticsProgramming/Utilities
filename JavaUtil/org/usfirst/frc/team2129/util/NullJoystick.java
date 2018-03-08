package org.usfirst.frc.team2129.util;

import edu.wpi.first.wpilibj.GenericHID;

public class NullJoystick extends GenericHID {

	public NullJoystick(int port) {
		super(port);
	}

	public double getX(Hand hand) {
		return 0;
	}

	public double getY(Hand hand) {
		return 0;
	}
	
	public boolean getRawButton(int button) {return false;}

}
