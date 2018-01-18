package org.usfirst.frc.team2129.util.motordesc;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SpeedController;

public class VictorMotorDescriptor extends MotorDescriptor {
	/* Describes a motor connected to a motor controller (e.g. Jaguar) that connects of a PWM cable. It's number is determined by where it plugs in.
	 * You can use them as:
	 * ... = new PWMMotorDescriptor(1);	//Motor connected thru a motor controller connected to PWM port #1
	 * 
	 * The second argument is inversion, so to connect a backwards motor, simple add `, true`, for example:
	 * ... = new PWMMotorDescriptor(1, false);	//Motor connected backwards thru a motor controller connected to PWM port #1
	 */
	
	private int id;
	private boolean invert = false;

	public VictorMotorDescriptor(int pwmID) {
		id = pwmID;
	}

	public VictorMotorDescriptor(int pwmID, boolean invert) {
		id = pwmID;
		this.invert = invert;
	}

	public SpeedController _get() {
		Victor victor = new Victor(id);
		victor.setInverted(invert);
		return victor;
	}

}
