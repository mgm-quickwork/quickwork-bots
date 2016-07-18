package io.gs.botkit.model.statemachine;

import org.json.JSONObject;

public class TransactionResult
{
	private boolean hasNext;
	private String type;
	private String nextComponentId;
	private JSONObject valueObj;
	private String value;
	private int valueInt;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public JSONObject getValueObj()
	{
		return valueObj;
	}

	@Override
	public String toString()
	{
		return "TransactionResult [hasNext=" + hasNext + ", type=" + type + ", nextComponentId=" + nextComponentId + ", valueObj=" + valueObj + ", value=" + value + ", valueInt=" + valueInt
				+ ", valueLong=" + valueLong + ", valueJSON=" + valueJSON + "]";
	}

	public void setValueObj(JSONObject valueObj)
	{
		this.valueObj = valueObj;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public int getValueInt()
	{
		return valueInt;
	}

	public void setValueInt(int valueInt)
	{
		this.valueInt = valueInt;
	}

	public long getValueLong()
	{
		return valueLong;
	}

	public void setValueLong(long valueLong)
	{
		this.valueLong = valueLong;
	}

	public JSONObject getValueJSON()
	{
		return valueJSON;
	}

	public void setValueJSON(JSONObject valueJSON)
	{
		this.valueJSON = valueJSON;
	}

	public boolean isHasNext()
	{
		return hasNext;
	}

	private long valueLong;
	private JSONObject valueJSON;

	public boolean hasNext()
	{
		return hasNext;
	}

	public void setHasNext(boolean hasNext)
	{
		this.hasNext = hasNext;
	}

	public String getNextComponentId()
	{
		return nextComponentId;
	}

	public void setNextComponentId(String nextComponentId)
	{
		this.nextComponentId = nextComponentId;
	}

}
