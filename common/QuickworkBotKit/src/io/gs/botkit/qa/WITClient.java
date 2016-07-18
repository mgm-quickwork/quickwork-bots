package io.gs.botkit.qa;

import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.nlp.CustomEntity;
import io.gs.botkit.nlp.Duration;
import io.gs.botkit.nlp.GupshupNLPEngine;
import io.gs.botkit.nlp.Location;
import io.gs.botkit.nlp.NLPResponse;
import io.gs.botkit.nlp.Number;

public class WITClient
{
	
	public static void main(String [] args)
	{
		System.out.println("Hello World");
		
		GupshupBotKit botkit =  GupshupBotKit.getInstance("demo","xxxxxxx");
		GupshupNLPEngine nlp = botkit.createGupshupNLP().witAIEngine("U5MTG7E3EBEZN72QLPOJ3SWO23J3JM3B");
		NLPResponse data = null;
		try
		{
			data = nlp.parse("I want ten  Pediatricans for  13th June");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(data.hasLocations())
		{
			for (Location loc : data.getLocations())
			{
				System.out.println("Location:"+loc.getLocation()+":"+loc.getConfidence());
			}
		}
		if(data.hasDurations())
		{
			for (Duration loc : data.getDurations())
			{
				System.out.println("Duration:"+loc.getValue()+":"+loc.getUnit()+":"+loc.getConfidence());
			}
		}
		if(data.hasNumbers())
		{
			for (Number loc : data.getNumbers())
			{
				System.out.println("Number:"+loc.getValue()+":"+loc.getUnit()+":"+loc.getConfidence());
			}
		}
		if(data.hasCustoms("speciality"))
		{
			for (CustomEntity loc : data.getCustoms("speciality"))
			{
				System.out.println("Custom:"+loc.getValue()+":"+loc.getConfidence());
			}
		}
		
		
		
	
	}
}
