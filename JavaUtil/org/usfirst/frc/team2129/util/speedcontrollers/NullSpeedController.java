package org.usfirst.frc.team2129.util.speedcontrollers;

import edu.wpi.first.wpilibj.SpeedController;

public class NullSpeedController implements SpeedController {
	private boolean inverted = false;
	private double output = 0d;

	public void pidWrite(double output) {}
	public double get() {return output;}
	public void set(double speed) {this.output=speed;}
	public void setInverted(boolean isInverted) {this.inverted=isInverted;}
	public boolean getInverted() {return this.inverted;}
	public void disable() {set(0d);}
	public void stopMotor() {set(0d);}
}
