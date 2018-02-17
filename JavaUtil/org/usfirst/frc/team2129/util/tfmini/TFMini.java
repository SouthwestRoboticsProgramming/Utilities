package org.usfirst.frc.team2129.util.tfmini;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TFMini extends SensorBase implements PIDSource {
	
	PIDSourceType      pidSourceType = PIDSourceType.kDisplacement;
	SerialPort         serialPort;
	short              rawDistance = -1;
	short              rawStrength = -1;
	int                totalFrames = 0;
	int                errorFrames = 0;
	int                bufferErrors = 0;
	long               lastFrameTime = -1;
	boolean            debugMode = false;
	
	
	public TFMini(SerialPort.Port port) {
		serialPort = new SerialPort(115200, port, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
		serialPort.reset(); //Reset to known mode
		
		serialPort.setTimeout(1); //Set a nonzero timeout for writing (.write() issue RuntimeError otherwise)
		serialPort.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess);
		serialPort.write(new byte[] {0x42, 0x57, 0x02, 0x00, 0x00, 0x00, 0x01, 0x06}, 8);
		// ^-- Write the magic bytestring to instruct it to run in "normal" mode
		serialPort.flush();
		
		
		// Small buffer. This should be plenty if polled quickly enough
		serialPort.setReadBufferSize(256);
		
		// Set timeout to 0 to avoid waiting
		serialPort.setTimeout(0);
		
		poll();
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
	}

	@Override
	public double pidGet() {
		if (pidSourceType==PIDSourceType.kDisplacement) return (double) this.rawDistance;
		return 0;
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 3];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 3] = hexArray[v >>> 4];
	        hexChars[j * 3 + 1] = hexArray[v & 0x0F];
	        hexChars[j * 3 + 2] = ' ';
	    }
	    return new String(hexChars);
	}
	
	protected byte[] getLastFrame(byte[] array) {
		//Seek through the byte array for a complete frame (i.e. 0x5959 followed by 7 bytes
		for(int i=Math.max(0, array.length-18);i<(array.length-9);i++) {
			if(array[i] == 0x59 && array[i+1] == 0x59) {
				return new byte[]{array[i], array[i+1], array[i+2], array[i+3], array[i+4], array[i+5], array[i+6], array[i+7], array[i+8]};
			}
		}
		return new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	}
	
	protected short parseRawDistance(byte[] frame) {
		int ulow = frame[2] & 0xFF; //Converting unsigned byte to signed integer
		int uhigh = frame[3] & 0xFF;
		return (short) (ulow + (uhigh << 8));
	}
	
	protected short parseRawStrength(byte[] frame) {
		int ulow = frame[4] & 0xFF;
		int uhigh = frame[5] & 0xFF;
		return (short) (ulow + (uhigh << 8));
	}
	
	protected boolean validateFrame(byte[] frame) {
		byte sum = 0;
		for (int i=0;i<8;i++) sum+=frame[i];
		return frame[0] == 0x59 && frame[1] == 0x59 && frame[8] == sum;
	}
	
	private void hwError(String message) {
		bufferErrors++;
		putDebug("buffererrors", bufferErrors);
		serialPort.reset();
		System.err.println(message);
	}
	
	public void poll() {
		try {
			int ready=0;
			try {
				ready = serialPort.getBytesReceived();
			}catch (RuntimeException e) {
				hwError("TFMini Serial I/O getBytesReceived error");
				return;
			}
			putDebug("waiting", ready);
			if(ready>=18) {
				byte[] rawData;
				try {
					rawData = serialPort.read(256*256); //Read entire buffer
				}catch (RuntimeException e) {
					hwError("TFMini Serial I/O Overflow. Buffer size was "+ready);
					return;
				}
				
				if(rawData.length<18) {
					hwError("TFMini Serial I/O Underflow. Buffer size was "+rawData.length);
					return;
				}
				
				putDebug("buffer", bytesToHex(rawData));
				
				byte[] frame = getLastFrame(rawData);
			
				putDebug("frame", bytesToHex(frame));
				
				totalFrames++;
				
				putDebug("totalframes", totalFrames);
				
				if(validateFrame(frame)) {
					rawDistance = parseRawDistance(frame);
					rawStrength = parseRawStrength(frame);
					lastFrameTime = System.currentTimeMillis();
					
					putDebug("distance", rawDistance);
					putDebug("strength", rawStrength);
					putDebug("lasttime", (int) lastFrameTime);
				}else {
					errorFrames++;
					putDebug("errorframes", errorFrames);
				}
			}
		}catch(RuntimeException e) {
			hwError("TFMini Serial I/O Unknown Error!");
		}
	}

	private void putDebug(String string, String value) {
		if (debugMode) SmartDashboard.putString("tfmini_debug_"+string, value);
	}

	private void putDebug(String string, int value) {
		if (debugMode) SmartDashboard.putNumber("tfmini_debug_"+string, value);
	}

	public short getRawDistance() {
		return rawDistance;
	}
	
	public short getRawStrength() {
		return rawStrength;
	}
	
	public int getTotalFrames() {
		return totalFrames;
	}
	
	public int getErrorFrames() {
		return errorFrames;
	}
	
	public int getBufferFrames() {
		return errorFrames;
	}
	
	public long getLastFrameTime() {
		return lastFrameTime;
	}
	
	public void setDebugMode(boolean v){
		debugMode=v;
	}

}
