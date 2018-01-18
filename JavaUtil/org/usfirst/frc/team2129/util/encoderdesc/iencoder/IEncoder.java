package org.usfirst.frc.team2129.util.encoderdesc.iencoder;

import edu.wpi.first.wpilibj.PIDSource;

public interface IEncoder extends PIDSource {
	/*
	 * Base of my hierarchy of Encoder providers. WPILib doesn't actually have an Encoder interface, so we had to do this
	 */
	public double getDistance();

	public double getRate();

	public void zero();
}
