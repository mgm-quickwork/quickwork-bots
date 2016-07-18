package io.gs.botkit.nlp;

import org.json.JSONObject;

public class Entity
{
	private String name;
	private boolean isSuggested;
	private double confidence;
	private JSONObject raw;

	public JSONObject getRaw()
	{
		return raw;
	}

	public void setRaw(JSONObject raw)
	{
		this.raw = raw;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getConfidence()
	{
		return confidence;
	}

	public void setConfidence(double conf)
	{
		this.confidence = conf;
	}

	public boolean isSuggested()
	{
		return isSuggested;
	}

	public Entity setSuggested(boolean isSuggested)
	{
		this.isSuggested = isSuggested;
		return this;
	}

}
