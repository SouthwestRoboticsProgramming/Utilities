package org.usfirst.frc.team2129.util.encoderdesc;

import org.usfirst.frc.team2129.util.encoderdesc.iencoder.IEncoder;

public abstract class IEncoderDescriptor {
	private IEncoder encoder;
	
	public IEncoder get(){
		if(encoder==null) encoder = _get();
		return encoder;
	}
	
	protected abstract IEncoder _get();
}
