package org.usfirst.frc.team2129.util.motordesc;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class MotorDescriptor {
	private SpeedController controller;

	public SpeedController get() {
		if (controller == null)
			controller = _get();
		else System.err.println("WARN: Called get() again on "+this);
		return controller;
	}

	protected abstract SpeedController _get();
}
