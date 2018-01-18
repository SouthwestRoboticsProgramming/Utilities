package org.usfirst.frc.team2129.util.speedcontrollers;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;

public class RelaySpeedController implements SpeedController {
	//DO NOT USE FOR REAL. HORRIBLE AND BAD
	private boolean inverted = false;
	private double state = 0.;
	private Relay relay;
	
	public RelaySpeedController(Relay r, boolean inv){
		relay=r;
		inverted=inv;
	}
	
	public void pidWrite(double output) {set(output);}
	public double get() {return state;}
	public void setInverted(boolean isInverted) {inverted=isInverted;}
	public boolean getInverted() {return inverted;}
	public void disable() {set(0);}
	public void stopMotor() {set(0);}
	
	public void set(double speed) {
		if(speed!=0){
			if(speed>0){
				relay.set(inverted?Relay.Value.kReverse:Relay.Value.kForward);
			}else{
				relay.set(inverted?Relay.Value.kForward:Relay.Value.kReverse);
			}
		}else{
			relay.set(Relay.Value.kOff);
		}
	}
}
