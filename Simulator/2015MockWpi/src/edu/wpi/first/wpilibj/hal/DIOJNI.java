package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.snobot.simulator.DigitalSourceWrapper;
import com.snobot.simulator.SensorActuatorRegistry;

public class DIOJNI extends JNIWrapper {
	

    private static DigitalSourceWrapper getWrapperFromBuffer(ByteBuffer digital_port_pointer)
    {
        int port = digital_port_pointer.get(0);
        return SensorActuatorRegistry.get().getDigitalSources().get(port);
    }
    
	public static ByteBuffer initializeDigitalPort(ByteBuffer port_pointer, IntBuffer status)
    {
		return port_pointer;
    }
	public static byte allocateDIO(ByteBuffer digital_port_pointer, byte input, IntBuffer status)
    {
		int pin = digital_port_pointer.get(0);
        DigitalSourceWrapper wrapper = new DigitalSourceWrapper(pin);
		SensorActuatorRegistry.get().register(wrapper, pin);
		
		return 0;

    }
	public static void freeDIO(ByteBuffer digital_port_pointer, IntBuffer status)
    {

    }
	public static void setDIO(ByteBuffer digital_port_pointer, short value, IntBuffer status)
    {
	    getWrapperFromBuffer(digital_port_pointer).set(value == 1);
    }
	public static byte getDIO(ByteBuffer digital_port_pointer, IntBuffer status)
    {
		return (byte) (getWrapperFromBuffer(digital_port_pointer).get() ? 1 : 0);

    }
	public static byte getDIODirection(ByteBuffer digital_port_pointer, IntBuffer status)
    {
		return 0;

    }
	public static void pulse(ByteBuffer digital_port_pointer, double pulseLength, IntBuffer status)
    {

    }
	public static byte isPulsing(ByteBuffer digital_port_pointer, IntBuffer status)
    {
		return 0;

    }
	public static byte isAnyPulsing(IntBuffer status)
    {
		return 0;

    }
	public static short getLoopTiming(IntBuffer status)
    {
		return 0;

    }
}
