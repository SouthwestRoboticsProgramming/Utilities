package org.usfirst.frc.team2129.util.encoderdesc.iencoder;

import edu.wpi.first.wpilibj.Encoder;

public class QuadratureEncoder extends IEncoderPIDSourceImplWrapper {
	/*
	 * Just wrap a Encoder into an IEncoder
	 */
	private Encoder _encoder;

	public QuadratureEncoder(int i1, int i2) {
		_encoder = new Encoder(i1, i2);
	}

	public double getDistance() {
		return _encoder.getDistance();
	}

	public double getRate() {
		return _encoder.getRate();
	}

	public void zero() {
		_encoder.reset();
	}

}
