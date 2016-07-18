package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class Location extends Entity
{
	private String location;

	public Location()
	{
		this.setName(NLPConstants.ENTITY_TYPE_LOCATION);

	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
}
