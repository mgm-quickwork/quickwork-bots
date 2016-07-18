package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class DateTime extends Entity
{
	private String value;
	private String grain;
	
	private DateTime to;
	private DateTime from;
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getGrain()
	{
		return grain;
	}

	public void setGrain(String grain)
	{
		this.grain = grain;
	}
	
	public DateTime getTo()
	{
		return to;
	}

	public void setTo(DateTime to)
	{
		this.to = to;
	}

	public DateTime getFrom()
	{
		return from;
	}

	public void setFrom(DateTime from)
	{
		this.from = from;
	}
	
	public DateTime()
	{
		this.setName(NLPConstants.ENTITY_TYPE_DATETIME);
	}
}
