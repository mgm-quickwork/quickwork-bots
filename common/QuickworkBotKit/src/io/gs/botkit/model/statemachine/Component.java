package io.gs.botkit.model.statemachine;

import org.json.JSONObject;

import io.gs.botkit.util.GupshupHelper;

public class Component
{
	public String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}

	public String getStartText()
	{
		return startText;
	}

	public String getCompletedText()
	{
		return completedText;
	}

	public String getType()
	{
		return type;
	}

	public boolean isBubbleToHandler()
	{
		return bubbleToHandler;
	}

	public JSONObject getComponentObj()
	{
		return componentObj;
	}

	@Override
	public String toString()
	{
		return "Component [name=" + name + ", id=" + id + ", startText=" + startText + ", completedText=" + completedText + ", type=" + type + ", bubbleToHandler=" + bubbleToHandler
				+ ", componentObj=" + componentObj + "]";
	}

	private String name;
	private String id;
	private String startText;
	private String completedText;
	private String type;
	private boolean bubbleToHandler;
	private boolean isBlockable;
	private String loopText;

	public boolean isBlockable()
	{
		return isBlockable;
	}

	public String getLoopText()
	{
		return loopText;
	}
	
	public Component setStartText(String text)
	{
		this.startText = text;
		return this;
	}

	private JSONObject componentObj;

	public Component build(JSONObject jo)
	{
		this.startText = GupshupHelper.getString("startText", jo);
		this.completedText = GupshupHelper.getString("completedText", jo);
		this.componentObj = GupshupHelper.getJSONObject("componentObj", jo);
		// Cannot be NULL
		this.id = jo.getString("id");
		this.type = jo.getString("type");
		this.name = GupshupHelper.getString("name", jo);
		this.isBlockable = GupshupHelper.getBoolean("isBlockable", jo);
		this.loopText=GupshupHelper.getString("loopText", jo);

		return this;
	}

}
