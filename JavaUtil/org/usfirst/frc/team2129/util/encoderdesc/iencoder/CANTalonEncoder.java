package org.usfirst.frc.team2129.util.encoderdesc.iencoder;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CANTalonEncoder extends IEncoderPIDSourceImplWrapper {
	/*
	 * Adapter shim from a motor controller conencted on a CANTalon Encoder bus to IEncoder
	 */
	private WPI_TalonSRX _talon;

	public CANTalonEncoder(WPI_TalonSRX talon) {
		_talon = talon;
	}

	public double getDistance() {
		return _talon.getSelectedSensorPosition(0);
	}

	public double getRate() {
		return _talon.getSelectedSensorVelocity(0);
	}

	public void zero() {
		_talon.getSensorCollection().setQuadraturePosition(0, 50);
	}
}
