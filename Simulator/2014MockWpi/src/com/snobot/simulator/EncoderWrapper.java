package com.snobot.simulator;

public class EncoderWrapper {
	
	private double distance_traveled = 0;
	private double distance_per_tick =  1 / 255.0;

	public void addDistanceDelta(double aDistance) {
		distance_traveled += aDistance;
	}
	
	public double getDistance()
	{
		return distance_traveled;
	}
	
	public double getDecodedDistance()
	{
		return getDistance() / 4.0;
	}

	public int getRaw() {
		return (int) (distance_traveled / distance_per_tick);
	}

	public void reset() {
		distance_traveled = 0;
	}

	public void setDistancePerTick(double aDPT) {
		distance_per_tick = aDPT;
	}

}