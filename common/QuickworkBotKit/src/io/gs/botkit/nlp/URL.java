package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class URL extends Entity
{
	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String url)
	{
		this.value = url;
	}

	public URL()
	{
		this.setName(NLPConstants.ENTITY_TYPE_URL);
	}
}