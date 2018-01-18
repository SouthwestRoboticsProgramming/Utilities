package org.usfirst.frc.team2129.util.encoderdesc.iencoder;

import edu.wpi.first.wpilibj.PIDSourceType;

public abstract class IEncoderPIDSourceImplWrapper implements IEncoder {
	/*
	 * Adapter shim for providing a PIDSource.
	 * I feel so dirty for writing 'IEncoderPIDSourceImplWrapper'
	 */
	private PIDSourceType _pidSourceType = PIDSourceType.kDisplacement;

	public PIDSourceType getPIDSourceType() {
		return _pidSourceType;
	}

	public double pidGet() {
		return _pidSourceType == PIDSourceType.kDisplacement ? getDistance() : getRate();
	}

	public void setPIDSourceType(PIDSourceType type) {
		_pidSourceType = type;
	}
}
