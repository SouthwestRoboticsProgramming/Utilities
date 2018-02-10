package org.usfirst.frc.team2129.util.tfmini;

public class CircularByteBuffer {
	private int size;
	private int pos;
	private byte[] buffer;
	
	public CircularByteBuffer(int size) {
		this.size=size;
		this.pos=0;
		this.buffer = new byte[size];
	}
	
	public void readIn(byte[] array) {
		for(int i=Math.max(0, array.length-this.size); i<array.length; i++){
			this.buffer[this.pos]=array[i];
			this.pos++;
			if(this.pos==this.size) this.pos=0;
		}
	}
	
	public byte[] getSnapshot() {
		return this.buffer.clone();
	}
}
