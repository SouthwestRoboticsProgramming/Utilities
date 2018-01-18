package org.usfirst.frc.team2129.util.speedcontrollers;

import edu.wpi.first.wpilibj.SpeedController;

public class SplitSpeedController implements SpeedController {
	/*
	 * Simple utility class to do all the things done to one to the other.
	 * Inversion happens in this, as opposed to being passed along (to allow having two motors with different inversion states in the same SplitSpeedController)
	 */
	private double state;
	private boolean inv;
	private SpeedController s1, s2;

	public SplitSpeedController(SpeedController s1, SpeedController s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public void pidWrite(double output) {
		set(output);
	}

	public double get() {
		return state;
	}

	public void set(double speed) {
		state = speed;
		speed *= inv ? -1 : 1;
		s1.set(speed);
		s2.set(speed);
	}

	public void setInverted(boolean isInverted) {
		inv = isInverted;
	}

	public boolean getInverted() {
		return inv;
	}

	public void disable() {
		set(0);
	}

	public void stopMotor() {
		disable();
	}
}
