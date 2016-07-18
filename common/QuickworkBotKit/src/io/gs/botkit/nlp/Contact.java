package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class Contact extends Entity
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

	public Contact()
	{
		this.setName(NLPConstants.ENTITY_TYPE_CONTACT);
	}
}
