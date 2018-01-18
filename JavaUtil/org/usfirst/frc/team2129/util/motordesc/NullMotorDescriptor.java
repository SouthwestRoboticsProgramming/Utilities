package org.usfirst.frc.team2129.util.motordesc;

import org.usfirst.frc.team2129.util.speedcontrollers.NullSpeedController;

import edu.wpi.first.wpilibj.SpeedController;

public class NullMotorDescriptor extends MotorDescriptor {
	/* Describes a motor that is not connected. Anything you write to the motor will simply be ignored, but it will behave as if it were a real motor.
	 * You can use them as:
	 * ... = new NullMotorDescriptor();	//Simply ignore everyhting sent to the motor
	 */

	@Override
	protected SpeedController _get() {
		return new NullSpeedController();
	}

}
