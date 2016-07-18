package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class Reminder extends Entity
{
	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void reminder()
	{
		this.setName(NLPConstants.ENTITY_TYPE_REMINDER);
	}
}