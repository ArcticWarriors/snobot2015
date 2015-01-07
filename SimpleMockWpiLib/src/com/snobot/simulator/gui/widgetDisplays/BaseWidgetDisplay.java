package com.snobot.simulator.gui.widgetDisplays;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseWidgetDisplay<ItemType, WidgetType extends Container> extends JPanel
{

	protected Map<Integer, WidgetType> mWidgetMap = new HashMap<>();
	
	public BaseWidgetDisplay(Map<Integer, ItemType> aMap) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(Entry<Integer, ItemType> pair : aMap.entrySet())
		{
			WidgetType panelPair = createWidget(pair);
			mWidgetMap.put(pair.getKey(), panelPair);

			JPanel panel = new JPanel();
			panel.add(new JLabel("" + pair.getKey()));
			panel.add(panelPair);
			
			add(panel);
		}
	}
	
	protected abstract WidgetType createWidget(Entry<Integer, ItemType> pair);
	
	public abstract void update(Map<Integer, ItemType> aMap);
}
