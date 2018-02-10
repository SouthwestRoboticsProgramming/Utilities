package org.usfirst.frc.team2129.util.encoderdesc;

import org.usfirst.frc.team2129.util.encoderdesc.iencoder.CANTalonEncoder;
import org.usfirst.frc.team2129.util.encoderdesc.iencoder.IEncoder;
import org.usfirst.frc.team2129.util.motordesc.MotorDescriptor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CANTalonEncoderDescriptor extends IEncoderDescriptor {
	private MotorDescriptor talonDescriptor;

	public CANTalonEncoderDescriptor(MotorDescriptor talonDescriptor) {
		this.talonDescriptor = talonDescriptor;
	}

	protected IEncoder _get() {
		return new CANTalonEncoder((WPI_TalonSRX) talonDescriptor.get());
	}

}
