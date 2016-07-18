package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class Duration extends Entity
{
	private long durationInSeconds;
	private String unit;
	private long value;
	
	public long getDurationInSeconds()
	{
		return durationInSeconds;
	}
	public void setDurationInSeconds(long durationInSeconds)
	{
		this.durationInSeconds = durationInSeconds;
	}
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	public long getValue()
	{
		return value;
	}
	public void setValue(long value)
	{
		this.value = value;
	}

	public Duration()
	{
		this.setName(NLPConstants.ENTITY_TYPE_DURATION);
	}
}