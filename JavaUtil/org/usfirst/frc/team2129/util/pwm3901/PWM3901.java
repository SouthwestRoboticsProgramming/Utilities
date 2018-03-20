package org.usfirst.frc.team2129.util.pwm3901;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PWM3901 {
	
	SPI spiPort;
	
	public PWM3901(SPI.Port port) {
		spiPort = new SPI(port);
		spiPort.setClockRate(2000000);
		spiPort.setMSBFirst();
		spiPort.setClockActiveLow();
		spiPort.setSampleDataOnFalling();
		spiPort.setChipSelectActiveLow();
//		spiPort.stopAuto();
		
		
		
	}
	
	public void initCheck() {
		registerWrite(0x3A, 0x5A);
		delayMilliseconds(10);
		
		byte modelnum = registerRead(0x00);
		byte modelnum_rev = registerRead(0x5F);
		
		SmartDashboard.putNumber("pwm3901_modelnum", modelnum);
		SmartDashboard.putNumber("pwm3901_modelnum_rev", modelnum_rev);
		
		log("====PWM3901 STARTUP==== 5");
		log("model num   : "+modelnum + "   ::   " + Integer.toBinaryString(modelnum & 0xFF));
		log("model num_r: "+modelnum_rev + "   ::   " + Integer.toBinaryString(modelnum_rev & 0xFF));
		
		for(int i=0;i<=0xC;i++) {
			byte val = registerRead(i);
			log("reg[0x"+Integer.toHexString(i)+"]: "+val+" :: "+Integer.toBinaryString(val & 0xFF));
		}
		
		delayMilliseconds(1000);
	}
	
	protected byte registerRead(int register_) {
		byte register = (byte) register_;
		
		register &= ~0x80;
		
		log("read: writing 0x"+Integer.toHexString(register & 0xFF)+" (0x"+Integer.toHexString(register_)+")");

		spiPort.write(new byte[] {register}, 1);
		
		delayMicroseconds(50);
		
		log("read: reading");
		
		byte[] read = new byte[1];
		spiPort.read(true, read, 1);
		
		log("read: read: 0x"+Integer.toHexString(read[0] & 0xFF));
		
		return read[0];
	}
	
	protected void registerWrite(int register_, int value_) {
		byte register = (byte) register_;
		byte value = (byte) value_;
		
		register = (byte) (register | 0x80);
		
		log("write: 0x"+Integer.toHexString(register & 0xFF)+" (0x"+Integer.toHexString(register_)+") <- 0x"+Integer.toHexString(value));

		spiPort.write(new byte[] {register, value}, 2);
	}
	
	protected void log(String s) {
		System.err.println("PWM3901:  "+s);
	}
	
	protected void delayMicroseconds(int s) {
		try {
			Thread.sleep(0, s*1000);
		} catch (InterruptedException e) {
			;
		}
	}
	
	protected void delayMilliseconds(int s) {
		try {
			Thread.sleep(s, 0);
		} catch (InterruptedException e) {
			;
		}
	}
}
