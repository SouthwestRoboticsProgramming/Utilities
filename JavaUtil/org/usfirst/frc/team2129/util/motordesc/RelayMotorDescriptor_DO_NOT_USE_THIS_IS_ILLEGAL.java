package org.usfirst.frc.team2129.util.motordesc;

import org.usfirst.frc.team2129.util.speedcontrollers.RelaySpeedController;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;

public class RelayMotorDescriptor_DO_NOT_USE_THIS_IS_ILLEGAL extends MotorDescriptor {
	/*
	 * For the love of god don't use this
	 */
	int spot;
	boolean rev;
	
	public RelayMotorDescriptor_DO_NOT_USE_THIS_IS_ILLEGAL(int s, boolean r){
		spot=s;
		rev=r;
	}
	
	public RelayMotorDescriptor_DO_NOT_USE_THIS_IS_ILLEGAL(int s){
		this(s, false);
	}
	
	protected SpeedController _get() {
		return new RelaySpeedController(new Relay(spot), rev);
	}

}
