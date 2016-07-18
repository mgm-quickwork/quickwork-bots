package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class CustomEntity extends Entity
{

	public CustomEntity()
	{
		this.setName(NLPConstants.ENTITY_TYPE_CUSTOM);
	}

	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
