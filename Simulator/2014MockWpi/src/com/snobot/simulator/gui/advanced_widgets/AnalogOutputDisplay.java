package com.snobot.simulator.gui.advanced_widgets;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.AnalogWrapper;
import com.snobot.simulator.RelayWrapper;
import com.snobot.simulator.SpeedControllerWrapper;
import com.snobot.simulator.gui.Util;

public class AnalogOutputDisplay extends BaseWidgetDisplay<AnalogWrapper> {

	private class AnalogDisplay extends JPanel
	{
		private static final int sDOT_SIZE = 30;
		
		private double mMotorSpeed;
		
		public AnalogDisplay()
		{
			setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
		}
		
		public void updateDisplay(double aValue)
		{
			mMotorSpeed = aValue;
			repaint();
		}
		
		public void paint(Graphics g)
		{
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(Util.colorGetShaededColor(mMotorSpeed, 5, 0));
			g.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
		}
	}
	
	public AnalogOutputDisplay(Map<Integer, AnalogWrapper> aMap) {
		super(aMap);
		setBorder(new TitledBorder("Analog"));
	}

	public void update(Map<Integer, AnalogWrapper> aMap)
	{
		for(Entry<Integer, AnalogWrapper> pair : aMap.entrySet())
		{
			((AnalogDisplay)mWidgetMap.get(pair.getKey())).updateDisplay(pair.getValue().getVoltage());
		}
	}

	@Override
	protected AnalogDisplay createWidget(Entry<Integer, AnalogWrapper> pair) {
		return new AnalogDisplay();
	}
}
